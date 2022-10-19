package com.lihan.vocabularynote.core.componets.appbar

sealed class MenuID(val id : String ) {
    object Back : MenuID("back")
    object Delete : MenuID("delete")
    object Close : MenuID("close")
}