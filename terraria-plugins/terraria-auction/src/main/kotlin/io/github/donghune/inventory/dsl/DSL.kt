package io.github.donghune.inventory.dsl

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.extensions.moneyFormat
import io.github.donghune.api.inventory.GUI
import io.github.donghune.util.money
import net.kyori.adventure.text.Component
import net.kyori.adventure.text.format.NamedTextColor
import net.kyori.adventure.text.format.TextDecoration
import org.bukkit.Material
import org.bukkit.entity.Player
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.inventory.ItemStack

val GUI.NEXT: ItemStack
    get() = ItemStackFactory()
        .setType(Material.OAK_SIGN)
        .setDisplayName(Component.text("다음").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
        .build()
val GUI.PREVIOUS: ItemStack
    get() = ItemStackFactory()
        .setType(Material.OAK_SIGN)
        .setDisplayName(Component.text("이전").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
        .build()
val GUI.MONEY: (Player) -> ItemStack
    get() = { player ->
        ItemStackFactory()
            .setType(Material.GOLD_INGOT)
            .setDisplayName(Component.text("보유금액").color(NamedTextColor.GOLD).decoration(TextDecoration.ITALIC, false))
            .addLore(player.money.moneyFormat()).build()
    }
