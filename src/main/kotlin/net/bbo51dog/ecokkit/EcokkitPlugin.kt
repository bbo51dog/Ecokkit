package net.bbo51dog.ecokkit

import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection
import cn.nukkit.plugin.PluginBase
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.bbo51dog.ecokkit.repository.RepositoryProvider
import net.bbo51dog.ecokkit.repository.UserRepository

class EcokkitPlugin : PluginBase() {

    private val provider: RepositoryProvider
    
    private val repo: UserRepository

    override fun onLoad() {
        dataFolder.mkdir()
        val config = this.loadConfig()
        this.provider = RepositoryProvider(dataFolder.absolutePath + "/ecokkit.db")
        this.repo = this.provider.createUserRepository()
        EcokkitAPI.init(this.repo, config.get("unit"), config.get("default_money"))
    }
    
    override fun onDisable() {
        this.provider.close()
    }
    
    private fun loadConfig(): Config {
        val map = linkedMapOf()
        map.put("default_money", 5000)
        map.put("unit", "${$}")
        return Config(dataFolder.absolutePath + "Cofig.yml", Config.YAML, ConfigSection(map))
    }
}
