package dev.lynne.mc_controls.cli

import com.mojang.brigadier.CommandDispatcher
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.exceptions.CommandSyntaxException
import dev.lynne.mc_controls.spring.ServerTypeProfiles
import org.slf4j.LoggerFactory
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Profile
import kotlin.system.exitProcess

@SpringBootApplication
@Profile(ServerTypeProfiles.CLI)
class McControlsCli(
    baseArguments: List<BaseCliArgument>
) : CommandLineRunner {
    private val dispatcher = CommandDispatcher<Nothing?>()
    private val rootCommand = literal<Nothing?>("cli")

    private val logger = LoggerFactory.getLogger(this.javaClass)

    init {
        logger.debug("Found ${baseArguments.size} base arguments")
        baseArguments.forEach { rootCommand.then(it.getArgument()) }
        dispatcher.register(rootCommand)
    }

    override fun run(vararg args: String) {
        val args = args
            .map { it.trim() }
            .filter { it.isNotEmpty() }
            .filter { !it.startsWith("-") }

        try {
            dispatcher.execute(args.joinToString(" "), null)
        } catch (e: CommandSyntaxException) {
            logger.error(e.message)
            exitProcess(-1)
        } catch (e: Exception) {
            logger.error(e.message, e)
            exitProcess(-1)
        }
    }
}