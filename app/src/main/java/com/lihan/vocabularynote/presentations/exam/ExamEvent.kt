package com.lihan.vocabularynote.presentations.exam

sealed class ExamEvent{
    object NextQuestion : ExamEvent()
    object PreQuestion : ExamEvent()
}
