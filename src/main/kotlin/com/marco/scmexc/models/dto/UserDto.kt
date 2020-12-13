package com.marco.scmexc.models.dto

data class UserDto (
    val username: String,
    val firstName: String,
    val lastName: String,
    val password: String,
    val email: String,
    val role: String?
)
