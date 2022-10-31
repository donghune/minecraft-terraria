package io.github.donghune.inventory

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.inventory.GUI
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.entity.Player

class CategoryInventory(player: Player) : GUI(player, "카테고리", 9) {
    companion object {
        private val weapon = ItemStackFactory()
            .setDisplayName(
                Component.text(AuctionCategory.WEAPON.kor).color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false)
            )
            .build()
        private val tool = ItemStackFactory()
            .setDisplayName(
                Component.text(AuctionCategory.TOOL.kor).color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false)
            )
            .build()
        private val equipment = ItemStackFactory()
            .setDisplayName(
                Component.text(AuctionCategory.EQUIPMENT.kor).color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false)
            )
            .build()
        private val accessory = ItemStackFactory()
            .setDisplayName(
                Component.text(AuctionCategory.ACCESSORY.kor).color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false)
            )
            .build()
        private val etc = ItemStackFactory()
            .setDisplayName(
                Component.text(AuctionCategory.ETC.kor).color(NamedTextColor.WHITE)
                    .decoration(TextDecoration.ITALIC, false)
            )
            .build()
    }

    init {
        inventory {
            button(weapon, 0) { BuyInventory(player, AuctionCategory.WEAPON).open() }
            button(tool, 1) { BuyInventory(player, AuctionCategory.TOOL).open() }
            button(equipment, 2) { BuyInventory(player, AuctionCategory.EQUIPMENT).open() }
            button(accessory, 3) { BuyInventory(player, AuctionCategory.ACCESSORY).open() }
            button(etc, 4) { BuyInventory(player, AuctionCategory.ETC).open() }
        }
    }
}