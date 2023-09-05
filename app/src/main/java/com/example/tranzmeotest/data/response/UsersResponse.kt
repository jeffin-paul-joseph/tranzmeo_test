package com.example.tranzmeotest.data.response

import com.example.tranzmeotest.data.model.User
import com.google.gson.annotations.SerializedName

data class UsersResponse(

    @field:SerializedName("total")
    val total: Int? = null,

    @field:SerializedName("limit")
    val limit: Int? = null,

    @field:SerializedName("skip")
    val skip: Int? = null,

    @field:SerializedName("users")
    val users: MutableList<User>? = null
)

data class Address(

    @field:SerializedName("address")
    val address: String? = null,

    @field:SerializedName("city")
    val city: String? = null,

    @field:SerializedName("postalCode")
    val postalCode: String? = null,

    @field:SerializedName("coordinates")
    val coordinates: Coordinates? = null,

    @field:SerializedName("state")
    val state: String? = null
){
    override fun toString(): String {
        return "$address, $city, $state \n$postalCode"
    }
}

data class Bank(

    @field:SerializedName("iban")
    val iban: String? = null,

    @field:SerializedName("cardExpire")
    val cardExpire: String? = null,

    @field:SerializedName("cardType")
    val cardType: String? = null,

    @field:SerializedName("currency")
    val currency: String? = null,

    @field:SerializedName("cardNumber")
    val cardNumber: String? = null
)

data class Company(

    @field:SerializedName("address")
    val address: Address? = null,

    @field:SerializedName("name")
    val name: String? = null,

    @field:SerializedName("department")
    val department: String? = null,

    @field:SerializedName("title")
    val title: String? = null
)

data class Coordinates(

    @field:SerializedName("lng")
    val lng: Float? = null,

    @field:SerializedName("lat")
    val lat: Float? = null
)

data class Hair(

    @field:SerializedName("color")
    val color: String? = null,

    @field:SerializedName("type")
    val type: String? = null
)
