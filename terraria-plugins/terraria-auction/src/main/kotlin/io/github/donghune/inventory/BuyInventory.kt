package io.github.donghune.inventory

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.PagingList
import io.github.donghune.api.inventory.GUI
import io.github.donghune.inventory.dsl.MONEY
import io.github.donghune.inventory.dsl.NEXT
import io.github.donghune.inventory.dsl.PREVIOUS
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.AuctionProductManager
import io.github.donghune.model.auction
import io.github.donghune.model.update
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player

class BuyInventory(player: Player, private val category: AuctionCategory) : GUI(player, "경매장", 54) {

    companion object {
        private val DID =
            ItemStackFactory().setType(Material.BOOKSHELF)
                .setDisplayName(Component.text("찜").color(NamedTextColor.RED).decoration(TextDecoration.ITALIC, false))
                .build()
    }


    private val pager: PagingList<AuctionProduct>
        get() = PagingList(45, AuctionProductManager.getListByCategory(category))

    var cacheCursor = 0

    init {
        var isDid = false

        inventory {
            pager.apply { cursor = cacheCursor }.page().forEachIndexed { index, data ->
                setItem(index, data.toItemStack()) {
                    if (isDid) {
                        isDid = false
                        if (player.auction.dids.contains(data.uuid)) {
                            player.sendMessage("이미 찜 한 아이템 입니다.")
                            return@setItem
                        }
                        player.auction.update { currentPlayerAuction ->
                            currentPlayerAuction.dids.add(data.uuid)
                        }
                        player.sendMessage("선택한 아이템이 찜 되었습니다.")
                        return@setItem
                    }
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
            button(DID, 45) {
                isDid = true
                player.sendMessage("찜 할 아이템을 선택해주세요")
                refreshContent()
            }
        }
    }

    override suspend fun refreshContent() {
        cacheCursor = pager.cursor
        super.refreshContent()
    }
}