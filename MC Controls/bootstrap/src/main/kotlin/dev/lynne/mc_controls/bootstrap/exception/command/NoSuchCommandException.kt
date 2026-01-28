package dev.lynne.mc_controls.bootstrap.exception.command

class NoSuchCommandException(val attemptedCommand: String, val availableCommands: Set<String>) : Exception("Invalid command '$attemptedCommand'. Available Commands:\n- ${availableCommands.joinToString("\n- ")}")