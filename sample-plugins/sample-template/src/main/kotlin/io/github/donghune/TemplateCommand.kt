package io.github.donghune

import io.github.monun.kommand.kommand

object TemplateCommand {
    fun initialize(plugin: TemplatePlugin) {
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