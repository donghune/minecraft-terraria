package io.github.donghune.model

import io.github.donghune.api.manager.Config
import io.github.donghune.api.manager.ConfigManager

@kotlinx.serialization.Serializable
data class AuctionConfig(
    val tradeTax: Double = 10.0,
    val cancelTax: Double = 10.0,
) : Config("AuctionConfig") {
    override fun update() {

    }
}

object AuctionConfigManager : ConfigManager<AuctionConfig>(
    "config", AuctionConfig.serializer(), AuctionConfig()
)

val config = AuctionConfig()