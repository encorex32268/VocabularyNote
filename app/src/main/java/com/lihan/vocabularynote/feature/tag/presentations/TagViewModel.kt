package com.lihan.vocabularynote.feature.tag.presentations

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.feature.tag.domain.model.Tag
import com.lihan.vocabularynote.feature.tag.domain.use_cases.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import java.util.Date
import javax.inject.Inject

@HiltViewModel
class TagViewModel @Inject constructor (
    private val tagUseCases: TagUseCases
) : ViewModel() {

    var tagState by mutableStateOf(TagState())
    init {
        onEvent(TagEvent.GetTags)
    }
    fun onEvent(event: TagEvent){
        when(event){
            is TagEvent.GetTags ->{
                viewModelScope.launch {
                       tagUseCases.getAllTag.invoke().collectLatest {
                           tagState  = tagState.copy(
                               tags =  it
                           )
                       }

                }
            }
            is TagEvent.InertTag->{
                viewModelScope.launch {
                    tagUseCases.insertTag(
                        event.tag
                    )
                }
            }
            is TagEvent.DeleteTag->{
                viewModelScope.launch {
                    event.tag?.id?.let {
                        tagUseCases.deleteTag(it)
                    }
                }
            }


        }

    }


}