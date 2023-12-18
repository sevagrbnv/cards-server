package ru.sevagrbnv.data.model

import ru.sevagrbnv.utils.Constants

enum class RoleModel {
    ADMIN, MODERATOR, CLIENT
}

fun String.getRoleByString(): RoleModel = when (this) {
    Constants.Role.ADMIN -> RoleModel.ADMIN
    Constants.Role.MODERATOR -> RoleModel.MODERATOR
    Constants.Role.CLIENT -> RoleModel.CLIENT
    else -> throw IllegalStateException("Undefined role. Wrong string: $this")
}

fun RoleModel.getStringByRole(): String = when (this) {
    RoleModel.ADMIN -> Constants.Role.ADMIN
    RoleModel.MODERATOR -> Constants.Role.MODERATOR
    RoleModel.CLIENT -> Constants.Role.CLIENT
}