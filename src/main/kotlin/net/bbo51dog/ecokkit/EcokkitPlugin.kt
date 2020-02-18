package net.bbo51dog.ecokkit

import cn.nukkit.utils.Config
import cn.nukkit.plugin.PluginBase
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.bbo51dog.ecokkit.repository.RepositoryProvider
import net.bbo51dog.ecokkit.repository.UserRepository

class EcokkitPlugin : PluginBase() {

    private val provider: RepositoryProvider
    
    private val repo: UserRepository

    override fun onLoad() {
        dataFolder.mkdir()
        Config(dataFolder.absolutePath + "Cofig.yml", )
        this.provider = RepositoryProvider(dataFolder.absolutePath + "/ecokkit.db", Config.YAML)
        this.repo = this.provider.createUserRepository()
        EcokkitAPI.init(this.repo)
    }
    
    override fun onDisable() {
        this.provider.close()
    }
}
