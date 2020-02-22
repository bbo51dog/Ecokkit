package net.bbo51dog.ecokkit.command

import cn.nukkit.Player
import cn.nukkit.Server
import cn.nukkit.command.Command
import cn.nukkit.command.CommandSender
import net.bbo51dog.ecokkit.api.EcokkitAPI

class OpMoneyCommand : Command("opmoney", "Ecokkit money command for operators", "/opmoney [set | add | reduce]") {

    private val api: EcokkitAPI = EcokkitAPI.instance
    
    private val lang = EcokkitAPI.instance.language

    init {
        this.permission = "ecokkit.command.opmoney"
    }

    override fun execute(sender: CommandSender?, commandLabel: String?, args: Array<out String>?): Boolean {
        if (sender == null) {
            return false
        }
        val usage = this.lang.getReplaceMessage(listOf("%usage"), listOf(this.usage), "command.usage")
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
            "set" -> {
                return this.set(sender, args)
            }
            "add" -> {
                return this.add(sender, args)
            }
            "reduce" -> {
                return this.reduce(sender, args)
            }
            else -> {
                sender.sendMessage(usage)
                return false
            }
        }
    }
    
    private fun set(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 3) {
            val usage = this.lang.getReplaceMessage(listOf("%usage"), listOf("/money set <player> <money>"), "command.usage")
            sender.sendMessage(usage)
            return false
        }
        if (!this.api.existsByName(args[1])) {
            val message = this.lang.getReplaceMessage(listOf("%player"), listOf(args[1]), "player.not.found")
            sender.sendMessage(message)
            return false
        }
        val money = args[2].toInt()
        if (money < 0) {
            val message = this.lang.getMessage("invalid.value")
            sender.sendMessage(message)
            return false
        }
        this.api.setMoneyByName(args[1], money)
        val search = listOf("%player", "%unit", "%money")
        val replace = listOf(args[1], this.api.unit, args[2])
        val message = this.lang.getReplaceMessage(search, replace, "command.set.success.sender")
        sender.sendMessage(message)
        val target = Server.getInstance().getOfflinePlayer(args[1])
        if (target == null) {
            return true
        }
        if (target.isOnline()) {
            val search_target = listOf("%player", "%unit", "%money")
            val replace_target = listOf(sender.name, this.api.unit, args[2])
            val message_target = this.lang.getReplaceMessage(search_target, replace_target, "command.set.success.target")
            sender.sendMessage(message_target)
        }
        return true
    }
    
    private fun add(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 3) {
            val usage = this.lang.getReplaceMessage(listOf("%usage"), listOf("/money add <player> <money>"), "command.usage")
            sender.sendMessage(usage)
            return false
        }
        if (!this.api.existsByName(args[1])) {
            val message = this.lang.getReplaceMessage(listOf("%player"), listOf(args[1]), "player.not.found")
            sender.sendMessage(message)
            return false
        }
        val money = args[2].toInt()
        if (money < 0) {
            val message = this.lang.getMessage("invalid.value")
            sender.sendMessage(message)
            return false
        }
        this.api.addMoneyByName(args[1], money)
        val search = listOf("%player", "%unit", "%money")
        val replace = listOf(args[1], this.api.unit, args[2])
        val message = this.lang.getReplaceMessage(search, replace, "command.add.success.sender")
        sender.sendMessage(message)
        val target = Server.getInstance().getOfflinePlayer(args[1])
        if (target == null) {
            return true
        }
        if (target.isOnline()) {
            val search_target = listOf("%player", "%unit", "%money")
            val replace_target = listOf(sender.name, this.api.unit, args[2])
            val message_target = this.lang.getReplaceMessage(search_target, replace_target, "command.add.success.target")
            sender.sendMessage(message_target)
        }
        return true
    }
    
    private fun reduce(sender: CommandSender, args: Array<out String>): Boolean {
        if (args.size < 3) {
            val usage = this.lang.getReplaceMessage(listOf("%usage"), listOf("/money reduce <player> <money>"), "command.usage")
            sender.sendMessage(usage)
            return false
        }
        if (!this.api.existsByName(args[1])) {
            val message = this.lang.getReplaceMessage(listOf("%player"), listOf(args[1]), "player.not.found")
            sender.sendMessage(message)
            return false
        }
        var money = args[2].toInt()
        if (money < 0) {
            val message = this.lang.getMessage("invalid.value")
            sender.sendMessage(message)
            return false
        }
        if (this.api.getMoneyByName(args[1]) < money) {
            this.api.setMoneyByName(args[1], 0)
        } else {
            this.api.reduceMoneyByName(args[1], money)
        }
        val search = listOf("%player", "%unit", "%money")
        val replace = listOf(args[1], this.api.unit, args[2])
        val message = this.lang.getReplaceMessage(search, replace, "command.reduce.success.sender")
        sender.sendMessage(message)
        val target = Server.getInstance().getOfflinePlayer(args[1])
        if (target == null) {
            return true
        }
        if (target.isOnline()) {
            val search_target = listOf("%player", "%unit", "%money")
            val replace_target = listOf(sender.name, this.api.unit, args[2])
            val message_target = this.lang.getReplaceMessage(search_target, replace_target, "command.reduce.success.target")
            sender.sendMessage(message_target)
        }
        return true
    }
}