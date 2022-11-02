package io.github.donghune

import io.github.donghune.api.BasePlugin
import net.milkbowl.vault.economy.Economy
import org.bukkit.plugin.RegisteredServiceProvider

lateinit var auctionPlugin: AuctionPlugin

class AuctionPlugin : BasePlugin() {

    val economy: Economy by lazy {
        if (server.pluginManager.getPlugin("Vault") == null) {
            throw Exception("Can't setup vault for economy")
        }
        val rsp: RegisteredServiceProvider<Economy> =
            server.servicesManager.getRegistration(Economy::class.java)
                ?: throw Exception("Can't setup vault for economy")
        rsp.provider
    }

    override fun onEnable() {
        super.onEnable()
        auctionPlugin = this
        AuctonCommand.initialize(this)
    }

}

