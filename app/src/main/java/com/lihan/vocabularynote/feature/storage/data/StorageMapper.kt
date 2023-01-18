package com.lihan.vocabularynote.feature.storage.data

import com.lihan.vocabularynote.feature.storage.data.entity.StorageEntity
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage

fun StorageEntity.toStorage() : Storage {
    return Storage(
        id = id,
        storageId = storageId,
        name = name,
        description = description,
        createAt = createAt
    )
}

fun Storage.toStorageEntity() : StorageEntity {
    return StorageEntity(
        id = id,
        storageId = storageId,
        name = name,
        description = description,
        createAt = createAt
    )
}