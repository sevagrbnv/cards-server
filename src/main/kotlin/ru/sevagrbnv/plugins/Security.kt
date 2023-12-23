package ru.sevagrbnv.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*
import io.ktor.server.auth.jwt.*
import kotlinx.coroutines.runBlocking
import ru.sevagrbnv.data.model.RoleModel
import ru.sevagrbnv.data.model.UserModel
import ru.sevagrbnv.domain.usecases.UserUseCase

fun Application.configureSecurity(userUseCase: UserUseCase) {

    authentication {
        jwt("jwt") {
            verifier(userUseCase.getJwtVerifier())
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
