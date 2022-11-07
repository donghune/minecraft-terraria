package io.github.donghune

import io.github.donghune.api.plugin
import io.github.donghune.inventory.AuctionCategory
import io.github.donghune.inventory.MainInventory
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.create
import io.github.monun.kommand.getValue
import io.github.monun.kommand.kommand
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*

object AuctionCommand {
    fun initialize() {
        plugin.kommand {
            register("auction") {
                then("open") {
                    executes {
                        openAuctionMenu(player)
                    }
                }
                then("register") {
                    then("category" to dynamicByEnum(EnumSet.allOf(AuctionCategory::class.java))) {
                        then("price" to int()) {
                            executes {
                                val category: AuctionCategory by it
                                val price: Int by it
                                registerProduct(player, category, price)
                            }
                        }
                    }
                }
            }
        }
    }

    private fun openAuctionMenu(player: Player) {
        MainInventory(player).open()
    }

    private fun registerProduct(player: Player, auctionCategory: AuctionCategory, price: Int) {
        AuctionProduct(
            uuid = UUID.randomUUID().toString(),
            itemStack = player.inventory.itemInMainHand,
            seller = player.uniqueId,
            price = price,
            category = auctionCategory,
            createDate = LocalDateTime.now()
        ).create()

        player.sendMessage("경매장에 물건이 등록되었습니다.")
    }
}