package io.github.donghune.inventory

import io.github.donghune.api.PagingList
import io.github.donghune.api.displayNameOrMaterial
import io.github.donghune.api.inventory.GUI
import io.github.donghune.inventory.dsl.NEXT
import io.github.donghune.inventory.dsl.PREVIOUS
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.AuctionProductManager
import io.github.donghune.model.delete
import org.bukkit.entity.Player

class CancelAdminInventory(player: Player) : GUI(player, "취소 ( 관리자 모드 )", 54) {
    private val pager: PagingList<AuctionProduct>
        get() = PagingList(45, AuctionProductManager.getList().sortedBy { it.itemStack.displayNameOrMaterial })

    var cacheCursor = 0

    init {

        inventory {
            pager.apply { cursor = cacheCursor }.page().forEachIndexed { index, data ->
                setItem(index, data.toItemStack()) {
                    cacheCursor = pager.cursor
                    data.delete()
                    player.sendMessage("아이템을 취소하였습니다.")
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
        }
    }
}