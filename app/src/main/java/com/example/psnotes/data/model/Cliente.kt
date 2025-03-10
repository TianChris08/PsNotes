package com.example.psnotes.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Cliente(
    @PrimaryKey(autoGenerate = false)
    val id : String,
    val fiscalName: String,
    val commercialName: String

)