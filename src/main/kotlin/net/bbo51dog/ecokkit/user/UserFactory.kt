package net.bbo51dog.ecokkit.user

object UserFactory {
    
    public fun createUser(name: String, money: Int): User{
        return UserImpl(name, money)
    }
}