package com.medtracker.services.dto

data class UserFDTO(
    val username: String,
    val email: String,
    val password: String,
    val birthday: String
)

data class LoginFDTO(
    val email: String,
    val password: String
)