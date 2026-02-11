package dev.lynne.mc_controls.cli

import com.mojang.brigadier.tree.LiteralCommandNode

interface BaseCliArgument {
    fun getArgument(): LiteralCommandNode<Nothing?>
}