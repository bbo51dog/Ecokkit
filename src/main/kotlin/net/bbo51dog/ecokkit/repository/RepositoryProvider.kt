package net.bbo51dog.ecokkit.repository

import cn.nukkit.utils.MainLogger
import java.sql.*
import net.bbo51dog.ecokkit.repository.sqlite.SQLiteUserRepository

class RepositoryProvider(path: String) {

    private lateinit var connection: Connection

    init {
        try {
            Class.forName("org.sqlite.JDBC")
            val connection = DriverManager.getConnection("jdbc:sqlite:$path")
        } catch(exception: SQLException) {
            MainLogger.getLogger().logException(exception)
        }
    }
}