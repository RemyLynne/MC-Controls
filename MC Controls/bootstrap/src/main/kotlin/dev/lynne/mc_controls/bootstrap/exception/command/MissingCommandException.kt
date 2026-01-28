package dev.lynne.mc_controls.bootstrap.exception.command

class MissingCommandException(val availableCommands: Set<String>) : Exception("Missing Command. Available Commands:\n- ${availableCommands.joinToString("\n- ")}")