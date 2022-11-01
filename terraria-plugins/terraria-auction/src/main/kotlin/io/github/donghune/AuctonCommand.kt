package io.github.donghune

import io.github.donghune.inventory.AuctionCategory
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.create
import io.github.monun.kommand.kommand
import org.bukkit.command.Command
import org.bukkit.command.CommandExecutor
import org.bukkit.command.CommandSender
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*

object AuctonCommand {
    fun initialize(plugin: AuctionPlugin) {
        val auctionExcutor = object : CommandExecutor {
            override fun onCommand(
                sender: CommandSender,
                command: Command,
                label: String,
                args: Array<out String>,
            ): Boolean {
                val input = args.joinToString(" ")
                if (input.matches("등록 [ㄱ-힣]+ \\d+".toRegex()) || input.matches("register [ㄱ-힣]+ \\d+".toRegex())) {
                    val player = sender as Player

                    val category = AuctionCategory.values().find { it.kor == args[1] }
                    val price = args[2].toInt()

                    if (category == null) {
                        player.sendMessage("카테고리가 올바르지 않습니다. 무기/도구/장비/장신구/기타")
                        return false
                    }

                    AuctionProduct(
                        uuid = UUID.randomUUID().toString(),
                        itemStack = player.inventory.itemInMainHand,
                        seller = player.uniqueId,
                        price = price,
                        category = category,
                        createDate = LocalDateTime.now()
                    ).create()
                    player.sendMessage("경매장에 물건이 등록되었습니다.")
                }
                return true
            }
        }
        plugin.getCommand("경매장")?.setExecutor(auctionExcutor)
        plugin.getCommand("auction")?.setExecutor(auctionExcutor)
    }
}