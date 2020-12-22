package com.example.riskassesmentclient.api.models

data class Company(
    val assets: Double,
    val id: Int,
    val name: String,
    val obligation: Double,
    val ownCapital: Double,
    val ownWorkingCapital: Double,
    val revenue: Double,
    val stAssets: Double,
    val stNetProfit: Double,
    val stObligations: Double,
    val userId: Int
)