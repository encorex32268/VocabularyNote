package com.lihan.vocabularynote.feature.storage.data.repository

import android.util.Log
import com.lihan.vocabularynote.feature.storage.data.StorageDao
import com.lihan.vocabularynote.feature.storage.data.toStorage
import com.lihan.vocabularynote.feature.storage.data.toStorageEntity
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StorageRepositoryImpl(
    private val dao : StorageDao
) : StorageRepository {
    override suspend fun insert(storage: Storage) {
        dao.insert(storage.toStorageEntity())
    }
    override fun getAllStorage(): Flow<List<Storage>> {
        return dao.getAllStorage().map {
            it.map {
                it.toStorage()
            }
        }
    }

    override suspend fun deleteStorage(storageId: Int) {
        dao.deleteStorage(storageId)
    }
    override suspend fun getStorageById(storageId: Int): Storage? {
        return dao.getStorageById(storageId)?.toStorage()
    }

    override suspend fun updateStorage(storage: Storage) {
        dao.updateStorage(storage.toStorageEntity())
    }

    override fun getStorageByName(searchText: String): Flow<List<Storage>> {
        return dao.getStorageByName(searchText).map {
            it.map {
                it.toStorage()
            }
        }
    }
}