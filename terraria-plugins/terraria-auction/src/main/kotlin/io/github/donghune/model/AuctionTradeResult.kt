package io.github.donghune.model

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.extensions.moneyFormat
import kotlinx.serialization.Contextual
import org.bukkit.Material
import org.bukkit.inventory.ItemStack

@kotlinx.serialization.Serializable
data class AuctionTradeResult(
    val itemStack: @Contextual ItemStack? = null,
    val money: Int? = null,
) : ItemStackable {
    override fun toItemStack(): ItemStack {
        return itemStack ?: ItemStackFactory(ItemStack(Material.PAPER)).setDisplayName(money?.moneyFormat() ?: "error")
            .build()
    }
}