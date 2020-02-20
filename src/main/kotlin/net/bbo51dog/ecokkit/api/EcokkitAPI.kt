package net.bbo51dog.ecokkit.api

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
        this.repo.getUser(xuid).money = money
    }

    override fun addMoney(xuid: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        this.repo.getUser(xuid).money += money
    }

    override fun reduceMoney(xuid: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = this.repo.getUser(xuid)
        val new = user.money + money
        require(new > 0, {"Money is too little"})
        user.money = new
    }

    override fun getMoneyByName(name: String): Int {
        return this.repo.getUser(name).money
    }

    override fun setMoneyByName(name: String, money: Int) {
        require(money >= 0, {"Money must be 0 or more"})
        this.repo.getUserByName(name).money = money
    }

    override fun addMoneyByName(name: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        this.repo.getUserByName(name).money += money
    }

    override fun reduceMoneyByName(name: String, money: Int) {
        require(money > 0, {"Money must be over 0"})
        val user = this.repo.getUserByName(name)
        val new = user.money + money
        require(new > 0, {"Money is too little"})
        user.money = new
    }

    override fun createMoneyData(xuid: String, name: String) {
        val user = UserFactory.createUser(xuid, name, this.default)
        this.repo.registerUser(user)
    }
}