package io.github.donghune.inventory

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.inventory.GUI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player

class MainInventory(player: Player) : GUI(player, "메뉴 선택", 9) {

    companion object {
        private val CATEGORY = ItemStackFactory()
            .setDisplayName(Component.text("카테고리").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))
            .build()
        private val DID = ItemStackFactory()
            .setType(Material.BOOKSHELF)
            .setDisplayName(Component.text("찜").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))
            .build()
        private val CANCEL = ItemStackFactory()
            .setType(Material.BARRIER)
            .setDisplayName(Component.text("취소").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))
            .build()
        private val RECEIVE = ItemStackFactory()
            .setType(Material.CHEST)
            .setDisplayName(Component.text("수령").color(NamedTextColor.WHITE).decoration(TextDecoration.ITALIC, false))
            .build()
    }

    init {
        inventory {
            button(CATEGORY, 1) {
                CategoryInventory(player).open()
            }
            button(DID, 2) {
                DidInventory(player).open()
            }
            button(CANCEL, 3) {
                if (player.isOp) {
                    CancelAdminInventory(player).open()
                } else {
                    CancelInventory(player).open()
                }
            }
            button(RECEIVE, 4) {
                ReceiveInventory(player).open()
            }
        }
    }
}

