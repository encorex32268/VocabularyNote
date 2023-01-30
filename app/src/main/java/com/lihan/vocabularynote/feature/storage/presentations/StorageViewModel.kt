package com.lihan.vocabularynote.feature.storage.presentations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.core.util.UiEvent
import com.lihan.vocabularynote.feature.storage.domain.mode.Storage
import com.lihan.vocabularynote.feature.storage.domain.use_cases.StorageUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.ZoneOffset
import javax.inject.Inject

@HiltViewModel
class StorageViewModel @Inject constructor(
    private val storageUseCases: StorageUseCases,
)  : ViewModel(){

    private var getStoragesJob: Job? = null

    var storageState by mutableStateOf(StorageState())
    private val _uiEvent  = Channel<UiEvent>()
    var uiEvent = _uiEvent.receiveAsFlow()

    init {
        refreshData()
    }
    fun onEvent(event : StorageEvent){
        when(event){
            is StorageEvent.SearchStorage->{
                if (event.text.isEmpty()){
                    storageState = storageState.copy(
                        searchText = event.text
                    )
                    refreshData()
                }else{
                    storageState = storageState.copy(
                        searchText = event.text,
                        items = storageState.items.filter {
                            it.name.contains(event.text)
                        }
                    )
                }
            }
            is StorageEvent.GetAllStorage->{
                refreshData()
            }
            is StorageEvent.ChangeHintVisible->{

            }
            is StorageEvent.InsertStorage->{
                viewModelScope.launch {
                    storageUseCases.insertStorage(
                        Storage(
                            storageId = System.currentTimeMillis().toInt() + 1024,
                            name = "New Storage",
                            description = "description...",
                            createAt = Instant.now().toEpochMilli()
                        )
                    )
                    _uiEvent.send(UiEvent.Success)
                }
                refreshData()
            }
        }
    }

    private fun refreshData(){
        getStoragesJob?.cancel()
        getStoragesJob = storageUseCases.getStorages.invoke().onEach {
            storageState = storageState.copy(
                items = it
            )
        }.launchIn(viewModelScope)
    }


}