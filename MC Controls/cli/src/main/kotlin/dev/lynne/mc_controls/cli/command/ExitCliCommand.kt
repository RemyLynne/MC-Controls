package dev.lynne.mc_controls.cli.command

import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import dev.lynne.mc_controls.cli.CliState
import org.springframework.stereotype.Component

@Component
class ExitCliCommand : BaseCliCommand {
    override fun getArgument(): LiteralArgumentBuilder<Nothing?> {
        return literal<Nothing?>("exit")
            .executes {
                CliState.running = false
                1
            }
    }
}