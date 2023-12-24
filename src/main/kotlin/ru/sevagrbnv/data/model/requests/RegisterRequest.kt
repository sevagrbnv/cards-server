package ru.sevagrbnv.data.model.requests

@kotlinx.serialization.Serializable
data class RegisterRequest(
    val email: String,
    val login: String,
    val password: String,
    val firstName: String,
    val lastName: String,
    val isActive: Boolean = false,
    val role: String
)
