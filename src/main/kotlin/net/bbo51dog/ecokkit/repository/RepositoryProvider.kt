package net.bbo51dog.ecokkit.repository

import java.sql.Connection
import java.sql.DriverManager
import net.bbo51dog.ecokkit.repository.sqlite.SQLiteUserRepository

class RepositoryProvider(path: String) {

    private lateinit var connection: Connection

    init {
        Class.forName("org.sqlite.JDBC")
        connection = DriverManager.getConnection("jdbc:sqlite:" + path)
    }
    
    public fun close() {
        connection.close()
    }
    
    public fun createUserRepository(): UserRepository {
        return SQLiteUserRepository(connection)
    }
}