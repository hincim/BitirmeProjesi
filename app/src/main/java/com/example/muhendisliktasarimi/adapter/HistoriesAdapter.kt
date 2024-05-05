package com.example.muhendisliktasarimi.adapter

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ItemHistoriesRowBinding
import com.example.muhendisliktasarimi.domain.model.Histories
import com.example.muhendisliktasarimi.view.HistoriesFragmentDirections
import com.example.muhendisliktasarimi.viewmodel.HistoriesViewModel
import java.sql.Date


class HistoriesAdapter(
    private var histories: List<Histories>,
    private var viewModel: HistoriesViewModel,
): RecyclerView.Adapter<HistoriesAdapter.HistoriesViewHolder>(){

    private val _searchName = MutableLiveData<String>()
    val searchName: LiveData<String>
        get() = _searchName
    inner class HistoriesViewHolder(val binding: ItemHistoriesRowBinding) :
        RecyclerView.ViewHolder(binding.root)
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoriesViewHolder {
            val inflater = DataBindingUtil.inflate<ItemHistoriesRowBinding>(
                LayoutInflater.from(parent.context),
                R.layout.item_histories_row, parent, false
            )
            return HistoriesViewHolder(inflater)
        }

        override fun getItemCount(): Int {
            return histories.size
        }

    override fun onBindViewHolder(holder: HistoriesViewHolder, position: Int) {
        val date = Date(histories[position].date)
        holder.binding.textViewHistories.text = "${histories[position].pastName}\n${date}"
        holder.binding.imageViewDelete.setOnClickListener {
            viewModel.deleteHistory(histories[position])
        }
        holder.binding.cardRow.setOnClickListener {
            _searchName.value = histories[position].pastName!!
            Navigation.findNavController(it).navigate(HistoriesFragmentDirections.actionHistoriesFragmentToWordMeanFragment(
                _searchName.value!!
            ))
        }
    }
}