package com.lihan.vocabularynote.presentations.home

import androidx.compose.ui.graphics.Color

sealed class HomeEvent{
    data class SortByColor(val color: Color) : HomeEvent()
    data class SearchByString(val string : String) : HomeEvent()
    data class ChangeHintVisible(val visible: Boolean) : HomeEvent()
    object GetNotes : HomeEvent()
}
