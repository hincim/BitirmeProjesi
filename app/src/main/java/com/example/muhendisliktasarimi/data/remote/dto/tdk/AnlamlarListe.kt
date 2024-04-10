package com.example.muhendisliktasarimi.data.remote.dto.tdk


data class AnlamlarListe(
    val anlam: String,
    val anlam_html: String,
    val anlam_id: String,
    val anlam_sira: String,
    val fiil: String,
    val gos: String,
    val gos_kelime: String,
    val gos_kultur: String,
    val madde_id: String,
    val orneklerListe: List<OrneklerListe>,
    val ozelliklerListe: List<OzelliklerListe>,
    val tipkes: String
)

