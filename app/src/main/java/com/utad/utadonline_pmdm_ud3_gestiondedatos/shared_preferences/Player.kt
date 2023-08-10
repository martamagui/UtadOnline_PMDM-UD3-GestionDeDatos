package com.utad.utadonline_pmdm_ud3_gestiondedatos.shared_preferences

data class Player(
    val name: String?,
    val acceptedTerms: Boolean?,
    val age: Int?,
    val score: Float?,
    val ranking: Long?,
    val playedPositions: Set<String>?
)
