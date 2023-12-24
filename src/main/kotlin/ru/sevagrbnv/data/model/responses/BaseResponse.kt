package ru.sevagrbnv.data.model.responses

@kotlinx.serialization.Serializable
data class BaseResponse(
    val success: Boolean,
    val message: String
)