package com.example.muhendisliktasarimi.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ActivitySignUpBinding
import com.example.muhendisliktasarimi.databinding.ActivitySolveQuestionBinding

class SignUpActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignUpBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }
}