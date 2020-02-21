package net.bbo51dog.ecokkit.repository

import java.sql.Connection
import java.sql.DriverManager
import net.bbo51dog.ecokkit.repository.sqlite.SQLiteUserRepository

class RepositoryProvider(path: String) {

    private val connection: Connection = DriverManager.getConnection("jdbc:sqlite:" + path)

    init {
        Class.forName("org.sqlite.JDBC")
    }
    
    public fun close() {
        this.connection.close()
    }
    
    public fun createUserRepository(): UserRepository {
        return SQLiteUserRepository(this.connection)
    }
}