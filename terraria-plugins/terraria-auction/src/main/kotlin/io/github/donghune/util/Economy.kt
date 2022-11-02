package io.github.donghune.util

import io.github.donghune.auctionPlugin
import org.bukkit.entity.Player


var Player.money: Int
    get() = auctionPlugin.economy.getBalance(this).toInt()
    set(value) {
        auctionPlugin.economy.depositPlayer(this, value.toDouble())
    }