package com.ugurrsnr.drinkwaterreminder

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.BitmapFactory
import android.graphics.Color
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.RemoteViews
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.work.*
import com.ugurrsnr.drinkwaterreminder.databinding.ActivityAddReminderBinding
import java.time.LocalDateTime
import java.util.concurrent.TimeUnit

class AddReminderActivity : AppCompatActivity() {


    private var input : Long? = null
    private lateinit var binding : ActivityAddReminderBinding
    private var inputInterval : Long? = null
    private lateinit var sharedPreferences : SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddReminderBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("com.ugurrsnr.drinkwaterreminder", MODE_PRIVATE)

        val currentReminderText = sharedPreferences.getString("currentReminder", "Your current reminder (mins) : 0")
        binding.currentReminderTV.text = currentReminderText

        binding.intervalET.setOnClickListener {
            binding.intervalET.text.clear()
        }


        binding.submitButton.setOnClickListener {
            input = binding.intervalET.text.toString().toLong()
            println("input = $input")

            if (input == null || input!!<15){
                inputInterval = 15
                Toast.makeText(this,"Reminder set 15 minutes", Toast.LENGTH_LONG).show()

            }else{
                inputInterval = input
            }
            println("interval = $inputInterval")

            binding.currentReminderTV.text = "Your current reminder (mins) : $inputInterval"
            sharedPreferences.edit().putString("currentReminder", "Your current reminder (mins) : $inputInterval").apply()

            val myWorkRequest : PeriodicWorkRequest = PeriodicWorkRequestBuilder<SendNotifications>(inputInterval!!,TimeUnit.MINUTES)
                .build()

            WorkManager.getInstance(this)
                .enqueueUniquePeriodicWork(
                    "WORK_NAME",
                    ExistingPeriodicWorkPolicy.REPLACE,
                    myWorkRequest
                )

            WorkManager.getInstance(this).getWorkInfoByIdLiveData(myWorkRequest.id).observe(this,
                Observer {
                    if (it.state == WorkInfo.State.SUCCEEDED) {
                        println("succeded")

                    }else if (it.state == WorkInfo.State.FAILED) {
                        println("failed")

                    } else if (it.state == WorkInfo.State.RUNNING) {
                        println("running")
                    }
                })
            val intentToMain = Intent(this,MainActivity::class.java)
            startActivity(intentToMain)

        }
        binding.deleteReminderButton.setOnClickListener {
            WorkManager.getInstance(this).cancelAllWork()
            binding.currentReminderTV.text = "Your current reminder (mins) : 0"
            sharedPreferences.edit().putString("currentReminder","Your current reminder (mins) : 0").apply()
        }

    }


}