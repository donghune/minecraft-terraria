package io.github.donghune.model

import io.github.donghune.api.manager.Entity
import io.github.donghune.api.manager.EntityManager
import kotlinx.serialization.Contextual
import org.bukkit.entity.Player
import java.util.*

@kotlinx.serialization.Serializable
data class PlayerAuction(
    val uuid: String,
    val dids: MutableList<String> = mutableListOf(),
    val tradeResults: MutableList<@Contextual AuctionTradeResult> = mutableListOf(),
) : Entity(uuid)

fun PlayerAuction.create() = PlayerAuctionManager.create(this)
fun PlayerAuction.update(block: (PlayerAuction) -> Unit) = PlayerAuctionManager.update(this.apply(block))
fun PlayerAuction.delete() = PlayerAuctionManager.delete(this)

object PlayerAuctionManager : EntityManager<PlayerAuction>(
    "players",
    PlayerAuction.serializer(),
    { PlayerAuction(it) }
)

val Player.auction: PlayerAuction
    get() = PlayerAuctionManager.getSafety(uniqueId.toString())