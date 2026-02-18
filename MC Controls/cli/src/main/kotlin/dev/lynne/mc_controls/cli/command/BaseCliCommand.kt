package dev.lynne.mc_controls.cli.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder

interface BaseCliCommand {
    fun getArgument(): LiteralArgumentBuilder<Nothing?>
}