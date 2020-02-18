package net.bbo51dog.ecokkit.repository.sqlite

import java.sql.*
import net.bbo51dog.ecokkit.repository.UserRepository

class SQLiteUserRepository(connection: Connection) : UserRepository {

    private val connection: Connection = connection

    init {
        val stmt = connection.createStatement()
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user (xuid TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, money TEXT NOT NULL)")
    }
}