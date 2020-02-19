package net.bbo51dog.ecokkit

import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection
import cn.nukkit.plugin.PluginBase
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.bbo51dog.ecokkit.repository.RepositoryProvider
import net.bbo51dog.ecokkit.repository.UserRepository

class EcokkitPlugin : PluginBase() {

    private lateinit var provider: RepositoryProvider
    
    private lateinit var repo: UserRepository

    private lateinit var api: EcokkitAPI

    override fun onLoad() {
        dataFolder.mkdir()
        val config = this.loadConfig()
        this.provider = RepositoryProvider(dataFolder.absolutePath + "/ecokkit.db")
        this.repo = this.provider.createUserRepository()
        this.api = EcokkitAPI(this.repo, config.getString("unit"), config.getInt("default_money"))
    }
    
    override fun onDisable() {
        this.provider.close()
    }
    
    private fun loadConfig(): Config {
        val section = ConfigSection()
        section.set("default_money", 5000)
        section.set("unit", "\$")
        return Config(dataFolder.absolutePath + "Cofig.yml", Config.YAML, section)
    }
}