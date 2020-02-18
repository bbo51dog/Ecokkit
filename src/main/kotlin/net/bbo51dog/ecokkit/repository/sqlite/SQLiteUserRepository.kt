package net.bbo51dog.ecokkit.repository.sqlite

import java.sql.*
import net.bbo51dog.ecokkit.repository.UserRepository
import net.bbo51dog.ecokkit.user.User
import net.bbo51dog.ecokkit.user.UserFactory

class SQLiteUserRepository(connection: Connection) : UserRepository {

    private val connection: Connection = connection

    init {
        val stmt = connection.createStatement()
        stmt.executeUpdate("CREATE TABLE IF NOT EXISTS user (xuid TEXT NOT NULL PRIMARY KEY, name TEXT NOT NULL, money TEXT NOT NULL)")
    }

    override fun registerUser(user: User) {
        val xuid = user.xuid
        require(!this.existsUserId(xuid), {"User is already registered"})
        val stmt = this.connection.prepareStatement("INSERT INTO user (xuid, name, money) VALUES (?, ?, ?)")
        stmt.setString(1, xuid)
        stmt.setString(2, user.name)
        stmt.setInt(3, user.money)
        stmt.execute()
    }

    override fun getUser(xuid: String): User {
        require(this.existsUserId(xuid), {"User not found"})
        val stmt = this.connection.prepareStatement("SELECT name, money FROM user WHERE xuid = ?")
        stmt.setString(1, xuid)
        val result = stmt.executeQuery()
        val name = result.getString("name")
        val money = result.getInt("money")
        return UserFactory.createUser(xuid, name, money)
    }

    override fun updateUser(user: User) {
        val xuid = user.xuid
        require(this.existsUserId(xuid), {"User not found"})
        val stmt = this.connection.prepareStatement("UPDATE user SET name = ?, money = ? WHERE xuid = ?")
        stmt.setString(1, user.name)
        stmt.setInt(2, user.money)
        stmt.setString(3, xuid)
        stmt.execute()
    }

    override fun existsUserId(xuid: String): Boolean {
        val stmt = this.connection.prepareStatement("SELECT COUNT(*) AS count FROM user WHERE xuid = ?")
        stmt.setString(1, xuid)
        val result = stmt.executeQuery()
        return result.getInt("count") == 1
    }
}