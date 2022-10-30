package io.github.donghune.api.extensions

import org.bukkit.World
import org.bukkit.block.Block
import org.bukkit.util.BoundingBox

fun BoundingBox.toBlocks(world: World): List<Block> {
    val blocks = mutableListOf<Block>()
    for (x in minX.toInt()..maxX.toInt()) {
        for (y in minY.toInt()..maxY.toInt()) {
            for (z in minZ.toInt()..maxZ.toInt()) {
                blocks.add(world.getBlockAt(x, y, z))
            }
        }
    }
    return blocks
}