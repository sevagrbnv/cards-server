package ru.sevagrbnv.plugins

import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import ru.sevagrbnv.domain.usecases.CardUseCase
import ru.sevagrbnv.domain.usecases.UserUseCase
import ru.sevagrbnv.routes.userRoute

fun Application.configureRouting(
    userUseCase: UserUseCase,
    cardUseCase: CardUseCase
) {
    routing {
        userRoute(userUseCase = userUseCase)
    }
}
