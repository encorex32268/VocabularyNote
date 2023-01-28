package com.lihan.vocabularynote.feature.storage.domain.use_cases

import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetStorages(
    private val storageRepository: StorageRepository
) {
    operator fun invoke() : Flow<List<Storage>>{
        return storageRepository.getAllStorage().map {
            it.sortedBy { it.createAt }
        }
    }
}