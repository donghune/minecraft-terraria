package io.github.donghune

import io.github.monun.kommand.kommand

object AuctonCommand {
    fun initialize(plugin: AuctionPlugin) {
        plugin.kommand {
            register("auction") {
                executes {
                    player.sendMessage("A")
                }
            }
        }
    }
}