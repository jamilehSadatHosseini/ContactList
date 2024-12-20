package com.example.contactsproject.di

import android.app.Application
import android.content.ContentResolver
import android.content.Context
import androidx.room.Room
import com.example.contactsproject.MyApplication
import com.example.contactsproject.data.contactObserver.ContactsObserverDataSource
import com.example.contactsproject.data.contactObserver.ContactsObserverDataSourceImp
import com.example.contactsproject.data.local.LocalDataSource
import com.example.contactsproject.data.local.LocalDataSourceImp
import com.example.contactsproject.data.local.dao.ContactDao
import com.example.contactsproject.data.local.database.ContactsDataBase
import com.example.contactsproject.data.repository.RepositoryImp
import com.example.contactsproject.domain.repository.Repository
import com.example.contactsproject.domain.usecase.ContactsChanged
import com.example.contactsproject.domain.usecase.GetContactDetail
import com.example.contactsproject.domain.usecase.GetContactItem
import com.example.contactsproject.domain.usecase.GetContacts
import com.example.contactsproject.domain.usecase.ObserveContacts
import com.example.contactsproject.domain.usecase.StopObservingContacts
import com.example.contactsproject.domain.usecase.UseCases
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    fun provideContext(application: MyApplication): Context {
        return application.applicationContext
    }

    @Provides
    @Singleton
    fun provideContentResolver(@ApplicationContext context: Context): ContentResolver {
        return context.contentResolver
    }

    @Provides
    @Singleton
    fun provideContactsObserverDataSource(contentResolver: ContentResolver): ContactsObserverDataSource {
        return ContactsObserverDataSourceImp(contentResolver)
    }

    @Provides
    @Singleton
    fun provideDataBases(application: Application): ContactsDataBase {
        return Room.databaseBuilder(
            context = application,
            klass = ContactsDataBase::class.java,
            name = "contacts_database"
        ).fallbackToDestructiveMigration().build()

    }

    @Singleton
    @Provides
    fun provideContactDao(dataBase: ContactsDataBase) = dataBase.contactDao

    @Provides
    @Singleton
    fun provideLocalDataSource(contactDao: ContactDao): LocalDataSource =
        LocalDataSourceImp(contactDao)

    @Provides
    fun provideRepository(
        localDataSource: LocalDataSource,
        contactsObserverDataSource: ContactsObserverDataSource
    ): Repository {
        return RepositoryImp(localDataSource, contactsObserverDataSource)
    }

    @Provides
    fun provideUseCases(repository: Repository): UseCases {
        return UseCases(
            getContacts = GetContacts(repository),
            getContactItem = GetContactItem(repository),
            getContactDetail = GetContactDetail(repository),
            contactsChanged  = ContactsChanged(repository),
            observingContacts = ObserveContacts(repository),
            stopObservingContacts = StopObservingContacts(repository)
        )
    }
}
