package net.bbo51dog.ecokkit.api

interface IEcokkitAPI {

    fun getMoney(xuid: String): Int

    fun setMoney(xuid: String, money: Int)

    fun addMoney(xuid: String, money: Int)

    fun reduceMoney(xuid: String, money: Int)

    fun getMoneyByName(name: String): Int

    fun setMoneyByName(name: String, money, Int)

    fun addMoneyByName(name: String, money: Int)

    fun reduceMoneyByName(name: String, money: Int)

    fun createMoneyData(xuid: String, name: String)
}