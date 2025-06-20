package com.ddesk.sddemoapp.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity("matches")
data class UserEntity(

    @PrimaryKey(autoGenerate = true)
    val sr : Int? = null,
    val id: String?="",
    val cell: String?="",
    val age: String?="",
    val email: String?="",
    val gender: String?="",
    val fullName:String?="",
    val phone: String?="",
    val picture: String?="",
    val street: String?,
    val city: String?="",
    val state: String?="",

    val religion:String?="",
    val education:String?="",
    val matchScore:Float?=0.0f
)