package io.github.donghune

import io.github.donghune.inventory.AuctionCategory
import io.github.donghune.inventory.MainInventory
import io.github.donghune.model.AuctionProduct
import io.github.donghune.model.create
import org.bukkit.command.Command
import org.bukkit.command.CommandSender
import org.bukkit.command.TabExecutor
import org.bukkit.entity.Player
import java.time.LocalDateTime
import java.util.*

object AuctonCommand {
    fun initialize(plugin: AuctionPlugin) {
        plugin.getCommand("auction")?.setExecutor(object : TabExecutor {
            override fun onCommand(
                sender: CommandSender,
                command: Command,
                label: String,
                args: Array<out String>,
            ): Boolean {
                if (args.isEmpty()) {
                    sender.sendMessage("auction open")
                    sender.sendMessage("auction register <category> <price>")
                    return true
                }
                when (args[0]) {
                    "register" -> {
                        if (args.size != 3) {
                            return true
                        }

                        val category = AuctionCategory.values().find { it.name == args[1] }
                        val price = args[2].toInt()

                        if (category == null) {
                            sender.sendMessage("auction register <category> <price>")
                            return true
                        }

                        registerProduct(
                            sender as Player,
                            category,
                            price
                        )
                    }
                    "open" -> {
                        openAuctionMenu(sender as Player)
                    }
                    else -> {}
                }
                return true
            }

            override fun onTabComplete(
                sender: CommandSender,
                command: Command,
                label: String,
                args: Array<out String>,
            ): List<String> {
                if (args.size == 1) {
                    return listOf("register", "open")
                } else if (args.size == 2) {
                    if (args[0] == "register") {
                        return AuctionCategory.values().map { it.toString() }.toList()
                    }
                }
                return emptyList()
            }
        })
        plugin.getCommand("경매장")?.setExecutor(object : TabExecutor {
            override fun onCommand(
                sender: CommandSender,
                command: Command,
                label: String,
                args: Array<out String>,
            ): Boolean {
                if (args.isEmpty()) {
                    sender.sendMessage("경매장 열기")
                    sender.sendMessage("경매장 등록 <카테고리> <가격>")
                    return true
                }
                when (args[0]) {
                    "등록" -> {
                        if (args.size != 3) {
                            return true
                        }

                        val category = AuctionCategory.values().find { it.kor == args[1] }
                        val price = args[2].toInt()

                        if (category == null) {
                            sender.sendMessage("경매장 등록 <카테고리> <가격>")
                            return true
                        }

                        registerProduct(
                            sender as Player,
                            category,
                            price
                        )
                    }
                    "열기" -> {
                        openAuctionMenu(sender as Player)
                    }
                    else -> {}
                }
                return true
            }

            override fun onTabComplete(
                sender: CommandSender,
                command: Command,
                label: String,
                args: Array<out String>,
            ): List<String> {
                if (args.size == 1) {
                    return listOf("등록", "열기")
                } else if (args.size == 2) {
                    if (args[0] == "등록") {
                        return AuctionCategory.values().map { it.kor }.toList()
                    }
                }
                return emptyList()
            }
        })
    }

    private fun openAuctionMenu(player: Player) {
        MainInventory(player).open()
    }

    private fun registerProduct(player: Player, category: AuctionCategory, price: Int) {
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
}