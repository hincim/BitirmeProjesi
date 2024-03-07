package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentHomePageScreenBinding


class HomePageScreen : Fragment() {

    private lateinit var _homePageScreen: FragmentHomePageScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{

        _homePageScreen = FragmentHomePageScreenBinding.inflate(inflater, container, false)
        return _homePageScreen.root
    }
}