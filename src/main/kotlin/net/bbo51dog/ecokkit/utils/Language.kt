package net.bbo51dog.ecokkit.utils

class Language(messages: Map<String, Map<String, Any>>, default: String) {

    private val messages = messages
    
    private val default = default
    
    fun getMessage(key: String, lang: String = "default", prefix: Boolean = true): String {
        val selected: String = if (lang == "default") {
            default
        } else {
            lang
        }
        val message = if (prefix) {
            require(messages[selected] != null, {"Language ${selected} not found"})
            messages[selected]?.get("prefix").toString() + messages[selected]?.get(key).toString()
        } else {
            messages[selected]?.get(key)
        }
        return message.toString()
    }
    
    fun getReplaceMessage(search: List<String>, replace: List<String>, key: String, lang: String = "default", prefix: Boolean = true): String {
        var message = getMessage(key, lang, prefix)
        for (i in 0..search.size - 1) {
            message = message.replace(search[i], replace[i])
        }
        return message
    }
}