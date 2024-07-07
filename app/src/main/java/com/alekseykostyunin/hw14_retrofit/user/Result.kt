package com.alekseykostyunin.hw14_retrofit.user

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Result(
    @Json(name = "gender") val gender: String,
    @Json(name = "name") val name: Name,
    @Json(name = "location") val location: Location,
    @Json(name = "email") val email: String,
    @Json(name = "dob") val dob: Dob,
    @Json(name = "phone") val phone: String,
    @Json(name = "picture") val picture: Picture
)
