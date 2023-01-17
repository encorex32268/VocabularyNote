package com.lihan.vocabularynote.feature_tag.presentations.add

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.lihan.vocabularynote.feature_tag.domain.model.Tag
import com.lihan.vocabularynote.feature_tag.domain.use_cases.InsertTag
import com.lihan.vocabularynote.feature_tag.domain.use_cases.TagUseCases
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import java.util.*
import javax.inject.Inject

@HiltViewModel
class TagAddViewModel @Inject constructor(
    private val tagUseCases : TagUseCases
) : ViewModel(){

    var tagAddState  by mutableStateOf(
        Tag(name = "", color = Color.Blue.toArgb(), createAt = 0)
    )
    fun onEvent(event: TagAddEvent){
        when(event){
            is TagAddEvent.InitTag->{
                val eventTag = event.tag
                if (eventTag.id != null){
                    tagAddState = tagAddState.copy(
                        id = eventTag.id,
                        name = eventTag.name,
                        color = eventTag.color,
                        createAt = eventTag.createAt
                    )
                }
            }

            is TagAddEvent.ColorPicked->{
                tagAddState = tagAddState.copy(
                    color = event.color
                )
            }
            is TagAddEvent.InputTagName->{
                tagAddState = tagAddState.copy(
                    name = event.name
                )
            }
            is TagAddEvent.InertTag->{
                if (tagAddState.id == null){
                    tagAddState = tagAddState.copy(
                        createAt = Date().time
                    )
                }
                viewModelScope.launch {
                    tagUseCases.insertTag(tagAddState)
                }
            }
        }
    }
}