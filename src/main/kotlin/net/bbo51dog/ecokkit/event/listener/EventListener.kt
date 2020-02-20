package net.bbo51dog.ecokkit.event.listener

import cn.nukkit.event.EventHandler
import cn.nukkit.event.Listener
import cn.nukkit.event.player.PlayerJoinEvent
import net.bbo51dog.ecokkit.api.EcokkitAPI

class EventListener : Listener {

    @EventHandler
    fun onJoin(event: PlayerJoinEvent) {
        val api = EcokkitAPI.instance
        val xuid = event.player.loginChainData.xuid
        if (!api.exists(xuid)) {
            api.createMoneyData(xuid, event.player.name)
        }
    }
}