package com.lihan.vocabularynote.feature.home.presentations.exam

sealed class ExamEvent{
    object NextQuestion : ExamEvent()
    object PreQuestion : ExamEvent()
}
