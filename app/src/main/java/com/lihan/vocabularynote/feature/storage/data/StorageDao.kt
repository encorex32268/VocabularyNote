package com.lihan.vocabularynote.feature.storage.data

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.lihan.vocabularynote.feature.storage.data.entity.StorageEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface StorageDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(storageEntity : StorageEntity)

    @Query("select * from StorageEntity")
    fun getAllStorage() : Flow<List<StorageEntity>>

    @Query("delete from StorageEntity where storageId = :storageId")
    suspend fun deleteStorage(storageId : Int)

    @Query("select * from StorageEntity where id = :storageId")
    suspend fun getStorageById(storageId: Int) : StorageEntity?

    @Update
    suspend fun updateStorage(storageEntity: StorageEntity)

     @Query("select * from StorageEntity where name like :searchText ")
     fun getStorageByName(searchText : String) : Flow<List<StorageEntity>>

}