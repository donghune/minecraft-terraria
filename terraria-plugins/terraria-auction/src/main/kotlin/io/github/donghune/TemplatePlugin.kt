package io.github.donghune

import io.github.donghune.api.BasePlugin

lateinit var templatePlugin: TemplatePlugin

class TemplatePlugin : BasePlugin() {
    override fun onEnable() {
        super.onEnable()
        templatePlugin = this
        TemplateCommand.initialize(this)
    }

    override fun onDisable() {
        super.onDisable()
    }
}

