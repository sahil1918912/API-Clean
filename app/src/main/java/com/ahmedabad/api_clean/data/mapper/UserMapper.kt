package com.ahmedabad.api_clean.data.mapper

import com.ahmedabad.api_clean.data.dto.CreateUserDto
import com.ahmedabad.api_clean.data.dto.UserDto
import com.ahmedabad.api_clean.domain.model.UserModel

fun UserDto.toUserModel(): UserModel {
    return UserModel(
        id = _id,
        name = name,
        role = role
    )
}

fun UserModel.toCreateUserDto(): CreateUserDto {
    return CreateUserDto(
        name = name,
        role = role
    )
}