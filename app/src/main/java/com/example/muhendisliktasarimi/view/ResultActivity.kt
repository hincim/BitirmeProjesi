package com.example.muhendisliktasarimi.view

import android.animation.ObjectAnimator
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.view.View
import android.view.animation.OvershootInterpolator
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivityResultBinding
import com.example.muhendisliktasarimi.databinding.ActivitySplashBinding
import com.google.firebase.Timestamp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.io.File

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)



        val correctCounter= intent.getIntExtra("correctCounter",0)
        val questionsSize= intent.getIntExtra("questionSize",0)
        // varsayılan olarak 0 değerini girerim.


        ObjectAnimator.ofFloat(binding.textViewResult,"translationY",500.0f, 0.0f).apply {
            duration = 1000
            interpolator  = OvershootInterpolator()
            binding.textViewResult.text = "$correctCounter DOĞRU ${questionsSize-correctCounter} YANLIŞ"

        }.start()


        ObjectAnimator.ofFloat(binding.textViewRate,"translationY",500.0f, 0.0f).apply {
            duration = 1000
            interpolator  = OvershootInterpolator()
            binding.textViewRate.text = "% ${(correctCounter * 100)/questionsSize} Başarı"

        }.start()

        object : CountDownTimer(1000,700){
            override fun onTick(p0: Long) {

            }

            override fun onFinish() {
                ObjectAnimator.ofFloat(binding.buttonAgain,"alpha",0.0f,1.0f).apply {
                    duration = 600

                }.start()
                binding.buttonAgain.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(binding.buttonScore,"alpha",0.0f,1.0f).apply {
                    duration = 600

                }.start()
                binding.buttonScore.visibility = View.VISIBLE
            }

        }.start()

        binding.buttonAgain.setOnClickListener {

                startActivity(Intent(this@ResultActivity, SolveQuestionActivity::class.java))
                finish()

            }

            binding.buttonScore.setOnClickListener {
                startActivity(Intent(this@ResultActivity, FeedActivity::class.java))
                finish()

            }
        }
}