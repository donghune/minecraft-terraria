package io.github.donghune

import io.github.donghune.inventory.AuctionCategory
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.create
import io.github.monun.kommand.KommandArgument.Companion.dynamicByEnum
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*

object AuctonCommand {

    private val auctionCategoryEnum = dynamicByEnum(EnumSet.allOf(AuctionCategory::class.java))

    private val korCommandDescription = listOf(
        "경매장 등록 <category> <price>"
    )

    private val engCommandDescription = listOf(
        "auction register <category> <price>"
    )

    fun initialize(plugin: AuctionPlugin) {
        plugin.kommand {
            register("auction") {
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
                executes {
                    sender.sendCommandDescription(engCommandDescription)
                }
            }
            register("경매장") {
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
                executes {
                    sender.sendCommandDescription(korCommandDescription)
                }
            }
        }
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

    private fun CommandSender.sendCommandDescription(commands : List<String>) {
        sendMessage("")
        commands.forEach {
            sendMessage(it)
        }
        sendMessage("")
    }
}