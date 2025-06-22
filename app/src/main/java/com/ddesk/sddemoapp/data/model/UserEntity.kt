package com.ddesk.sddemoapp.data.model

import android.os.Parcel
import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("matches")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val sr : Int? = null,
    val id: String?="",
    val cell: String?="",
    val age: String?="",//
    val email: String?="",//
    val gender: String?="",//
    val fullName:String?="",//
    val phone: String?="",//
    val picture: String?="",//
    val street: String?,//
    val city: String?="",//
    val state: String?="",//

    val religion:String?="",
    val education:String?="",
    val matchScore:Float?=0.0f
):Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readValue(Int::class.java.classLoader) as? Int,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readValue(Float::class.java.classLoader) as? Float
    ) {
    }

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeValue(sr)
        parcel.writeString(id)
        parcel.writeString(cell)
        parcel.writeString(age)
        parcel.writeString(email)
        parcel.writeString(gender)
        parcel.writeString(fullName)
        parcel.writeString(phone)
        parcel.writeString(picture)
        parcel.writeString(street)
        parcel.writeString(city)
        parcel.writeString(state)
        parcel.writeString(religion)
        parcel.writeString(education)
        parcel.writeValue(matchScore)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<UserEntity> {
        override fun createFromParcel(parcel: Parcel): UserEntity {
            return UserEntity(parcel)
        }

        override fun newArray(size: Int): Array<UserEntity?> {
            return arrayOfNulls(size)
        }
    }
}