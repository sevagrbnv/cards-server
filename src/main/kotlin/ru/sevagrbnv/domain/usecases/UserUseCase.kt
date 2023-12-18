package ru.sevagrbnv.domain.usecases

import ru.sevagrbnv.authentification.JwtService
import ru.sevagrbnv.data.model.UserModel
import ru.sevagrbnv.domain.repository.UserRepository

class UserUseCase(
    private val userRepository: UserRepository,
    private val jwtService: JwtService
) {

    suspend fun createUser(userModel: UserModel) = userRepository.insertUser(userModel = userModel)

    suspend fun findUserByEmail(email: String) = userRepository.getUserByEmail(email = email)

    fun generateToken(userModel: UserModel): String = jwtService.generateToken(user = userModel)
}