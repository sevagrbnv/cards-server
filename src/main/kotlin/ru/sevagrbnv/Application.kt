package ru.sevagrbnv

import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import ru.sevagrbnv.plugins.*
import ru.sevagrbnv.plugins.DatabaseFactory.initDatabase

fun main() {
    embeddedServer(Netty, port = 8080, host = "0.0.0.0", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    initDatabase()
//    configureSerialization()
//    configureDatabases()
//    configureMonitoring()
//    configureSecurity()
//    configureRouting()
}
