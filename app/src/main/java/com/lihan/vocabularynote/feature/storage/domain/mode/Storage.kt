package com.lihan.vocabularynote.feature.storage.domain.mode

import android.os.Parcel
import android.os.Parcelable

data class Storage(
    val id : Int ?=null,
    val storageId : Int = 0,
    val name : String = "",
    val description : String ="",
    val createAt : Long = 0,
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readInt(),
        parcel.readString()?:"",
        parcel.readString()?:"",
        parcel.readLong()
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(id)
        parcel.writeInt(storageId)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeLong(createAt)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Storage> {
        override fun createFromParcel(parcel: Parcel): Storage {
            return Storage(parcel)
        }

        override fun newArray(size: Int): Array<Storage?> {
            return arrayOfNulls(size)
        }
    }
}