package ru.sevagrbnv.data.repository

import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select
import ru.sevagrbnv.data.model.UserModel
import ru.sevagrbnv.data.model.getRoleByString
import ru.sevagrbnv.data.model.getStringByRole
import ru.sevagrbnv.data.model.tables.UserTable
import ru.sevagrbnv.domain.repository.UserRepository
import ru.sevagrbnv.plugins.DatabaseFactory

class UserRepositoryImpl : UserRepository {

    override suspend fun getUserByEmail(email: String): UserModel? =
        DatabaseFactory.dbQuery {
            UserTable.select { UserTable.email.eq(email) }
                .map { rowToUser(row = it) }
                .singleOrNull()
        }

    override suspend fun insertUser(user: UserModel) {
        DatabaseFactory.dbQuery {
            UserTable.insert { table ->
                table[email] = user.email
                table[login] = user.login
                table[password] = user.password
                table[firstName] = user.firstName
                table[lastName] = user.lastName
                table[isActive] = user.isActive
                table[role] = user.role.getStringByRole()
            }
        }
    }

    private fun rowToUser(row: ResultRow?): UserModel? {
        return if (row == null) null
        else UserModel(
            id = row[UserTable.id],
            email = row[UserTable.email],
            login = row[UserTable.login],
            password = row[UserTable.password],
            firstName = row[UserTable.firstName],
            lastName = row[UserTable.lastName],
            isActive = row[UserTable.isActive],
            role = row[UserTable.role].getRoleByString()
        )
    }
}