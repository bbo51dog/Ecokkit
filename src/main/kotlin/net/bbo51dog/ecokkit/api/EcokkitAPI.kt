package net.bbo51dog.ecokkit.api

import cn.nukkit.Server
import net.bbo51dog.ecokkit.event.EcokkitAddMoneyEvent
import net.bbo51dog.ecokkit.event.EcokkitReduceMoneyEvent
import net.bbo51dog.ecokkit.event.EcokkitSetMoneyEvent
import net.bbo51dog.ecokkit.repository.UserRepository
import net.bbo51dog.ecokkit.user.UserFactory

class EcokkitAPI private constructor(repo: UserRepository, unit: String, default: Int) : IEcokkitAPI {

    private val repo: UserRepository = repo
    
    private val default: Int = default
    
    override val unit: String = unit
    
    companion object {

        @JvmStatic
        lateinit var instance: EcokkitAPI
            private set

        @JvmStatic
        fun createInstance(repo: UserRepository, unit: String, default: Int) {
            this.instance = EcokkitAPI(repo, unit, default)
        }
    }

    override fun getMoney(xuid: String): Int {
        return this.repo.getUser(xuid).money
    }

    override fun setMoney(xuid: String, money: Int) {
        require(money >= 0, {"Money must be 0 or more"})
        val user = this.repo.getUser(xuid)
        val event = EcokkitSetMoneyEvent(xuid, user.name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = money
    }

    override fun addMoney(xuid: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = this.repo.getUser(xuid)
        val event = EcokkitAddMoneyEvent(xuid, user.name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money += money
    }

    override fun reduceMoney(xuid: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = this.repo.getUser(xuid)
        val new = user.money + money
        require(new > 0, {"Money is too little"})
        val event = EcokkitReduceMoneyEvent(xuid, user.name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = new
    }
    
    override fun exists(xuid: String): Boolean {
        return this.repo.existsUserId(xuid)
    }

    override fun getMoneyByName(name: String): Int {
        return this.repo.getUser(name).money
    }

    override fun setMoneyByName(name: String, money: Int) {
        require(money >= 0, {"Money must be 0 or more"})
        val user = this.repo.getUserByName(name)
        val event = EcokkitSetMoneyEvent(user.xuid, name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = money
    }

    override fun addMoneyByName(name: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = this.repo.getUserByName(name)
        val event = EcokkitAddMoneyEvent(user.xuid, name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money += money
    }

    override fun reduceMoneyByName(name: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = this.repo.getUserByName(name)
        val new = user.money + money
        require(new > 0, {"Money is too little"})
        val event = EcokkitReduceMoneyEvent(user.xuid, name, money)
        Server.getInstance().pluginManager.callEvent(event)
        if (event.isCancelled()) {
            return
        }
        user.money = new
    }
    
    override fun existsByName(name: String): Boolean {
        return this.repo.existsUserName(name)
    }

    override fun createMoneyData(xuid: String, name: String) {
        val user = UserFactory.createUser(xuid, name, this.default)
        this.repo.registerUser(user)
    }
    
    override fun getAll(): MutableMap<String, Map<String, Any>> {
        return this.repo.getAllData()
    }
}