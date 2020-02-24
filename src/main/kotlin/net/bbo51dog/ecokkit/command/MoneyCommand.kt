package net.bbo51dog.ecokkit.command

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import net.bbo51dog.ecokkit.api.EcokkitAPI

class MoneyCommand : Command("money", "Ecokkit money command", "/money [mine | see | pay]") {

    private val api: EcokkitAPI = EcokkitAPI.instance
    
    private val lang = EcokkitAPI.instance.language

    init {
        permission = "ecokkit.command.money"
    }

    override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
        if (sender == null) {
            return false
        }
        if (sender !is Player) {
            sender.sendMessage(lang.getMessage("sender.not.player"))
            return false
        }
        val xuid = sender.loginChainData.xuid
        if (!api.exists(xuid)) {
            api.createMoneyData(xuid, sender.name)
        }
        val usage = lang.getReplaceMessage(listOf("%usage"), listOf(usage), "command.usage")
        val size = args?.size
        if (size == null) {
            sender.sendMessage(usage)
            return false
        }
        if (size < 1) {
            sender.sendMessage(usage)
            return false
        }
        when (args[0]) {
            "mine" -> {
                return mine(sender)
            }
            "see" -> {
                return see(sender, args)
            }
            "pay" -> {
                return pay(sender, args)
            }
            else -> {
                sender.sendMessage(usage)
                return false
            }
        }
    }
    
    private fun mine(sender: Player): Boolean {
        val search = listOf("%unit", "%money")
        val replace = listOf(api.unit, api.getMoneyByName(sender.name).toString())
        val message = lang.getReplaceMessage(search, replace, "command.mine")
        sender.sendMessage(message)
        return true
    }
    
    private fun see(sender: Player, args: Array<out String>): Boolean {
        if (args.size < 2) {
            val usage = lang.getReplaceMessage(listOf("%usage"), listOf("/money see <player>"), "command.usage")
            sender.sendMessage(usage)
            return false
        }
        if (!api.existsByName(args[1])) {
            val message = lang.getReplaceMessage(listOf("%player"), listOf(args[1]), "player.not.found")
            sender.sendMessage(message)
            return false
        }
        val search = listOf("%player", "%unit", "%money")
        val replace = listOf(args[1], api.unit, api.getMoneyByName(args[1]).toString())
        val message = lang.getReplaceMessage(search, replace, "command.see")
        sender.sendMessage(message)
        return true
    }
    
    private fun pay(sender: Player, args: Array<out String>): Boolean {
        if (args.size < 3) {
            val usage = lang.getReplaceMessage(listOf("%usage"), listOf("/money pay <player> <money>"), "command.usage")
            sender.sendMessage(usage)
            return false
        }
        if (!api.existsByName(args[1])) {
            val message = lang.getReplaceMessage(listOf("%player"), listOf(args[1]), "player.not.found")
            sender.sendMessage(message)
            return false
        }
        val pay = args[2].toInt()
        if (api.getMoneyByName(sender.name) < pay) {
            sender.sendMessage(lang.getMessage("command.pay.lacking"))
            return false
        }
        api.reduceMoneyByName(sender.name, pay)
        api.addMoneyByName(args[1], pay)
        val search = listOf("%player", "%unit", "%money")
        val replace = listOf(args[1], api.unit, args[2])
        val message = lang.getReplaceMessage(search, replace, "command.pay.success.sender")
        sender.sendMessage(message)
        val target = Server.getInstance().getOfflinePlayer(args[1])
        if (target == null) {
            return true
        }
        if (target.isOnline()) {
            val search_target = listOf("%player", "%unit", "%money")
            val replace_target = listOf(sender.name, api.unit, args[2])
            val message_target = lang.getReplaceMessage(search_target, replace_target, "command.pay.success.target")
            sender.sendMessage(message_target)
        }
        return true
    }
}