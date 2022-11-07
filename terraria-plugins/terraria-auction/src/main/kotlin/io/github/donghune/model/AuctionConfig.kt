package io.github.donghune.model

import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager
import kotlinx.serialization.Contextual
import java.time.LocalDateTime

@kotlinx.serialization.Serializable
data class AuctionConfig(
    val tradeTax: Double = 10.0,
    val cancelTax: Double = 10.0,
    var taxTotal: Int = 0,
    var latestTaxDonation : @Contextual LocalDateTime = LocalDateTime.now()
) : Config("AuctionConfig") {
    override fun update() {
        AuctionConfigManager.update(this)
    }

    @Synchronized
    fun reserveTax(amount: Int) {
        taxTotal = amount
    }
}

object AuctionConfigManager : ConfigManager<AuctionConfig>(
    "config", AuctionConfig.serializer(), AuctionConfig()
)

val auctionConfig = AuctionConfig()