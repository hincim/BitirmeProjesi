package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.muhendisliktasarimi.databinding.FragmentToolsPageScreenBinding


class ToolsPageScreen : Fragment() {

    private lateinit var _toolsPageScreen: FragmentToolsPageScreenBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _toolsPageScreen = FragmentToolsPageScreenBinding.inflate(inflater, container, false)

        _toolsPageScreen.cardMean.setOnClickListener {
            findNavController().navigate(ToolsPageScreenDirections.actionToolsPageScreenToFragmentWordMeanFragment(""))
        }
        return _toolsPageScreen.root
    }
}