package com.example.muhendisliktasarimi.view

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.core.view.MenuProvider
import androidx.lifecycle.Lifecycle
import androidx.navigation.fragment.findNavController
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.FragmentCommPageScreenBinding
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth


class CommPageScreen : Fragment() {

    private lateinit var _comePageScreen: FragmentCommPageScreenBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _comePageScreen = FragmentCommPageScreenBinding.inflate(inflater, container, false)
        firebaseAuth = FirebaseAuth.getInstance()

        _comePageScreen.buttonSignOut.setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(requireContext(),SignInActivity::class.java))
            finishAffinity(requireActivity())
        }
        return _comePageScreen.root
    }
}