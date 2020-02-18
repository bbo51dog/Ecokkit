package net.bbo51dog.ecokkit.user

class UserImpl(xuid: String, name: String, money: Int) : User {

    protected val xuid: String = xuid

    protected val name: String = name.toLowerCase()

    protected var money: Int = money
}