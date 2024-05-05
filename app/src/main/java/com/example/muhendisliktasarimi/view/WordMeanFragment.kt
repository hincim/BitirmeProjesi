package com.example.muhendisliktasarimi.view

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.adapter.HistoriesAdapter
import com.example.muhendisliktasarimi.adapter.MeanAdapter
import com.example.muhendisliktasarimi.data.remote.dto.tdk.TdkDto
import com.example.muhendisliktasarimi.databinding.FragmentWordMeanBinding
import com.example.muhendisliktasarimi.domain.model.Histories
import com.example.muhendisliktasarimi.viewmodel.HistoriesViewModel
import com.example.muhendisliktasarimi.viewmodel.TdkViewModel

class WordMeanFragment : Fragment(R.layout.fragment_word_mean), SearchView.OnQueryTextListener {

    private lateinit var _fragmentBinding: FragmentWordMeanBinding
    private lateinit var viewModel : TdkViewModel
    private lateinit var meanAdapter: MeanAdapter
    private lateinit var historyAdapter: HistoriesAdapter
    private lateinit var list: ArrayList<TdkDto>
    private lateinit var historyViewModel: HistoriesViewModel

    override fun onResume() {
        super.onResume()

        arguments?.let {
            viewModel.getWordMean(WordMeanFragmentArgs.fromBundle(it).searchName)
        }
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _fragmentBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_word_mean,container,false)

        (activity as AppCompatActivity).setSupportActionBar(_fragmentBinding.toolbarMeans)

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {

                menuInflater.inflate(R.menu.menu_search,menu)
                val item = menu.findItem(R.id.action_search).actionView as SearchView
                item.setOnQueryTextListener(this@WordMeanFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }

        },viewLifecycleOwner, Lifecycle.State.RESUMED)
        println("onCreateView")
        return _fragmentBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        list = ArrayList()

        println("onViewCreated")

        viewModel = ViewModelProvider(requireActivity())[TdkViewModel::class.java]
        historyViewModel = ViewModelProvider(requireActivity())[HistoriesViewModel::class.java]
        meanAdapter = MeanAdapter(viewModel,list)
        historyAdapter = HistoriesAdapter(arrayListOf(),historyViewModel)
        _fragmentBinding.recyclerView.adapter = meanAdapter
        _fragmentBinding.recyclerView.layoutManager = LinearLayoutManager(requireContext())
        _fragmentBinding.textViewHistories.setOnClickListener {
            Navigation.findNavController(it).navigate(WordMeanFragmentDirections.actionWordMeanFragmentToHistoriesFragment())

        }

        getData()

        _fragmentBinding.swipeRefreshLayout.setOnRefreshListener {
            _fragmentBinding.progressBarMean.visibility = View.VISIBLE
            _fragmentBinding.textViewNoData.visibility = View.GONE
            _fragmentBinding.recyclerView.visibility = View.GONE
            getData()
            _fragmentBinding.swipeRefreshLayout.isRefreshing = false
        }
    }

    private fun getData(){
        viewModel.state.observe(viewLifecycleOwner, Observer {
            it?.let { data ->
                if (data.isLoading){
                    _fragmentBinding.progressBarMean.visibility = View.VISIBLE
                    _fragmentBinding.textViewNoData.visibility = View.GONE
                    _fragmentBinding.textViewTitle.visibility = View.GONE
                    _fragmentBinding.recyclerView.visibility = View.GONE
                }else if (data.error == "Error"){
                    _fragmentBinding.textViewNoData.visibility = View.VISIBLE
                    _fragmentBinding.recyclerView.visibility = View.GONE
                    _fragmentBinding.progressBarMean.visibility = View.GONE
                    _fragmentBinding.textViewTitle.visibility = View.GONE
                }
                else{
                    list.clear()
                    _fragmentBinding.progressBarMean.visibility = View.GONE
                    _fragmentBinding.textViewNoData.visibility = View.GONE
                    _fragmentBinding.recyclerView.visibility = View.VISIBLE
                    _fragmentBinding.textViewTitle.visibility = View.VISIBLE
                    _fragmentBinding.textViewTitle.text = viewModel.searchName.value
                    list.add(it.mean!!)
                    meanAdapter.means = list
                    meanAdapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onQueryTextSubmit(query: String?): Boolean {

        if (query != null) {
            if (query.isNotEmpty()){
                viewModel.getWordMean(query.lowercase().trim())
                val history = Histories(query.lowercase().trim())
                historyViewModel.saveInSQLite(history)
            }
        }
        return true
    }

    override fun onQueryTextChange(newText: String?): Boolean {
        return false
    }

}