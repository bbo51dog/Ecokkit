package net.bbo51dog.ecokkit

import cn.nukkit.plugin.PluginBase
import net.bbo51dog.ecokkit.repository.RepositoryProvider

class EcokkitPlugin : PluginBase() {

    private lateinit var provider: RepositoryProvider

    override fun onEnable() {
        val folder = this.getDataFolder()
        folder.mkdir()
        this.provider = RepositoryProvider(folder.absolutePath + "/ecokkit.db ")
    }
}