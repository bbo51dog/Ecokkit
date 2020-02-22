package net.bbo51dog.ecokkit.user

class UserImpl(xuid: String, name: String, money: Int) : User {

    override val xuid: String = xuid

    override val name: String = name.toLowerCase()

    override var money: Int = money
}