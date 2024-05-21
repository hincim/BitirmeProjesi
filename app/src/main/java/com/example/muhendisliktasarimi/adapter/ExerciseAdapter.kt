package com.example.muhendisliktasarimi.adapter

import android.animation.ObjectAnimator
import android.content.Intent
import android.os.CountDownTimer
import android.speech.tts.TextToSpeech
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.MainActivity
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ItemMovieRowBinding
import com.example.muhendisliktasarimi.databinding.ItemWordRowBinding
import com.example.muhendisliktasarimi.databinding.ItemWordcardsRowBinding
import com.example.muhendisliktasarimi.domain.model.Words
import com.example.muhendisliktasarimi.state.MoviesState
import java.util.Locale

class ExerciseAdapter: RecyclerView.Adapter<ExerciseAdapter.CardViewHolder>() {

    class CardViewHolder(var binding: ItemWordcardsRowBinding): RecyclerView.ViewHolder(binding.root)
    private lateinit var tts: TextToSpeech
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
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CardViewHolder {
        val view = DataBindingUtil.inflate<ItemWordcardsRowBinding>(LayoutInflater.from(parent.context),
            R.layout.item_wordcards_row,parent,false)
        return CardViewHolder(view)
    }

    override fun getItemCount(): Int {
        return words.size
    }

    override fun onBindViewHolder(holder: CardViewHolder, position: Int) {
        holder.binding.textViewTr.text = words[position].trWord
        holder.binding.textViewEng.text = words[position].engWord
        holder.binding.imageViewSound.setOnClickListener {
            tts = TextToSpeech(holder.itemView.context, TextToSpeech.OnInitListener {
                if (it == TextToSpeech.SUCCESS){
                    tts.language = Locale.US
                    tts.setSpeechRate(1.0f)
                    tts.speak(words[position].engWord,TextToSpeech.QUEUE_ADD,null)
                }
            })
        }
        holder.binding.cardRow.setOnClickListener {

            ObjectAnimator.ofFloat(holder.binding.card,"translationY",500.0f, 0.0f).apply {
                duration = 300
                interpolator  = OvershootInterpolator()

            }.start()


            if (holder.binding.textViewEng.visibility == View.VISIBLE){
                holder.binding.textViewEng.visibility = View.GONE
                holder.binding.textViewTr.visibility = View.VISIBLE
                ObjectAnimator.ofFloat(holder.binding.textViewEng,"alpha",1.0f,0.0f).apply {
                    duration = 400

                }.start()
                ObjectAnimator.ofFloat(holder.binding.textViewTr,"alpha",0.0f,1.0f).apply {
                    duration = 400

                }.start()
            }else{
                holder.binding.textViewEng.visibility = View.VISIBLE
                holder.binding.textViewTr.visibility = View.GONE
                ObjectAnimator.ofFloat(holder.binding.textViewTr,"alpha",1.0f,0.0f).apply {
                    duration = 400

                }.start()
                ObjectAnimator.ofFloat(holder.binding.textViewEng,"alpha",0.0f,1.0f).apply {
                    duration = 400

                }.start()
            }
        }

    }

}