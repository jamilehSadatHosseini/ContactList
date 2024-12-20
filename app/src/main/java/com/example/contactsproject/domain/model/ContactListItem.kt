package com.example.contactsproject.domain.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity( tableName = "contact")
data class ContactListItem(
    @PrimaryKey
    val id: String,
    val name: String,
)