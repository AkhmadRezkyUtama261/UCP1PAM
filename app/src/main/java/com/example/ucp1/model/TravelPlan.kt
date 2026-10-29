package com.example.ucp1.model

/**
 * Data model untuk menyimpan seluruh data rencana perjalanan yang diinput pengguna.
 * Model ini digunakan untuk menampung dan merepresentasikan data yang diparsing
 * antarhalaman dari formulir menuju halaman detail.
 */
data class TravelPlan(
    val nama: String = "",
    val alamat: String = "",
    val origin: String = "",
    val departure: String = "",
    val arrival: String = "",
    val transport: String = ""
)