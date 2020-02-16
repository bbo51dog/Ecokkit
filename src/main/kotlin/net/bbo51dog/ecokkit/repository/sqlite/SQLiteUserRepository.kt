package net.bbo51dog.ecokkit.repository.sqlite

import java.sql.*
import net.bbo51dog.ecokkit.repository.UserRepository

class SQLiteUserRepository : UserRepository(connection: Connection) {

    private val connection: Connection = connection
}