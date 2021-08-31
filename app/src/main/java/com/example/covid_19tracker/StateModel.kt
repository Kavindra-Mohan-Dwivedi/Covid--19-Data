package com.example.covid_19tracker

data class StateData(
    val stateName: String,
    val recovered: Int,
    val deaths: Int,
    val cases: Int
) {
}