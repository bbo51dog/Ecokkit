package net.bbo51dog.ecokkit.command

import cn.nukkit.Player
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import net.bbo51dog.ecokkit.api.EcokkitAPI

class MoneyCommand : Command("money", "Ecokkit money command", "/money []") {

    init {
        this.permission = "ecokkit.command.money"
    }

    override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
        if (sender == null) {
            return false
        }
        val api = EcokkitAPI.instance
        val lang = api.language
        if (sender !is Player) {
            sender.sendMessage(lang.getMessage("sender.not.player"))
            return false
        }
        val xuid = sender.loginChainData.xuid
        if (!api.exists(xuid)) {
            api.createMoneyData(xuid, sender.name)
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
            else -> {
                sender.sendMessage(this.usage)
                return false
            }
        }
        return true
    }
}