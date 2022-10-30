package io.github.donghune.api

import com.github.shynixn.mccoroutine.bukkit.launch
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay

fun KBukkitScheduler(function: KBukkitScheduler.() -> Unit) {
    function(KBukkitScheduler())
}

open class KBukkitScheduler {

    private var onBukkitStart: suspend () -> Unit = {}
    private var onBukkitDuringSec: suspend (Int) -> Unit = { _ -> }
    private var onBukkitStop: suspend () -> Unit = {}

    private var _isRunning = false
    val isRunning
        get() = _isRunning

    private var _totalTime = 0
    val totalTime
        get() = _totalTime

    private var _during = 0
    val during
        get() = _during

    val leftSec: Int
        get() = totalTime - during

    private var task: Job? = null

    fun start(time: Int) {
        _totalTime = time
        _during = 0
        _isRunning = true
        task = plugin.launch {
            onBukkitStart()
            for (i in 0..totalTime) {
                _during = i
                onBukkitDuringSec(leftSec)
                delay(1000)
            }
            onBukkitStop()
        }
    }

    fun stop() {
        _isRunning = false
        task?.cancel()
        plugin.launch {
            onBukkitStop()
        }
    }

    fun cancel() {
        task?.cancel()
        _isRunning = false
    }

    fun onStart(function: suspend () -> Unit) {
        onBukkitStart = function
    }

    fun onDuringSec(function: suspend (Int) -> Unit) {
        onBukkitDuringSec = function
    }

    fun onStop(function: suspend () -> Unit) {
        onBukkitStop = function
    }
}