package net.bbo51dog.ecokkit.api

import net.bbo51dog.ecokkit.utils.Language

interface IEcokkitAPI {

    val unit: String 
    
    val language: Language

    fun getMoney(xuid: String): Int

    fun setMoney(xuid: String, money: Int)

    fun addMoney(xuid: String, money: Int)

    fun reduceMoney(xuid: String, money: Int)
    
    fun exists(xuid: String): Boolean

    fun getMoneyByName(name: String): Int

    fun setMoneyByName(name: String, money: Int)

    fun addMoneyByName(name: String, money: Int)

    fun reduceMoneyByName(name: String, money: Int)
    
    fun existsByName(name: String): Boolean

    fun createMoneyData(xuid: String, name: String)
    
    fun getAll(): MutableMap<String, Map<String, Any>>
}