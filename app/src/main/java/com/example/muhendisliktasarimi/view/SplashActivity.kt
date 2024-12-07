package com.example.muhendisliktasarimi.view

import android.animation.ObjectAnimator
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivitySplashBinding

class SplashActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySplashBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySplashBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // Uygulama başlatıldığında isFirstTime değerini true yapıyoruz
        val sharedPref = this.getSharedPreferences("AppPrefs", Context.MODE_PRIVATE)
        with(sharedPref.edit()) {
            putBoolean("isFirstTime", true) // Uygulama başlatıldığında animasyon oynatılacak
            apply()
        }

        ObjectAnimator.ofFloat(binding.imageView,"rotationY",0.0f,-360.0f).apply {
            duration = 800

        }.start()

        object : CountDownTimer(1000,800){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                startActivity(Intent(this@SplashActivity, SignInActivity::class.java))
                finish()
            }

        }.start()
    }
}