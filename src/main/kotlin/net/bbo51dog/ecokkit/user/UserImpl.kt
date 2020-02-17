package net.bbo51dog.ecokkit.user

class UserImpl(name: String, money: Int) : User {

    private val name: String = name.toLowerCase()

    private val money: Int = money
}