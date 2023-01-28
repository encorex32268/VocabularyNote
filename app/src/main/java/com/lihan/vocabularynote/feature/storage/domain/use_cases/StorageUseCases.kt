package com.lihan.vocabularynote.feature.storage.domain.use_cases

class StorageUseCases(
    val deleteStorage: DeleteStorage,
    val getStorageById: GetStorageById,
    val getStorages: GetStorages,
    val insertStorage: InsertStorage,
)