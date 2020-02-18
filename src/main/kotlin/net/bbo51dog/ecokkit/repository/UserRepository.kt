package net.bbo51dog.ecokkit.repository

import net.bbo51dog.ecokkit.user.User

interface UserRepository : Repository {

    fun registerUser(user: User)

    fun getUser(xuid: String): User

    fun getUserByName(name: String): User

    fun updateUser(user: User)

    fun existsUserId(xuid: String): Boolean

    fun existsUserName(name: String): Boolean
}
