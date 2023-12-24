package ru.sevagrbnv.routes

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.sevagrbnv.authentification.hash
import ru.sevagrbnv.data.model.UserModel
import ru.sevagrbnv.data.model.getRoleByString
import ru.sevagrbnv.data.model.requests.LoginRequest
import ru.sevagrbnv.data.model.requests.RegisterRequest
import ru.sevagrbnv.data.model.responses.BaseResponse
import ru.sevagrbnv.domain.usecases.UserUseCase
import ru.sevagrbnv.utils.Constants

fun Route.userRoute(userUseCase: UserUseCase) {

    val hashFunction = {p: String -> hash(password = p) }

    post("api/v1/signup") {
        val registerRequest = call.receiveNullable<RegisterRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = UserModel(
                id = 0,
                email = registerRequest.email.trim().lowercase(),
                login = registerRequest.login.trim().lowercase(),
                password = hashFunction(registerRequest.password.trim()),
                firstName = registerRequest.firstName.trim(),
                lastName = registerRequest.lastName.trim(),
                role = registerRequest.role.trim().getRoleByString()
            )
            userUseCase.createUser(user)
            call.respond(HttpStatusCode.OK, BaseResponse(success = true, userUseCase.generateToken(userModel = user)))
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(success = false, e.message ?: Constants.Error.GENERAL))
        }
    }

    post("api/v1/signin") {
        val loginRequest = call.receiveNullable<LoginRequest>() ?: kotlin.run {
            call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, Constants.Error.GENERAL))
            return@post
        }

        try {
            val user = userUseCase.findUserByEmail(loginRequest.email.trim().lowercase())

            if (user == null)
                call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, Constants.Error.WRONG_EMAIL))
            else {
                if (user.password == hashFunction(loginRequest.password)) {
                    call.respond(HttpStatusCode.OK, BaseResponse(success = true, userUseCase.generateToken(userModel = user)))
                } else {
                    call.respond(HttpStatusCode.BadRequest, BaseResponse(success = false, Constants.Error.INCORRECT_PASSWORD))
                }
            }
        } catch (e: Exception) {
            call.respond(HttpStatusCode.Conflict, BaseResponse(false, e.message ?: Constants.Error.GENERAL))
        }
    }
}