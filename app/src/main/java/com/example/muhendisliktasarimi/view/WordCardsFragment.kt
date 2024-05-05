package com.example.muhendisliktasarimi.view

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearSnapHelper
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SnapHelper
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.adapter.ExerciseAdapter
import com.example.muhendisliktasarimi.databinding.FragmentWordCardsBinding
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel


class WordCardsFragment : Fragment(R.layout.fragment_word_cards) {

    private lateinit var _fragmentBinding: FragmentWordCardsBinding
    private var adapter =  ExerciseAdapter()
    private lateinit var viewModel: WordsViewModel
    private lateinit var layoutManager: GridLayoutManager
    private lateinit var sharedPreferences: SharedPreferences
    private val KEY_LAST_VISIBLE_POSITION = "last_visible_position"
    private val PREFS_NAME = "MyPrefs"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_word_cards,container,false)

        return _fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[WordsViewModel::class.java]
        sharedPreferences = requireContext().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

        subscribeToObserves()

        _fragmentBinding.rv.adapter = adapter
        layoutManager = GridLayoutManager(requireContext(), 1, GridLayoutManager.HORIZONTAL, false)

        _fragmentBinding.rv.layoutManager = layoutManager


        val snapHelper: SnapHelper = LinearSnapHelper()
        snapHelper.attachToRecyclerView(_fragmentBinding.rv)

        val lastVisibleItemPosition = sharedPreferences.getInt(KEY_LAST_VISIBLE_POSITION, 0)
        _fragmentBinding.rv.post {
            _fragmentBinding.rv.scrollToPosition(lastVisibleItemPosition)
        }
    }


    private fun subscribeToObserves() {
        viewModel.getDataFromSQLite()

        viewModel.words.observe(viewLifecycleOwner, Observer {
            it?.let {
                adapter.words = it
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        // RecyclerView'in görüntüsü değiştiğinde pozisyonu kaydet
        val firstVisibleItemPosition = layoutManager.findFirstVisibleItemPosition()
        val editor = sharedPreferences.edit()
        editor.putInt(KEY_LAST_VISIBLE_POSITION, firstVisibleItemPosition)
        editor.apply()
    }
}