package jp.speakbuddy.feature_fact.data.ui

import kotlinx.serialization.Serializable

@Serializable
data class FactUiData(
    val fact: String,
    val length: Int,
    val isFavorite: Boolean
)
