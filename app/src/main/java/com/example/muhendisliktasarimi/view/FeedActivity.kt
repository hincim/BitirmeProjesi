package com.example.muhendisliktasarimi.view

import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.muhendisliktasarimi.adapter.ScoreAdapter
import com.example.muhendisliktasarimi.databinding.ActivityFeedBinding
import com.example.muhendisliktasarimi.domain.model.GroupedScore
import com.example.muhendisliktasarimi.domain.model.Score
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.Query
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.Date

class FeedActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var db : FirebaseFirestore
    private lateinit var scoreAdapter: ScoreAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setSupportActionBar(binding.toolbar2)

        auth = Firebase.auth
        db = Firebase.firestore

        getData{
            binding.rv.layoutManager = LinearLayoutManager(this)
            scoreAdapter = ScoreAdapter(it)
            binding.rv.adapter = scoreAdapter
        }
    }

    @RequiresApi(Build.VERSION_CODES.O)
    private fun getData(callback: (List<GroupedScore>) -> Unit) {
        binding.progressBarFeed.visibility = View.VISIBLE
        binding.rv.visibility = View.GONE
        db.collection("Score")
            .orderBy("date")
            .get()
            .addOnSuccessListener { documents ->
                binding.progressBarFeed.visibility = View.GONE
                binding.rv.visibility = View.VISIBLE
                val groupedScoresMap = mutableMapOf<String, MutableList<Pair<String, String>>>()
                for (document in documents) {
                    val email = document.getString("userEmail") ?: ""
                    val date = document.getString("date") ?: ""
                    val score = document.getString("score") ?: ""

                    val scoresList = groupedScoresMap.getOrPut(email) { mutableListOf() }
                    scoresList.add(Pair(date, score))
                }

                val groupedScores = groupedScoresMap.map { GroupedScore(it.key, it.value) }
                callback(groupedScores)
            }
            .addOnFailureListener { exception ->
                Toast.makeText(this,"Skorlar alınamadı",Toast.LENGTH_SHORT).show()
            }
    }
}
