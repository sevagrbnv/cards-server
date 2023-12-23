package ru.sevagrbnv

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.sevagrbnv.authentification.JwtService
import ru.sevagrbnv.data.repository.CardRepositoryImpl
import ru.sevagrbnv.data.repository.UserRepositoryImpl
import ru.sevagrbnv.domain.usecases.CardUseCase
import ru.sevagrbnv.domain.usecases.UserUseCase
import ru.sevagrbnv.plugins.*
import ru.sevagrbnv.plugins.DatabaseFactory.initDatabase

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    val jwtService = JwtService()
    val userRepository = UserRepositoryImpl()
    val cardRepository = CardRepositoryImpl()
    val userUseCase = UserUseCase(userRepository, jwtService)
    val cardUseCase = CardUseCase(cardRepository)

    initDatabase()
    configureMonitoring()
    configureSerialization()
    configureSecurity(userUseCase)
//    configureRouting()
}
