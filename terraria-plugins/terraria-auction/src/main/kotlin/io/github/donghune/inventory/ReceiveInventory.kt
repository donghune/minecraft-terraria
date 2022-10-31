package io.github.donghune.inventory

import io.github.donghune.api.PagingList
import io.github.donghune.api.extensions.hasSpace
import io.github.donghune.api.inventory.GUI
import io.github.donghune.inventory.dsl.NEXT
import io.github.donghune.inventory.dsl.PREVIOUS
import io.github.donghune.model.AuctionTradeResult
import io.github.donghune.model.auction
import io.github.donghune.util.money
import org.bukkit.entity.Player

class ReceiveInventory(player: Player) : GUI(player,"Receive", 54) {
    init {
        val pager: PagingList<AuctionTradeResult> = PagingList(45, player.auction.tradeResults)

        inventory {
            pager.page().forEachIndexed { index, data ->
                setItem(index, data.toItemStack()) {
                    data.itemStack?.let {
                        if (!player.inventory.hasSpace(it)) {
                            player.sendMessage("인벤토리 공간이 부족합니다.")
                            return@setItem
                        }

                        player.inventory.addItem(data.itemStack)
                    }

                    data.money?.let {
                        player.money += it
                    }
                }
            }
            button(PREVIOUS, 48) {
                pager.previous()
                refreshContent()
            }
            button(NEXT, 50) {
                pager.next()
                refreshContent()
            }
        }
    }
}