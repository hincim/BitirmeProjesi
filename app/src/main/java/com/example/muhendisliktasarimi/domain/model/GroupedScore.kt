package com.example.muhendisliktasarimi.domain.model

data class GroupedScore(
    val userEmail: String,
    val scores: List<Pair<String, String>>
    )