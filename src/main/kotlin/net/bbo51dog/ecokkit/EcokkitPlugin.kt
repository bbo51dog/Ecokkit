package net.bbo51dog.ecokkit

import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection
import cn.nukkit.plugin.PluginBase
import java.io.File
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.bbo51dog.ecokkit.command.MoneyCommand
import net.bbo51dog.ecokkit.command.OpMoneyCommand
import net.bbo51dog.ecokkit.event.listener.EventListener
import net.bbo51dog.ecokkit.repository.RepositoryProvider
import net.bbo51dog.ecokkit.repository.UserRepository
import net.bbo51dog.ecokkit.utils.Language

class EcokkitPlugin : PluginBase() {

    private lateinit var provider: RepositoryProvider
    
    private lateinit var repo: UserRepository

    private lateinit var api: EcokkitAPI
    
    private lateinit var conf: Config

    override fun onLoad() {
        dataFolder.mkdir()
        loadConfig()
        val lang = loadLanguage()
        provider = RepositoryProvider(dataFolder.absolutePath + "/ecokkit.db")
        repo = provider.createUserRepository()
        EcokkitAPI.createInstance(repo, lang, conf.getString("unit"), conf.getInt("default_money"))
    }
    
    override fun onEnable() {
        registerCommand()
        server.pluginManager.registerEvents(EventListener(), this)
    }
    
    override fun onDisable() {
        provider.close()
    }
    
    private fun loadConfig() {
        val section = ConfigSection()
        section.set("default_money", 5000)
        section.set("unit", "\$")
        section.set("lang_default", "eng")
        section.set("lang_list", listOf(
            "eng",
            "jpn"
        ))
        conf = Config(dataFolder.absolutePath + "/Config.yml", Config.YAML, section)
    }
    
    private fun loadLanguage(): Language {
        val langs = mutableMapOf<String, Map<String, Any>>()
        conf.getStringList("lang_list").forEach {
            saveResource(it + ".ini")
            var path = dataFolder.absolutePath + "/${it}.ini"
            if (File(path).exists()) {
                var data: Map<String, Any> = Config(path, Config.PROPERTIES).all
                langs.set(it, data)
            }
        }
        return Language(langs, conf.getString("lang_default"))
    }

    private fun registerCommand() {
        server.commandMap.register("money", MoneyCommand())
        server.commandMap.register("opmoney", OpMoneyCommand())
    }
}