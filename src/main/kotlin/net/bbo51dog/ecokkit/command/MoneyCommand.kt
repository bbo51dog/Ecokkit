package net.bbo51dog.ecokkit.command

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import net.bbo51dog.ecokkit.api.EcokkitAPI

class MoneyCommand : Command("money", "Ecokkit money command", "/money [mine | see]") {

    private val api: EcokkitAPI = EcokkitAPI.instance
    
    private val lang = EcokkitAPI.instance.language

    init {
        this.permission = "ecokkit.command.money"
    }

    override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
        if (sender == null) {
            return false
        }
        if (sender !is Player) {
            sender.sendMessage(this.lang.getMessage("sender.not.player"))
            return false
        }
        val xuid = sender.loginChainData.xuid
        if (!this.api.exists(xuid)) {
            this.api.createMoneyData(xuid, sender.name)
        }
        val size = args?.size
        if (size == null) {
            sender.sendMessage(this.usage)
            return false
        }
        if (size < 1) {
            sender.sendMessage(this.usage)
            return false
        }
        when (args[0]) {
            "mine" -> {
                return this.mine(sender)
            }
            "see" -> {
                return this.see(sender, args)
            }
            else -> {
                sender.sendMessage(this.usage)
                return false
            }
        }
    }
    
    private fun mine(sender: Player): Boolean {
        val search = listOf<String>("%unit", "%money")
        val replace = listOf<String>(this.api.unit, this.api.getMoneyByName(sender.name).toString())
        val message = this.lang.getReplaceMessage(search, replace, "command.mine")
        sender.sendMessage(message)
        return true
    }
    
    private fun see(sender: Player, args: Array<out String>): Boolean {
        if (args.size < 2) {
            sender.sendMessage("/money see <player>")
            return false
        }
        if (!this.api.existsByName(args[1])) {
            val message = this.lang.getMessage("player.not.found").replace("%player", args[1])
            sender.sendMessage(message)
            return false
        }
        val search = listOf<String>("%player", "%unit", "%money")
        val replace = listOf<String>(args[1], this.api.unit, this.api.getMoneyByName(args[1]).toString())
        val message = this.lang.getReplaceMessage(search, replace, "command.see")
        sender.sendMessage(message)
        return true
    }
}