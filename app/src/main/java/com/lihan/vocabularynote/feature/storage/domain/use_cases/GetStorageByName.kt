package com.lihan.vocabularynote.feature.storage.domain.use_cases

import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.repository.StorageRepository
import kotlinx.coroutines.flow.Flow


class GetStorageByName(
    private val storageRepository: StorageRepository
) {

    operator fun invoke(searchText : String) : Flow<List<Storage>>{
        return storageRepository.getStorageByName(searchText)
    }
}