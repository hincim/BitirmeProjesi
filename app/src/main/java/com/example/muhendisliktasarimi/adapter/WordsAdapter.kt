package com.example.muhendisliktasarimi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ItemWordRowBinding
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.view.HomePageScreenDirections

class WordsAdapter: RecyclerView.Adapter<WordsAdapter.WordsViewHolder>(){

    class WordsViewHolder(var binding: ItemWordRowBinding): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<Words>() {
        override fun areItemsTheSame(oldItem: Words, newItem: Words): Boolean {

            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: Words, newItem: Words): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var words: List<Words>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordsViewHolder {
        val inflater = DataBindingUtil.inflate<ItemWordRowBinding>(LayoutInflater.from(parent.context),
            R.layout.item_word_row,parent,false)
        return WordsViewHolder(inflater)
    }

    override fun onBindViewHolder(holder: WordsViewHolder, position: Int) {
       holder.binding.textViewEng.text = words[position].engWord
       holder.binding.textViewTr.text = words[position].trWord
        println(words[position].uuid)

        holder.binding.cardRow.setOnClickListener {
            Navigation.findNavController(it).navigate(HomePageScreenDirections.actionHomePageScreenToWordUpdateFragment(words[position].trWord!!,words[position].engWord!!,words[position].uuid))
            println("Tıklanan ${words[position].uuid}")
        }
    }

    override fun getItemCount(): Int {
        return words.size
    }
}