package com.example.contactsproject.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.contactsproject.data.local.dao.ContactDao
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem


@Database(entities = [ContactListItem::class, ContactEntity::class], version = 1)

abstract class ContactsDataBase : RoomDatabase() {
    abstract val contactDao: ContactDao
}