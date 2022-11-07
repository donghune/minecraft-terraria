package io.github.donghune.model

import io.github.donghune.api.ItemStackFactory
import io.github.donghune.api.displayNameOrMaterial
import io.github.donghune.api.extensions.moneyFormat
import io.github.donghune.api.manager.Entity
import io.github.donghune.api.manager.EntityManager
import io.github.donghune.inventory.AuctionCategory
import io.github.donghune.util.money
import kotlinx.serialization.Contextual
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.inventory.ItemStack
import java.time.LocalDateTime
import java.util.*

@kotlinx.serialization.Serializable
data class AuctionProduct(
    val uuid: String,
    val itemStack: @Contextual ItemStack,
    val seller: @Contextual UUID,
    val price: Int,
    val category: AuctionCategory,
    val createDate: @Contextual LocalDateTime,
) : Entity(uuid), ItemStackable {
    override fun toItemStack(): ItemStack {
        return ItemStackFactory(itemStack)
            .addLore(" ")
            .addLore("판매자 : ${Bukkit.getPlayer(seller)?.name}")
            .addLore("판매금액 : ${price.moneyFormat()}")
            .build()
    }

    fun seller() = Bukkit.getPlayer(seller)!!
}

fun AuctionProduct.create() = AuctionProductManager.create(this)
fun AuctionProduct.update(block: (AuctionProduct) -> Unit) = AuctionProductManager.update(this.apply(block))
fun AuctionProduct.delete() = AuctionProductManager.delete(this)

object AuctionProductManager : EntityManager<AuctionProduct>(
    "products",
    AuctionProduct.serializer()
) {
    fun getListByCategory(category: AuctionCategory): List<AuctionProduct> {
        return getList().filter { it.category == category }.sortedBy { it.itemStack.displayNameOrMaterial }
    }

    fun getMyProducts(seller: Player): List<AuctionProduct> {
        return getList().filter { it.seller == seller.uniqueId }.sortedBy { it.itemStack.displayNameOrMaterial }
    }

    @Synchronized
    fun trade(auctionProduct: AuctionProduct, buyer: Player) {
        if (!getListByCategory(auctionProduct.category).contains(auctionProduct)) {
            buyer.sendMessage("이미 거래가 되었거나 취소된 물건 입니다.")
            return
        }

        if (auctionProduct.seller.toString() == buyer.uniqueId.toString()) {
            buyer.sendMessage("본인의 물건은 구매 할 수 없습니다.")
            return
        }

        if (buyer.money < auctionProduct.price) {
            buyer.sendMessage("소지한 금액이 부족합니다.")
            return
        }

        buyer.auction.update { currentPlayerAuction ->
            currentPlayerAuction.tradeResults.add(
                AuctionTradeResult(
                    itemStack = auctionProduct.itemStack
                )
            )
        }

        auctionProduct.seller().auction.update { currentPlayerAuction ->
            currentPlayerAuction.tradeResults.add(
                AuctionTradeResult(
                    money = (auctionProduct.price * (1 - auctionConfig.tradeTax)).toInt()
                )
            )
        }

        auctionConfig.reserveTax((auctionProduct.price * auctionConfig.tradeTax).toInt())

        buyer.sendMessage("거래가 성사되었습니다. 경매장에서 수령해주세요")
        auctionProduct.seller()
            .sendMessage("${auctionProduct.itemStack.displayNameOrMaterial} 아이템이 판매되었습니다. 경매장에서 수령해주세요")
    }
}