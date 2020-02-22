package net.bbo51dog.ecokkit.user

object UserFactory {
    
    public fun createUser(xuid: String, name: String, money: Int): User {
        return UserImpl(xuid, name, money)
    }
}