package com.example.myapplication

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.util.Log
import android.widget.Button
import android.widget.SeekBar
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    private var progressRabbit = 0
    private var progressTurtle = 0

    private lateinit var btn_start: Button
    private lateinit var sb_rabbit: SeekBar
    private lateinit var sb_turtle: SeekBar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_start = findViewById(R.id.btn_start)
        sb_rabbit = findViewById(R.id.sb_rabbit)
        sb_turtle = findViewById(R.id.sb_turtle)

        btn_start.setOnClickListener {
            btn_start.isEnabled = false
            progressRabbit = 0
            progressTurtle = 0
            sb_rabbit.progress = 0
            sb_turtle.progress = 0
            runRabbit()
            runTurtle()
        }
    }

    private val handler = Handler(
        Looper.myLooper()!!
    ) { msg ->
        Log.e("Rabbit", progressRabbit.toString())
        Log.e("Turtle", progressTurtle.toString())
        if (msg.what == 1) {
            sb_rabbit!!.progress = progressRabbit
        } else if (msg.what == 2) {
            sb_turtle!!.progress = progressTurtle
        }
        if (progressRabbit >= 100 && progressTurtle < 100) {
            Toast.makeText(this@MainActivity, "兔子勝", Toast.LENGTH_SHORT).show()
            btn_start!!.isEnabled = true
        } else if (progressTurtle >= 100 && progressRabbit < 100) {
            Toast.makeText(this@MainActivity, "烏龜勝", Toast.LENGTH_SHORT).show()
            btn_start!!.isEnabled = true
        }
        false
    }

    private fun runRabbit() {
        Thread {
            val sleepProbability =
                arrayOf(true, true, false)
            while (progressRabbit <= 100 && progressTurtle < 100) {
                try {
                    Thread.sleep(100)
                    if (sleepProbability[(Math.random() * 3).toInt()]) {
                        Thread.sleep(300)
                    }
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressRabbit += 3
                val msg = Message()
                msg.what = 1
                handler.sendMessage(msg)
            }
        }.start()
    }

    private fun runTurtle() {
        Thread {
            while (progressRabbit <= 100 && progressTurtle < 100) {
                try {
                    Thread.sleep(100)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
                progressTurtle += 1
                val msg = Message()
                msg.what = 2
                handler.sendMessage(msg)
            }
        }.start()
    }
}