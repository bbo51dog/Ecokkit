package net.bbo51dog.ecokkit.event

class EcokkitAddMoneyEvent(xuid: String, name: String, money: Int) : EcokkitEvent() {

    val xuid: String = xuid
    
    val name: String = name
    
    val money: Int = money
}