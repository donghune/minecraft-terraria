package io.github.donghune.api.extensions

import org.bukkit.Location
import org.bukkit.Material
import org.bukkit.block.Block
import org.bukkit.entity.Player

fun Player.isHasItemInMainHand(player: Player, message: String? = null): Boolean {
    if (player.inventory.itemInMainHand.type == Material.AIR) {
        player.sendMessage((message ?: "&c손에 아이템이 존해하지 않습니다.").chatColor())
        return false
    }
    return true
}

fun String.chatColor(): String {
    return replace("&", "§")
}

fun Pair<Location, Location>.toBlockArray(): List<Block> {
    val blockList = mutableListOf<Block>()

    if (first.world == null) {
        return emptyList()
    }

    if (second.world == null) {
        return emptyList()
    }

    val minX: Int = first.blockX.coerceAtMost(second.blockX)
    val maxX: Int = first.blockX.coerceAtLeast(second.blockX)

    val minY: Int = first.blockY.coerceAtMost(second.blockY)
    val maxY: Int = first.blockY.coerceAtLeast(second.blockY)

    val minZ: Int = first.blockZ.coerceAtMost(second.blockZ)
    val maxZ: Int = first.blockZ.coerceAtLeast(second.blockZ)

    for (x in minX..maxX) {
        for (y in minY..maxY) {
            for (z in minZ..maxZ) {
                blockList.add(first.world!!.getBlockAt(x, y, z))
            }
        }
    }
    return blockList
}