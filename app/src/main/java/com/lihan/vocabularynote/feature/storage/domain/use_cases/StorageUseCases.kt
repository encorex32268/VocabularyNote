package com.lihan.vocabularynote.feature.storage.domain.use_cases

import com.lihan.vocabularynote.feature.storage.presentations.edit.StorageEditEvent

class StorageUseCases(
    val deleteStorage: DeleteStorage,
    val getStorageById: GetStorageById,
    val getStorages: GetStorages,
    val insertStorage: InsertStorage,
    val updateStorage : UpdateStorage
)