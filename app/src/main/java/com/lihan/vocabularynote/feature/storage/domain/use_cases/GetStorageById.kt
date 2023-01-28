package com.lihan.vocabularynote.feature.storage.domain.use_cases

import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository


class GetStorageById(
    private val storageRepository: StorageRepository
){
    suspend operator fun invoke(storageId : Int){
        storageRepository.getStorageById(storageId)
    }
}