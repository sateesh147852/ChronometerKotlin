package com.chronometer

import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.View
import android.widget.Chronometer
import androidx.appcompat.app.AppCompatActivity
import com.chronometer.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), View.OnClickListener {

    private lateinit var binding: ActivityMainBinding
    private lateinit var chronometer: Chronometer
    private var pauseOffset: Long = 0
    private var isRunning: Boolean = true
    private val TAG : String = MainActivity::class.java.simpleName

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initialize()
    }

    private fun initialize() {
        chronometer = binding.chronometer
        chronometer.base = SystemClock.elapsedRealtime()
        chronometer.start()
        binding.tvRestart.setOnClickListener(this)
        binding.tvStart.setOnClickListener(this)
        binding.tvPause.setOnClickListener(this)

        chronometer.setOnChronometerTickListener {
            Log.i(TAG, "initialize: "+it.base)
        }
    }

    override fun onClick(v: View?) {

        when (v?.id) {

            binding.tvRestart.id -> {
                chronometer.base = SystemClock.elapsedRealtime()
                pauseOffset = 0
                if (isRunning) {
                    chronometer.start()
                    isRunning = true
                }
            }

            binding.tvStart.id -> {
                if (!isRunning) {
                    chronometer.base = SystemClock.elapsedRealtime() - pauseOffset
                    chronometer.start()
                    isRunning = true
                }
            }

            binding.tvPause.id -> {
                if (isRunning) {
                    pauseOffset = SystemClock.elapsedRealtime() - chronometer.base
                    chronometer.stop()
                    isRunning = false
                }
            }
        }
    }
}