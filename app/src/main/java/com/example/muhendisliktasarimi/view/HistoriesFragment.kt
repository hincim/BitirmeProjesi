package com.example.muhendisliktasarimi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.adapter.HistoriesAdapter
import com.example.muhendisliktasarimi.databinding.FragmentHistoriesBinding
import com.example.muhendisliktasarimi.viewmodel.HistoriesViewModel


class HistoriesFragment : Fragment(R.layout.fragment_histories) {
    private var _fragmentBinding: FragmentHistoriesBinding? = null
    private lateinit var historiesAdapter: HistoriesAdapter
    private lateinit var viewModel: HistoriesViewModel
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(requireActivity())[HistoriesViewModel::class.java]

        val binding = FragmentHistoriesBinding.bind(view)
        _fragmentBinding = binding
        binding.rv.layoutManager = LinearLayoutManager(requireContext())
        viewModel.getDataFromSQLite()
        viewModel.histories.observe(viewLifecycleOwner, Observer {
            it?.let {
                historiesAdapter = HistoriesAdapter(it,viewModel,/*viewModelTdk*/)
                binding.rv.adapter = historiesAdapter
            }
        })

        binding.textViewAllDelete.setOnClickListener {
            viewModel.deleteAllHistory()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _fragmentBinding = null
    }
}