package dev.lynne.mc_controls.bootstrap.mode

import dev.lynne.mc_controls.bootstrap.exception.command.MissingCommandException
import dev.lynne.mc_controls.bootstrap.exception.command.NoSuchCommandException

class ModeDetector(
    private val commandToMode: Map<String, Mode> = mapOf(
        "serve" to Mode.SERVER,
        "cli" to Mode.CLI
    )
) {
    fun detect(args: Array<String>): Mode {
        val baseCommand = getBaseCommand(args) ?: throw MissingCommandException(commandToMode.keys)
        return commandToMode[baseCommand] ?: throw NoSuchCommandException(baseCommand, commandToMode.keys)
    }

    private fun getBaseCommand(args: Array<String>): String? =
        args.asSequence()
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .firstOrNull { !it.startsWith("-") }
}