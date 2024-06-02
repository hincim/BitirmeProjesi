package com.example.muhendisliktasarimi.adapter

import android.animation.ObjectAnimator
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.muhendisliktasarimi.R
import com.example.muhendisliktasarimi.databinding.ScoreRowBinding
import com.example.muhendisliktasarimi.databinding.UserScoreGroupBinding
import com.example.muhendisliktasarimi.domain.model.GroupedScore

class ScoreAdapter(private val groupedScoreList: List<GroupedScore>): RecyclerView.Adapter<ScoreAdapter.RvViewHolder>() {

    class RvViewHolder(val binding: UserScoreGroupBinding) : RecyclerView.ViewHolder(binding.root)
    private var groupedScoreSize  = 0
    private var scoreList = mutableSetOf<Int>()
    private var date = mutableSetOf<String>()
    private var list = mutableListOf<String>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RvViewHolder {
        val view = UserScoreGroupBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RvViewHolder(view)
    }

    override fun getItemCount(): Int {
        groupedScoreSize = groupedScoreList.size
        return groupedScoreList.size
    }



    override fun onBindViewHolder(holder: RvViewHolder, position: Int) {

        val groupedScore = groupedScoreList[position]
        val username = groupedScore.userEmail.substringBefore('@')
        holder.binding.userEmailText.text = username
        list.add(username)

        ObjectAnimator.ofFloat(holder.binding.scoresContainer,"translationX",-1500.0f, 0.0f).apply {
            duration = 400
            interpolator  = OvershootInterpolator()

        }.start()
        ObjectAnimator.ofFloat(holder.binding.userEmailText,"alpha",-1.0f,1.0f).apply {
            duration = 1000

        }.start()

        holder.binding.scoresContainer.removeAllViews()
        var i = 0
        scoreList.clear()
        date.clear()
        for (score in groupedScore.scores){
            scoreList.add(score.second.toInt())
            date.add(score.first)
        }

        val scoreRowBinding = ScoreRowBinding.inflate(LayoutInflater.from(holder.binding.scoresContainer.context), holder.binding.scoresContainer, false)
        if (position % 2 == 1) scoreRowBinding.row.background = ContextCompat.getDrawable(holder.itemView.context, R.color.gray) else scoreRowBinding.row.background = ContextCompat.getDrawable(holder.itemView.context, R.color.white)
        scoreRowBinding.rvScoreText.text = "Ortalama başarı: %"+scoreList.average().toInt()
        scoreRowBinding.date.text = "\nEn son giriş: "+date.maxOrNull()!!.split(" ")[0]
        holder.binding.scoresContainer.addView(scoreRowBinding.root)

        /* for (score in groupedScore.scores) {
             i +=1
             val scoreRowBinding = ScoreRowBinding.inflate(LayoutInflater.from(holder.binding.scoresContainer.context), holder.binding.scoresContainer, false)
             if (i%2 == 1){
                 scoreRowBinding.row.background = ContextCompat.getDrawable(holder.itemView.context, R.color.gray)
             }
             scoreRowBinding.rvScoreText.text = score.second
             scoreRowBinding.date.text = score.first.split(" ")[0]
             holder.binding.scoresContainer.addView(scoreRowBinding.root)

         }*/
    }

}