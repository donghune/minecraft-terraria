package io.github.donghune.inventory

import io.github.donghune.api.PagingList
import io.github.donghune.api.displayNameOrMaterial
import io.github.donghune.api.extensions.hasSpace
import io.github.donghune.api.inventory.GUI
import io.github.donghune.inventory.dsl.NEXT
import io.github.donghune.inventory.dsl.PREVIOUS
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.AuctionProductManager
import io.github.donghune.model.config
import io.github.donghune.model.delete
import io.github.donghune.util.money
import org.bukkit.entity.Player

class CancelInventory(player: Player) : GUI(player, "취소", 54) {

    private val pager: PagingList<AuctionProduct>
        get() = PagingList(45, AuctionProductManager.getList().sortedBy { it.itemStack.displayNameOrMaterial })

    var cacheCursor = 0

    init {
        inventory {
            pager.apply { cursor = cacheCursor }.page().forEachIndexed { index, data ->
                setItem(index, data.toItemStack()) {
                    if (!player.inventory.hasSpace(data.itemStack)) {
                        player.sendMessage("인벤토리에 공간이 없습니다.")
                        return@setItem
                    }

                    data.delete()
                    player.inventory.addItem(data.itemStack)
                    player.money += (data.price * config.cancelTax).toInt()
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

    override suspend fun refreshContent() {
        cacheCursor = pager.cursor
        super.refreshContent()
    }
}