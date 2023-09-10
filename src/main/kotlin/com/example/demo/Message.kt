package com.example.demo

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty

// Kotlin style (test does not pass)
data class Message(val payload: String)

// Java style (makes the test pass)
//data class Message @JsonCreator constructor(@JsonProperty("payload") val payload: String)
