package io.github.donghune

import io.github.donghune.inventory.AuctionCategory
import io.github.donghune.inventory.MainInventory
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.create
import io.github.monun.kommand.KommandArgument.Companion.dynamicByEnum
import io.github.monun.kommand.PluginKommand
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import io.github.monun.kommand.node.LiteralNode
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*

object AuctonCommand {

    private val auctionCategoryEnum = dynamicByEnum(EnumSet.allOf(AuctionCategory::class.java))

    fun initialize(plugin: AuctionPlugin) {
        plugin.kommand {
            register(
                command = "auction",
                description = listOf(
                    "auction open",
                    "auction register <category> <price>"
                )
            ) {
                then("register") {
                    then("category" to auctionCategoryEnum) {
                        then("price" to int()) {
                            executes {
                                val category: AuctionCategory by it
                                val price: Int by it

                                registerProduct(
                                    player,
                                    category,
                                    price
                                )
                            }
                        }
                    }
                }
                then("open") {
                    executes {
                        openAuctionMenu(player)
                    }
                }
            }
            register(
                command = "경매장",
                description = listOf(
                    "경매장 열기",
                    "경매장 등록 <category> <price>"
                )
            ) {
                then("등록") {
                    then("category" to auctionCategoryEnum) {
                        then("price" to int()) {
                            executes {
                                val category: AuctionCategory by it
                                val price: Int by it

                                registerProduct(
                                    player,
                                    category,
                                    price
                                )
                            }
                        }
                    }
                }
                then("열기") {
                    executes {
                        openAuctionMenu(player)
                    }
                }
            }
        }
    }

    private fun openAuctionMenu(player: Player) {
        MainInventory(player).open()
    }

    private fun registerProduct(player: Player, category: AuctionCategory, price: Int) {
        AuctionProduct(
            uuid = UUID.randomUUID().toString(),
            itemStack = player.inventory.itemInMainHand,
            seller = player.uniqueId,
            price = price,
            category = category,
            createDate = LocalDateTime.now()
        ).create()
        player.sendMessage("경매장에 물건이 등록되었습니다.")
    }

    private fun PluginKommand.register(command: String, description: List<String>, init: LiteralNode.() -> Unit) {
        register(command) {
            init()
            executes {
                sender.sendMessage("")
                sender.sendMessage("========== [ $command 도움말 ] ==========")
                description.forEach {
                    sender.sendMessage(it)
                }
                sender.sendMessage("")
            }
        }
    }
}