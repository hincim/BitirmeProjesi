package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentToolsPageScreenBinding


class ToolsPageScreen : Fragment() {

    private lateinit var _toolsPageScreen: FragmentToolsPageScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _toolsPageScreen = FragmentToolsPageScreenBinding.inflate(inflater, container, false)
        return _toolsPageScreen.root
    }
}