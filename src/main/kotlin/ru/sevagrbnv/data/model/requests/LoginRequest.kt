package ru.sevagrbnv.data.model.requests

@kotlinx.serialization.Serializable
data class LoginRequest(
    val email: String,
    val password: String
)