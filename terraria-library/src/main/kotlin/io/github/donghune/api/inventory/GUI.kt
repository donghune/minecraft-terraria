package io.github.donghune.api.inventory

import com.github.shynixn.mccoroutine.bukkit.launch
import com.github.shynixn.mccoroutine.bukkit.registerSuspendingEvents
import io.github.donghune.api.extensions.chatColor
import io.github.donghune.api.plugin
import org.bukkit.Bukkit
import org.bukkit.entity.Player
import org.bukkit.event.EventHandler
import org.bukkit.event.Listener
import org.bukkit.event.inventory.InventoryClickEvent
import org.bukkit.event.inventory.InventoryCloseEvent
import org.bukkit.event.inventory.InventoryOpenEvent
import org.bukkit.inventory.ItemStack

abstract class GUI(
    private val player: Player,
    private val title: String,
    private val size: Int,
) : Listener {

    private val clickEvents: MutableMap<Int, suspend (InventoryClickEvent) -> Unit> = mutableMapOf()
    protected val inventory by lazy { Bukkit.createInventory(null, size, title.chatColor()) }

    fun inventory(block: suspend () -> Unit) {
        content = block
    }

    suspend fun button(itemStack: ItemStack, index: Int, block: suspend (InventoryClickEvent) -> Unit) =
        setItem(index, itemStack, block)


    fun onInventoryClose(block: suspend InventoryCloseEvent.() -> Unit) {
        onInventoryClose = block
    }

    fun onInventoryOpen(block: suspend InventoryOpenEvent.() -> Unit) {
        onInventoryOpen = block
    }

    fun onPlayerInventoryClick(isCancelled: Boolean = false, block: suspend InventoryClickEvent.() -> Unit) {
        onPlayerInventoryClick = isCancelled to block
    }


    private var content: suspend () -> Unit = {}
    private var onInventoryClose: suspend InventoryCloseEvent.() -> Unit = {}
    private var onInventoryOpen: suspend InventoryOpenEvent.() -> Unit = {}
    private var onPlayerInventoryClick: Pair<Boolean, suspend InventoryClickEvent.() -> Unit> = false to {}

    @EventHandler
    suspend fun onInventoryOpenEvent(event: InventoryOpenEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryOpen(event)
    }

    @EventHandler
    suspend fun onInventoryClickEvent(event: InventoryClickEvent) {
        if (event.clickedInventory == player.inventory && player.openInventory.topInventory == inventory) {
            event.isCancelled = onPlayerInventoryClick.first
            onPlayerInventoryClick.second(event)
            return
        }

        if (event.clickedInventory != inventory) {
            return
        }

        event.isCancelled = true
        clickEvents[event.rawSlot]?.invoke(event)
    }

    @EventHandler
    suspend fun onInventoryCloseEvent(event: InventoryCloseEvent) {
        if (event.inventory != inventory) {
            return
        }

        onInventoryClose(event)
        InventoryCloseEvent.getHandlerList().unregister(this)
        InventoryClickEvent.getHandlerList().unregister(this)
        InventoryOpenEvent.getHandlerList().unregister(this)
    }

    suspend open fun refreshContent() {
        inventory.clear()
        clickEvents.clear()
        InventoryClickEvent.getHandlerList().unregister(this@GUI)
        Bukkit.getPluginManager().registerSuspendingEvents(this@GUI, plugin)
        content()
    }

    fun open() {
        plugin.launch {
            Bukkit.getPluginManager().registerSuspendingEvents(this@GUI, plugin)
            player.openInventory(inventory)
            content()
        }
    }

    fun openLater() {
        Bukkit.getScheduler().runTaskLater(plugin, Runnable { open() }, 1L)
    }

    fun setItem(index: Int, itemStack: ItemStack, onClick: suspend InventoryClickEvent.() -> Unit = {}) {
        inventory.setItem(index, itemStack)
        clickEvents[index] = onClick
    }

}