package net.bbo51dog.ecokkit.utils

class Language(map: Map<String, Any>) {

    private val map = map
    
    val prefix: String = map["prefix"].toString()
    
    fun getMessage(key: String, prefix: Boolean = true): String {
        val message = if (prefix) {
            this.prefix + this.map[key]
        } else {
            this.map[key]
        }
        return message.toString()
    }
}