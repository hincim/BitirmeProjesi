package com.example.muhendisliktasarimi.view

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
        return _comePageScreen.root
    }

}