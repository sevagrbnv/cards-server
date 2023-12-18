package ru.sevagrbnv.domain.repository

import ru.sevagrbnv.data.model.UserModel

interface UserRepository {

    suspend fun getUserByEmail(email: String): UserModel?

    suspend fun insertUser(userModel: UserModel)
}