package net.bbo51dog.ecokkit.api

import cn.nukkit.Server
import net.bbo51dog.ecokkit.event.EcokkitAddMoneyEvent
import net.bbo51dog.ecokkit.event.EcokkitReduceMoneyEvent
import net.bbo51dog.ecokkit.event.EcokkitSetMoneyEvent
import net.bbo51dog.ecokkit.repository.UserRepository
import net.bbo51dog.ecokkit.utils.Language
import net.bbo51dog.ecokkit.user.UserFactory

class EcokkitAPI private constructor(repo: UserRepository, lang: Language, unit: String, default: Int) : IEcokkitAPI {

    private val repo: UserRepository = repo
    
    private val default: Int = default
    
    override val unit: String = unit
    
    override val language: Language = lang
    
    companion object {

        @JvmStatic
        lateinit var instance: EcokkitAPI
            private set

        @JvmStatic
        fun createInstance(repo: UserRepository, lang: Language, unit: String, default: Int) {
            if (!::instance.isInitialized) {
                instance = EcokkitAPI(repo, lang, unit, default)
            }
        }
    }

    override fun getMoney(xuid: String): Int {
        return repo.getUser(xuid).money
    }

    override fun setMoney(xuid: String, money: Int) {
        require(money >= 0, {"Money must be 0 or more"})
        val user = repo.getUser(xuid)
        val event = EcokkitSetMoneyEvent(xuid, user.name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = money
        repo.updateUser(user)
    }

    override fun addMoney(xuid: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = repo.getUser(xuid)
        val event = EcokkitAddMoneyEvent(xuid, user.name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money += money
        repo.updateUser(user)
    }

    override fun reduceMoney(xuid: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = repo.getUser(xuid)
        val new = user.money + money
        require(new > 0, {"Money is too little"})
        val event = EcokkitReduceMoneyEvent(xuid, user.name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = new
        repo.updateUser(user)
    }
    
    override fun exists(xuid: String): Boolean {
        return repo.existsUserId(xuid)
    }

    override fun getMoneyByName(name: String): Int {
        return repo.getUserByName(name).money
    }

    override fun setMoneyByName(name: String, money: Int) {
        require(money >= 0, {"Money must be 0 or more"})
        val user = repo.getUserByName(name)
        val event = EcokkitSetMoneyEvent(user.xuid, name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = money
        repo.updateUser(user)
    }

    override fun addMoneyByName(name: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = repo.getUserByName(name)
        val event = EcokkitAddMoneyEvent(user.xuid, name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money += money
        repo.updateUser(user)
    }

    override fun reduceMoneyByName(name: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = repo.getUserByName(name)
        val new = user.money + money
        require(new > 0, {"Money is too little"})
        val event = EcokkitReduceMoneyEvent(user.xuid, name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = new
        repo.updateUser(user)
    }
    
    override fun existsByName(name: String): Boolean {
        return repo.existsUserName(name)
    }

    override fun createMoneyData(xuid: String, name: String) {
        val user = UserFactory.createUser(xuid, name, default)
        repo.registerUser(user)
    }
    
    override fun getAll(): MutableMap<String, Map<String, Any>> {
        return repo.getAllData()
    }
}