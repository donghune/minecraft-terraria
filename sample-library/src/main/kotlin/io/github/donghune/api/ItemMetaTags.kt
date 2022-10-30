package io.github.donghune.api

import io.github.donghune.api.serialization.json
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.encodeToString
import org.bukkit.NamespacedKey
import org.bukkit.inventory.meta.ItemMeta
import org.bukkit.persistence.PersistentDataContainer
import org.bukkit.persistence.PersistentDataType

val ItemMeta.tags: ItemMetaTags
    get() = ItemMetaTags(persistentDataContainer)

class ItemMetaTags(val persistentDataContainer: PersistentDataContainer) {
    inline fun <reified T> set(tag: String, value: T) {
        val key = NamespacedKey(plugin, tag)
        when (value) {
            is Byte -> persistentDataContainer.set(key, PersistentDataType.BYTE, value)
            is Short -> persistentDataContainer.set(key, PersistentDataType.SHORT, value)
            is Int -> persistentDataContainer.set(key, PersistentDataType.INTEGER, value)
            is Long -> persistentDataContainer.set(key, PersistentDataType.LONG, value)
            is Float -> persistentDataContainer.set(key, PersistentDataType.FLOAT, value)
            is Double -> persistentDataContainer.set(key, PersistentDataType.DOUBLE, value)
            is String -> persistentDataContainer.set(key, PersistentDataType.STRING, value)
            is ByteArray -> persistentDataContainer.set(key, PersistentDataType.BYTE_ARRAY, value)
            is IntArray -> persistentDataContainer.set(key, PersistentDataType.INTEGER_ARRAY, value)
            is LongArray -> persistentDataContainer.set(key, PersistentDataType.LONG_ARRAY, value)
            else -> persistentDataContainer.set(key, PersistentDataType.STRING, json.encodeToString(value))
        }
    }

    inline fun <reified T> getObject(tag: String) : T? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.STRING)?.let { json.decodeFromString(it) }
    }

    fun getByte(tag: String): Byte? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.BYTE)
    }

    fun getShort(tag: String): Short? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.SHORT)
    }

    fun getInt(tag: String): Int? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.INTEGER)
    }

    fun getLong(tag: String): Long? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.LONG)
    }

    fun getFloat(tag: String): Float? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.FLOAT)
    }

    fun getDouble(tag: String): Double? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.DOUBLE)
    }

    fun getString(tag: String): String? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.STRING)
    }

    fun getByteArray(tag: String): ByteArray? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.BYTE_ARRAY)
    }

    fun getIntArray(tag: String): IntArray? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.INTEGER_ARRAY)
    }

    fun getLongArray(tag: String): LongArray? {
        val key = NamespacedKey(plugin, tag)
        return persistentDataContainer.get(key, PersistentDataType.LONG_ARRAY)
    }
}