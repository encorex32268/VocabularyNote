package com.lihan.vocabularynote.feature.storage.domain.use_cases

import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow


class GetStorageById(
    private val storageRepository: StorageRepository
){
    suspend operator fun invoke(storageId : Int) : Storage?{
       return storageRepository.getStorageById(storageId)
    }
}