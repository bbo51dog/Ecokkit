package net.bbo51dog.ecokkit.user

class UserImpl(xuid: String, name: String, money: Int) : User {

    private val xuid: String = xuid

    private val name: String = name.toLowerCase()

    private val money: Int = money
}