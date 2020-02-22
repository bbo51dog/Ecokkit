package net.bbo51dog.ecokkit.utils

class Language(messages: Map<String, Any>) {

    private val messages = messages
    
    val prefix: String = messages["prefix"].toString()
    
    fun getMessage(key: String, prefix: Boolean = true): String {
        val message = if (prefix) {
            this.prefix + messages[key]
        } else {
            messages[key]
        }
        return message.toString()
    }
    
    fun getReplaceMessage(search: List<String>, replace: List<String>, key: String, prefix: Boolean = true): String {
        var message = getMessage(key, prefix)
        for (i in 0..search.size - 1) {
            message = message.replace(search[i], replace[i])
        }
        return message
    }
}