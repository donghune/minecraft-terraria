package io.github.donghune

import io.github.monun.kommand.kommand

object AuctonCommand {
    fun initialize(plugin: AuctionPlugin) {
        plugin.kommand {
            register("bingo") {
                then("start") {
                    executes {
                        // TODO: 2022/10/30
                    }
                }
            }
        }
    }
}