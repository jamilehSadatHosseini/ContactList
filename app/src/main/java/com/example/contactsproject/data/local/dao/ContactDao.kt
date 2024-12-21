package com.example.contactsproject.data.local.dao

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Upsert
import com.example.contactsproject.domain.model.ContactEntity
import com.example.contactsproject.domain.model.ContactListItem

@Dao
interface ContactDao{
    @Query("SELECT * FROM contact")
    fun getContacts(): LiveData<List<ContactListItem>>

    @Query("SELECT * FROM contact WHERE id = :Id")
    fun getContactByID( Id:String): LiveData<ContactListItem>

    @Query("SELECT * FROM contactDetail WHERE contactID = :Id")
    fun getContactDetailByID( Id:String): LiveData<ContactEntity?>

    @Query("SELECT * FROM contactDetail WHERE contactID = :Id")
     fun existConcactDetail( Id:String): ContactEntity?
    @Upsert
    suspend fun insertOrUpdateContacts(contacts: List<ContactListItem>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertOrUpdateContactDetail(contactDetails: ContactEntity)

        @Query("""
        UPDATE contactDetail 
        SET phoneNumber = :phoneNumber, 
            photoUri = :photoUri, 
            lastUpdated = :lastUpdated
        WHERE contactID = :contactID
    """)
        suspend fun updateContactDetails(
            contactID: String,
            phoneNumber: String,
            photoUri: String?,
            lastUpdated: Long
        )

        suspend fun updateContactDetails(contactDetails: ContactEntity) {
            val existingContact = existConcactDetail(contactDetails.contactID)
            if (existingContact != null) {
            updateContactDetails(
                contactID = contactDetails.contactID,
                phoneNumber = contactDetails.phoneNumber,
                photoUri = contactDetails.photoUri,
                lastUpdated = contactDetails.lastUpdated
            )}
            else{
                insertOrUpdateContactDetail(contactDetails)
            }
        }
    @Query("DELETE FROM contact")
    suspend fun deleteAllContacts()

}