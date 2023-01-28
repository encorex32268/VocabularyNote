package com.lihan.vocabularynote.feature.storage.domain.use_cases

import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository

class UpdateStorage(
    private val storageRepository: StorageRepository
) {
    suspend operator fun invoke(storage : Storage){
        storageRepository.updateStorage(storage)
    }
}