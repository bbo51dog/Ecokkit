package net.bbo51dog.ecokkit.repository

import cn.nukkit.utils.MainLogger
import java.sql.*
import net.bbo51dog.ecokkit.repository.sqlite.SQLiteUserRepository

class RepositoryProvider{

    private lateinit var Connection: connection

    constructor(path: String): this(){
        try{
            Class.forName("org.sqlite.JDBC")
            val connection = DriverManager.getConnection("jdbc:sqlite:$path")
        }catch(exception: SQLException){
            MainLogger.getLogger().logException(exception)
        }
    }
}