package com.lihan.vocabularynote.feature.storage.presentations

import com.lihan.vocabularynote.feature.home.presentations.home.HomeEvent

sealed class StorageEvent{
    object InsertStorage : StorageEvent()
    object GetAllStorage  : StorageEvent()
    data class SearchStorage(val text : String ) : StorageEvent()
    object AToZFilter : StorageEvent()
    object NewFilter : StorageEvent()
    data class ChangeHintVisible(val visible: Boolean) : StorageEvent()

}
