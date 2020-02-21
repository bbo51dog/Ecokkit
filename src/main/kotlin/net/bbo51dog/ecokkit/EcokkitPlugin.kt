package net.bbo51dog.ecokkit

import cn.nukkit.utils.Config
import cn.nukkit.utils.ConfigSection
import cn.nukkit.plugin.PluginBase
import net.bbo51dog.ecokkit.api.EcokkitAPI
import net.bbo51dog.ecokkit.command.MoneyCommand
import net.bbo51dog.ecokkit.event.listener.EventListener
import net.bbo51dog.ecokkit.repository.RepositoryProvider
import net.bbo51dog.ecokkit.repository.UserRepository
import net.bbo51dog.ecokkit.utils.Language

class EcokkitPlugin : PluginBase() {

    private lateinit var provider: RepositoryProvider
    
    private lateinit var repo: UserRepository

    private lateinit var api: EcokkitAPI

    override fun onLoad() {
        dataFolder.mkdir()
        val config = this.loadConfig()
        val lang = this.loadLanguage()
        this.provider = RepositoryProvider(dataFolder.absolutePath + "/ecokkit.db")
        this.repo = this.provider.createUserRepository()
        EcokkitAPI.createInstance(this.repo, lang, config.getString("unit"), config.getInt("default_money"))
    }
    
    override fun onEnable() {
        this.registerCommand()
        this.server.pluginManager.registerEvents(EventListener(), this)
    }
    
    override fun onDisable() {
        this.provider.close()
    }
    
    private fun loadConfig(): Config {
        val section = ConfigSection()
        section.set("default_money", 5000)
        section.set("unit", "\$")
        return Config(dataFolder.absolutePath + "/Cofig.yml", Config.YAML, section)
    }
    
    private fun loadLanguage(): Language {
        val section = ConfigSection(linkedMapOf<String, Any>(
            "prefix" to "§l§a[§6Ecokkit§a]§r ",
            "sender.not.player" to "§cゲーム内で実行してください",
            "player.not.found" to "プレイヤー｢%player｣が見つかりませんでした",
            "command.usage" to "使い方: %usage",
            "command.mine" to "あなたの所持金 §e>> §r%unit%money",
            "command.see" to "%playerの所持金 §e>> §r%unit%money",
            "command.pay.lacking" to "所持金が不足しています",
            "command.pay.success.sender" to "%playerに%unit%money支払いました",
            "command.pay.success.target" to "%playerから%unit%money受け取りました"
        ))
        val config = Config(dataFolder.absolutePath + "/Language.ini", Config.PROPERTIES, section)
        return Language(config.all)
    }

    private fun registerCommand() {
        this.server.commandMap.register("money", MoneyCommand())
    }
}