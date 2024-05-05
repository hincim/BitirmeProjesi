package com.example.muhendisliktasarimi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentCommPageScreenBinding


class CommPageScreen : Fragment() {

    private lateinit var _comePageScreen: FragmentCommPageScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _comePageScreen = FragmentCommPageScreenBinding.inflate(inflater, container, false)

        _comePageScreen.btnSignIn.setOnClickListener {
            startActivity(Intent(it.context,SignInActivity::class.java))
        }
        _comePageScreen.btnSignUp.setOnClickListener {
            startActivity(Intent(it.context,SignUpActivity::class.java))
        }
        return _comePageScreen.root
    }

}