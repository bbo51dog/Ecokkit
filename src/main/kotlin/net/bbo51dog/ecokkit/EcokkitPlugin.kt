package net.bbo51dog.ecokkit

import cn.nukkit.plugin.PluginBase
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.bbo51dog.ecokkit.repository.RepositoryProvider
net.bbo51dog.ecokkit.repository.UserRepository

class EcokkitPlugin : PluginBase() {

    private lateinit var provider: RepositoryProvider
    
    private lateinit var repo: UserRepository

    override fun onLoad() {
        val folder = this.getDataFolder()
        folder.mkdir()
        this.provider = RepositoryProvider(folder.absolutePath + "/ecokkit.db ")
        this.repo = this.provider.createUserRepository()
        EcokkitAPI.init(this.repo)
    }
    
    override fun onDisable() {
        this.provider.close()
    }
}