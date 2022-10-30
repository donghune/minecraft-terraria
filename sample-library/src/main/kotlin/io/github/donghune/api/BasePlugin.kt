package io.github.donghune.api

import org.bukkit.plugin.java.JavaPlugin

lateinit var plugin: JavaPlugin

open class BasePlugin : JavaPlugin() {
    override fun onEnable() {
        plugin = this
    }
}

