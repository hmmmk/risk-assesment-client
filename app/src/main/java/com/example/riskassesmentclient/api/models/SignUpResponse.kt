package com.example.riskassesmentclient.api.models

data class SignUpResponse(
    val token: String,
    val user: User
)