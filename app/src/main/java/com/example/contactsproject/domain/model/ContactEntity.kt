package com.example.contactsproject.domain.model

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "contactDetail", foreignKeys = [
        ForeignKey(
            entity = ContactListItem::class,
            parentColumns = ["id"],
            childColumns = ["contactID"],
            onDelete = ForeignKey.CASCADE
        )
    ]
)
data class ContactEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val contactID: String,
    val phoneNumber: String,
    val photoUri: String?,
    val lastUpdated: Long
)
