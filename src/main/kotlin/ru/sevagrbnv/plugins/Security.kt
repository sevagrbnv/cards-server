package ru.sevagrbnv.plugins

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking
import ru.sevagrbnv.authentification.JwtService
import ru.sevagrbnv.data.model.RoleModel
import ru.sevagrbnv.data.model.UserModel
import ru.sevagrbnv.data.repository.UserRepositoryImpl
import ru.sevagrbnv.domain.repository.UserRepository
import ru.sevagrbnv.domain.usecases.UserUseCase

fun Application.configureSecurity() {
    val jwtService = JwtService()
    val repository = UserRepositoryImpl()
    val userUseCase = UserUseCase(repository, jwtService)

    runBlocking {
        userUseCase.createUser(
            UserModel(
                id = 1,
                email = "1@gmail.com",
                login = "login",
                password = "1",
                firstName = "first",
                lastName = "last",
                isActive = true,
                role = RoleModel.MODERATOR
            )

        )
    }

    authentication {
        jwt("jwt") {
            verifier(jwtService.getVerifier())
            realm = "Service server"
            validate {
                val payload = it.payload
                val email = payload.getClaim("email").asString()
                val user = userUseCase.findUserByEmail(email = email)
                user
            }
        }
    }
}
