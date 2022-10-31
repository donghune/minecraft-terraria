package io.github.donghune.model

import org.bukkit.inventory.ItemStack

interface ItemStackable {
    fun toItemStack(): ItemStack
}