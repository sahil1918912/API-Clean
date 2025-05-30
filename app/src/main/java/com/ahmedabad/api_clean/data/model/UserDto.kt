package com.ahmedabad.api_clean.data.model

import com.google.gson.annotations.SerializedName

// User.kt (domain/model)
data class User(
    @SerializedName("id") val id: Int?,
    @SerializedName("email") val email: String?,
    @SerializedName("first_name") val firstName: String?,
    @SerializedName("last_name") val lastName: String?,
    @SerializedName("avatar") val avatar: String?,

    // These are for create/update request
    val name: String? = null,
    val job: String? = null
)



