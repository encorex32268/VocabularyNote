package com.lihan.vocabularynote.presentations.tag

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.domain.use_cases.tag.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor (
    private val tagUseCases: TagUseCases
) : ViewModel() {

    var tagState by mutableStateOf(TagState())

    fun onEvent(event: TagEvent){
        when(event){
            TagEvent.GetTags->{
                viewModelScope.launch {
                       tagUseCases.getAllTag.invoke().collectLatest {
                           tagState  = tagState.copy(
                               tags =  it
                           )
                       }

                }
            }
        }

    }


}