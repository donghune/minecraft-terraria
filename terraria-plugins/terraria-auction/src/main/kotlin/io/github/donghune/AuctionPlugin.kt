package io.github.donghune

import io.github.donghune.api.BasePlugin

lateinit var templatePlugin: AuctionPlugin

class AuctionPlugin : BasePlugin() {
    override fun onEnable() {
        super.onEnable()
        templatePlugin = this
        AuctonCommand.initialize(this)
    }

    override fun onDisable() {
        super.onDisable()
    }
}

