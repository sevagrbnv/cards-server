package ru.sevagrbnv.utils

class Constants {

    object Role {
        const val ADMIN = "admin"
        const val MODERATOR = "moderator"
        const val CLIENT = "client"
    }

    object Error {
        const val GENERAL = "Oh, something went wrong!"
        const val WRONG_EMAIL = "Wrong e-mail"
        const val INCORRECT_PASSWORD = "Incorrect password"
    }
}