package io.github.donghune

import org.bukkit.plugin.java.JavaPlugin

lateinit var templatePlugin: AuctionPlugin

class AuctionPlugin : JavaPlugin() {
    override fun onEnable() {
        super.onEnable()
        templatePlugin = this
        AuctonCommand.initialize(this)
    }
}

