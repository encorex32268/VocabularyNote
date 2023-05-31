package com.lihan.vocabularynote.feature.storage.domain.use_cases

import android.util.Log
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository

class DeleteStorage(
    private val storageRepository: StorageRepository
) {
    suspend operator fun invoke(storageId : Int ){
        storageRepository.deleteStorage(storageId)
    }
}