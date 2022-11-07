package io.github.donghune

import io.github.donghune.api.BasePlugin
import io.github.donghune.model.auction
import io.github.donghune.model.auctionConfig
import io.github.donghune.util.money
import net.milkbowl.vault.economy.Economy
import org.bukkit.Bukkit
import org.bukkit.plugin.RegisteredServiceProvider
import java.time.DayOfWeek
import java.time.LocalDateTime
import java.time.ZoneId
import java.time.temporal.TemporalAdjusters

lateinit var auctionPlugin: AuctionPlugin

class AuctionPlugin : BasePlugin() {

    val economy: Economy by lazy {
        if (server.pluginManager.getPlugin("Vault") == null) {
            throw Exception("Can't setup vault for economy")
        }
        val rsp: RegisteredServiceProvider<Economy> =
            server.servicesManager.getRegistration(Economy::class.java)
                ?: throw Exception("Can't setup vault for economy")
        rsp.provider
    }

    override fun onEnable() {
        super.onEnable()
        auctionPlugin = this
        AuctionCommand.initialize()

        Bukkit.getScheduler().runTaskLater(
            this, kotlinx.coroutines.Runnable {
                Bukkit.getOnlinePlayers()
                    .filter { it.auction.createAt.plusDays(7) >= LocalDateTime.now() }
                    .also { players ->
                        players.forEach {
                            it.money += auctionConfig.taxTotal / players.size
                            it.sendMessage("뉴비 지원금이 도착하였습니다.")
                        }
                    }
            }, LocalDateTime.now()
                .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                .atZone(ZoneId.systemDefault()).toInstant().epochSecond / 50
        )
    }

}


