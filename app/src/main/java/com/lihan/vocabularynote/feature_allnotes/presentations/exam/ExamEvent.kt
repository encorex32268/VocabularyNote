package com.lihan.vocabularynote.feature_allnotes.presentations.exam

sealed class ExamEvent{
    object NextQuestion : ExamEvent()
    object PreQuestion : ExamEvent()
}
