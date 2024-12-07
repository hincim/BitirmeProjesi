package com.example.muhendisliktasarimi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ItemWordPairBinding
import com.example.muhendisliktasarimi.databinding.ItemWordRowBinding
import com.example.muhendisliktasarimi.domain.model.Histories
import com.example.muhendisliktasarimi.domain.model.WordPair
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.viewmodel.HistoriesViewModel
import com.example.muhendisliktasarimi.viewmodel.WordsViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class WordPairAdapter(private var viewModel: WordsViewModel): RecyclerView.Adapter<WordPairAdapter.WordPairViewHolder>() {


    class WordPairViewHolder(var binding: ItemWordPairBinding,

    ): RecyclerView.ViewHolder(binding.root)

    private val diffUtil = object: DiffUtil.ItemCallback<WordPair>() {
        override fun areItemsTheSame(oldItem: WordPair, newItem: WordPair): Boolean {

            return oldItem == newItem
        }

        override fun areContentsTheSame(oldItem: WordPair, newItem: WordPair): Boolean {
            return oldItem == newItem
        }
    }
    private val recyclerListDiffer = AsyncListDiffer(this,diffUtil)
    var wordsPairList: List<WordPair>
        get() = recyclerListDiffer.currentList
        set(value) = recyclerListDiffer.submitList(value)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordPairViewHolder {
        val inflater = DataBindingUtil.inflate<ItemWordPairBinding>(
            LayoutInflater.from(parent.context),
            R.layout.item_word_pair,parent,false)
        return WordPairAdapter.WordPairViewHolder(inflater)
    }

    override fun getItemCount() = wordsPairList.size
    override fun onBindViewHolder(holder: WordPairViewHolder, position: Int) {
        val wordPair = wordsPairList[position]
        holder.binding.tvEnglish.text = wordPair.english
        holder.binding.tvTurkish.text = wordPair.turkish
        holder.binding.buttonAdded.setOnClickListener {

            CoroutineScope(Dispatchers.IO).launch {
                val exists = viewModel.checkIfWordExists(wordPair.english)
                withContext(Dispatchers.Main) {
                    if (!exists) {
                        viewModel.saveInSQLiteWord(Words(wordPair.english, wordPair.turkish))
                        Snackbar.make(holder.binding.root, "Kelime eklendi", Snackbar.LENGTH_SHORT).show()


                        // Güncellenen listedeki position'un geçerliliğini kontrol et
                        if (position in wordsPairList.indices) {
                            val updatedList = wordsPairList.toMutableList().apply {
                                removeAt(position) // Pozisyona göre öğeyi kaldır
                            }
                            wordsPairList = updatedList
                        }
                    } else {
                        Snackbar.make(holder.binding.root, "Kelime zaten mevcut", Snackbar.LENGTH_SHORT).show()
                    }
                }

//                viewModel.saveInSQLiteWord(Words(wordPair.english, wordPair.turkish))
//                Toast.makeText(holder.itemView.context, "Kelime eklendi", Toast.LENGTH_SHORT).show()
//
//                val updatedList = wordsPairList.toMutableList().apply {
//                    removeAt(position)
//                }
//                wordsPairList = updatedList
            }
        }

    }

}