package net.bbo51dog.eckkit.user

object UserFactory {
    
    public fun createUser(name: String, money: Int): User{
        return UserImpl(name money)
    }
}