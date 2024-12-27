package com.example.feature_fact.data

import kotlinx.serialization.Serializable

@Serializable
data class Fact(
    val fact: String,
    val length: Int
)
