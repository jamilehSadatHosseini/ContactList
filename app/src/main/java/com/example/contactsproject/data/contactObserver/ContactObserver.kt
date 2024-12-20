package com.example.contactsproject.data.contactObserver

import android.content.ContentResolver
import android.database.ContentObserver
import android.os.Handler
import android.os.Looper
import android.provider.CallLog
import android.provider.ContactsContract
import android.util.Log
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem

class ContactObserver(
    private val contentResolver: ContentResolver,
    private val onChange: () -> Unit
) : ContentObserver(Handler(Looper.getMainLooper())) {

    override fun onChange(selfChange: Boolean) {
        super.onChange(selfChange)
        onChange()
    }

    fun startObserving() {
        contentResolver.registerContentObserver(
            ContactsContract.Contacts.CONTENT_URI,
            true,
            this
        )
    }

    fun stopObserving() {
        contentResolver.unregisterContentObserver(this)
    }

    fun fetchContacts(): List<ContactListItem> {
        val contacts = mutableListOf<ContactListItem>()
        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(
                ContactsContract.Contacts._ID,
                ContactsContract.Contacts.DISPLAY_NAME
            ),
            "${ContactsContract.Contacts.HAS_PHONE_NUMBER} = ?",
            arrayOf("1"),
            "${ContactsContract.Contacts.DISPLAY_NAME} ASC"
        )

        cursor?.use {
            val displayNameIndex = it.getColumnIndexOrThrow(ContactsContract.Contacts.DISPLAY_NAME)
            val contactIdIndex = it.getColumnIndexOrThrow(ContactsContract.Contacts._ID)

            while (it.moveToNext()) {
                val name = it.getString(displayNameIndex) ?: "Unknown"
                val contactId = it.getString(contactIdIndex)

                contacts.add(
                    ContactListItem(contactId, name)
                )
            }
        }
        return contacts
    }

    fun fetchContactById(contactId: String): ContactEntity? {
        val phoneNumber = getPhoneNumber(contactId, contentResolver) ?: return null
        val lastContacted = getLastTimeContactedByPhoneNumber(phoneNumber, contentResolver) ?: 0L

        val cursor = contentResolver.query(
            ContactsContract.Contacts.CONTENT_URI,
            arrayOf(ContactsContract.Contacts._ID),
            "${ContactsContract.Contacts._ID} = ?",
            arrayOf(contactId),
            null
        )

        cursor?.use {
            if (it.moveToFirst()) {
                val photoUri = getPhotoUri(contactId)

                return ContactEntity(
                    id = 0,
                    contactID = contactId,
                    phoneNumber = phoneNumber,
                    photoUri = photoUri,
                    lastUpdated = lastContacted
                )
            }
        }
        return null
    }

    private fun getPhoneNumber(contactId: String, contentResolver: ContentResolver): String? {
        val cursor = contentResolver.query(
            ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Phone.NUMBER),
            "${ContactsContract.CommonDataKinds.Phone.CONTACT_ID} = ?",
            arrayOf(contactId),
            null
        )

        return cursor?.use {
            if (it.moveToFirst()) {
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER))
            } else {
                null
            }
        }
    }

    private fun getLastTimeContactedByPhoneNumber(
        phoneNumber: String,
        contentResolver: ContentResolver
    ): Long? {
        val cursor = contentResolver.query(
            CallLog.Calls.CONTENT_URI,
            arrayOf(CallLog.Calls.DATE),
            "${CallLog.Calls.NUMBER} = ?",
            arrayOf(phoneNumber),
            "${CallLog.Calls.DATE} DESC"
        )

        return cursor?.use {
            if (it.moveToFirst()) {
                it.getLong(it.getColumnIndexOrThrow(CallLog.Calls.DATE))
            } else {
                null
            }
        }
    }

    private fun getPhotoUri(contactId: String): String? {
        val photoCursor = contentResolver.query(
            ContactsContract.Data.CONTENT_URI,
            arrayOf(ContactsContract.CommonDataKinds.Photo.PHOTO_URI),
            "${ContactsContract.Data.CONTACT_ID} = ? AND ${ContactsContract.Data.MIMETYPE} = ?",
            arrayOf(contactId, ContactsContract.CommonDataKinds.Photo.CONTENT_ITEM_TYPE),
            null
        )

        return photoCursor?.use {
            if (it.moveToFirst()) {
                it.getString(it.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Photo.PHOTO_URI))
            } else {
                null
            }
        }
    }
}
