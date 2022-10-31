package io.github.donghune.inventory

import io.github.donghune.api.PagingList
import io.github.donghune.api.inventory.GUI
import io.github.donghune.inventory.dsl.*
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.AuctionProductManager
import io.github.donghune.model.auction
import org.bukkit.entity.Player

class DidInventory(player: Player) : GUI(player,"찜 목록", 54) {

    init {
        val pager: PagingList<AuctionProduct> =
            PagingList(45, player.auction.dids.mapNotNull { AuctionProductManager.get(it) })

        inventory {
            pager.page().forEachIndexed { index, data ->
                setItem(index, data.toItemStack()) {
                    AuctionProductManager.trade(data, player)
                    refreshContent()
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
            button(MONEY(player), 53) {

            }
        }
    }
}