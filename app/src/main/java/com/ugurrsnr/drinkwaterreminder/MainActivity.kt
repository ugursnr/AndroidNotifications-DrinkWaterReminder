package com.ugurrsnr.drinkwaterreminder

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.core.content.ContextCompat
import com.ugurrsnr.drinkwaterreminder.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding : ActivityMainBinding

    private lateinit var sharedPreferences :SharedPreferences


    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        sharedPreferences = getSharedPreferences("com.ugurrsnr.drinkwaterreminder", MODE_PRIVATE)
        var currentDrink : Int = sharedPreferences.getInt("currentDrink", 0)
        binding.drinkTodayTV.text = "You Drink : ${currentDrink.toString()} Glasses of Water"
        colorChanger(currentDrink)

        binding.apply {

            upButton.setOnClickListener {
                currentDrink++
                drinkTodayTV.text = "You Drink : ${currentDrink.toString()} Glasses of Water"
                colorChanger(currentDrink)
                sharedPreferences.edit().putInt("currentDrink", currentDrink).apply()
            }

            downButton.setOnClickListener {
                currentDrink--
                if (currentDrink <0){
                    currentDrink = 0
                }
                colorChanger(currentDrink)
                drinkTodayTV.text = "You Drink : ${currentDrink.toString()} Glasses of Water"
                sharedPreferences.edit().putInt("currentDrink", currentDrink).apply()

            }

        }
        binding.addReminderButton.setOnClickListener {
            val intent = Intent(this@MainActivity, AddReminderActivity::class.java)
            startActivity(intent)
        }

    }
    private fun colorChanger(currentDrink : Int){

        val redColor = ContextCompat.getColor(applicationContext,R.color.red)
        val greenColor = ContextCompat.getColor(applicationContext,R.color.green)
        val orangeColor = ContextCompat.getColor(applicationContext,R.color.orange)

        when(currentDrink){
            in 0..5 -> binding.currentTargetTV.setTextColor(redColor.toString().toInt())
            in 5..8 -> binding.currentTargetTV.setTextColor(orangeColor.toString().toInt())
            else    -> binding.currentTargetTV.setTextColor(greenColor.toString().toInt())

        }

    }






}