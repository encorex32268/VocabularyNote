package com.lihan.vocabularynote.feature.storage.presentations

import com.lihan.vocabularynote.feature.home.presentations.home.HomeEvent

sealed class StorageEvent{
    data class InsertStorage(val title : String , val description : String ) : StorageEvent()
    object GetAllStorage  : StorageEvent()
    data class SearchStorage(val text : String ) : StorageEvent()
    data class ChangeHintVisible(val visible: Boolean) : StorageEvent()

    data class GetNotesByStorageId(val storageId : Int) : StorageEvent()

}
