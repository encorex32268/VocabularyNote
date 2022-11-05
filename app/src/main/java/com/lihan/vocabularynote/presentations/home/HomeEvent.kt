package com.lihan.vocabularynote.presentations.home

import androidx.compose.ui.graphics.Color

sealed class HomeEvent{
    data class SortByColor(val color: Color) : HomeEvent()
    object GetNotes : HomeEvent()
}
