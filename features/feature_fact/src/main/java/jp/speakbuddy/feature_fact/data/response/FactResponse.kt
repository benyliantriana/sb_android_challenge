package jp.speakbuddy.feature_fact.data.response

import kotlinx.serialization.Serializable

@Serializable
data class FactResponse(
    val fact: String,
    val length: Int
)
