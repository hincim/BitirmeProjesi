package com.example.muhendisliktasarimi.data.remote.dto.search_words




data class Tr(
  val text: String,
  val pos: String,
  val fr: Int,
  val syn: List<Syn>?,
  val mean: List<Mean>?
)