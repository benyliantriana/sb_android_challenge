package jp.speakbuddy.edisonandroidexercise.data

import kotlinx.serialization.Serializable

@Serializable
data class Fact(
    val fact: String,
    val length: Int
)
