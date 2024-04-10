package com.example.muhendisliktasarimi.data.remote.dto.tdk

import com.example.muhendisliktasarimi.data.remote.dto.tdk.AnlamlarListe

data class TdkDtoItem(
    val anlam_gor: String,
    val anlam_say: String,
    val anlamlarListe: List<AnlamlarListe>,
    val birlesikler: String,
    val cesit: String,
    val cesit_say: String,
    val cogul_mu: String,
    val egik_mi: String,
    val font: Any,
    val gosterim_tarihi: Any,
    val kac: String,
    val kelime_no: String,
    val lisan: String,
    val lisan_html: Any,
    val lisan_kodu: String,
    val madde: String,
    val madde_duz: String,
    val madde_html: String,
    val madde_id: String,
    val on_taki: Any,
    val on_taki_html: Any,
    val ozel_mi: String,
    val taki: Any,
    val telaffuz: Any,
    val telaffuz_html: Any
)

