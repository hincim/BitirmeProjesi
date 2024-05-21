package com.example.muhendisliktasarimi.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ScoreRowBinding
import com.example.muhendisliktasarimi.databinding.UserScoreGroupBinding
import com.example.muhendisliktasarimi.domain.model.GroupedScore

class ScoreAdapter(private val groupedScoreList: List<GroupedScore>): RecyclerView.Adapter<ScoreAdapter.RvViewHolder>() {

    class RvViewHolder(val binding: UserScoreGroupBinding) : RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val view = UserScoreGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvViewHolder(view)
    }

    override fun getItemCount(): Int {
        return groupedScoreList.size
    }

    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {


        val groupedScore = groupedScoreList[position]
        val username = groupedScore.userEmail.substringBefore('@')
        holder.binding.userEmailText.text = username

        // Clear previous views
        holder.binding.scoresContainer.removeAllViews()
        var i = 0
        for (score in groupedScore.scores) {
            i +=1
            val scoreRowBinding = ScoreRowBinding.inflate(LayoutInflater.from(holder.binding.scoresContainer.context), holder.binding.scoresContainer, false)
            if (i%2 == 1){
                scoreRowBinding.row.background = ContextCompat.getDrawable(holder.itemView.context, R.color.gray)
            }
            scoreRowBinding.rvScoreText.text = score.second
            scoreRowBinding.date.text = score.first.split(" ")[0]
            holder.binding.scoresContainer.addView(scoreRowBinding.root)
        }
    }

}