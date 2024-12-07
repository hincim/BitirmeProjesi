package com.example.muhendisliktasarimi.data.remote.dto.search_words


data class Def(
  val text: String,
  val pos: String,
  val ts: String,
  val tr: List<Tr>
)