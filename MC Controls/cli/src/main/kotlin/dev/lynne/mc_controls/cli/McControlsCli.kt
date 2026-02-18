package dev.lynne.mc_controls.cli

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.lynne.mc_controls.cli.command.BaseCliCommand
import dev.lynne.mc_controls.spring.ServerTypeProfiles
import org.slf4j.LoggerFactory
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Profile
import kotlin.system.exitProcess

@SpringBootApplication
@Profile(ServerTypeProfiles.CLI)
class McControlsCli(
    baseArguments: List<BaseCliCommand>
) : ApplicationRunner {
    private val dispatcher = CommandDispatcher<Nothing?>()

    private val logger = LoggerFactory.getLogger(this.javaClass)

    init {
        logger.debug("Found ${baseArguments.size} base arguments")
        baseArguments.forEach { dispatcher.register(it.getArgument()) }
    }

    override fun run(args: ApplicationArguments) {
        val console = System.console()
        if (console == null) {
            logger.error("No system console found. CLI requires a (TTY) console to be used")
            exitProcess(1)
        }
        while (CliState.running) {
            val input = System.console().readLine("Enter command: ").trim()
            try {
                dispatcher.execute(input, null)
            } catch (e: CommandSyntaxException) {
                logger.error(e.message)
            } catch (e: Exception) {
                logger.error(e.message, e)
            }
        }
    }
}