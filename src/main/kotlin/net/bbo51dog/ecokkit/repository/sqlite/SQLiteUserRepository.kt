package net.bbo51dog.ecokkit.repository.sqlite

import java.sql.*
import net.bbo51dog.ecokkit.repository.UserRepository

class SQLiteUserRepository(connection: Connection) : UserRepository {

    private val connection: Connection = connection
}