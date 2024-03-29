package com.lihan.vocabularynote.feature.storage.domain.repository


import com.lihan.vocabularynote.feature.storage.data.entity.StorageEntity
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import kotlinx.coroutines.flow.Flow

interface StorageRepository {

    suspend fun insert(storage: Storage)
    fun getAllStorage() : Flow<List<Storage>>
    suspend fun deleteStorage(storageId : Int)
    suspend fun getStorageById(storageId: Int) : Storage?
    suspend fun updateStorage(storage : Storage)
    fun getStorageByName(searchText : String) : Flow<List<Storage>>

}