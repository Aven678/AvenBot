package fr.aven.bot.util.lang

/**
 * Manage LangKeys
 *
 * @property pkg [String] Kotlin package
 * @property clazz [String] Kotlin class
 * @property func [String] function name
 * @property key [String] key name
 */
data class LangKey(val pkg: String, val clazz: String, val func: String, val key: String) {
    override fun toString(): String = "$pkg.$clazz.$func.$key"

    companion object {
        /**
         * Create a LangKey
         * @param obj [Any] KClass
         * @param func [String] function name
         * @param key [String] key name
         */
        fun keyBuilder(obj: Any, func: String, key: String): LangKey {
            val pkg = obj.javaClass.`package`.name
            val clazz = obj.javaClass.simpleName
            return LangKey(pkg, clazz, func, key)
        }
    }
}