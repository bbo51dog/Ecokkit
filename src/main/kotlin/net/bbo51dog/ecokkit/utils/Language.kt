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
    
    fun getReplaceMessage(search: List<String>, replace: List<String>, key: String, prefix: Boolean = true): String {
        var message = this.getMessage(key, prefix)
        for (i in 0..search.size - 1) {
            message = message.replace(search[i], replace[i])
        }
        return message
    }
}