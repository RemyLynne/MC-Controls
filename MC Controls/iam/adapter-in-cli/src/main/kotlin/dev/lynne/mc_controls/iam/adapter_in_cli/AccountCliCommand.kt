package dev.lynne.mc_controls.iam.adapter_in_cli

import com.mojang.brigadier.arguments.BoolArgumentType.bool
import com.mojang.brigadier.arguments.StringArgumentType.greedyString
import com.mojang.brigadier.arguments.StringArgumentType.word
import com.mojang.brigadier.builder.LiteralArgumentBuilder
import com.mojang.brigadier.builder.LiteralArgumentBuilder.literal
import com.mojang.brigadier.builder.RequiredArgumentBuilder.argument
import dev.lynne.mc_controls.cli.command.BaseCliCommand
import dev.lynne.mc_controls.iam.application.port.`in`.UserManagementUseCase
import dev.lynne.mc_controls.iam.domain.exception.NoCredentialsException
import dev.lynne.mc_controls.iam.domain.exception.NoSuchUserException
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import kotlin.reflect.full.memberProperties

@Component
class AccountCliCommand(
    private val userManagement: UserManagementUseCase
) : BaseCliCommand {
    private val logger = LoggerFactory.getLogger(this.javaClass)

    override fun getArgument(): LiteralArgumentBuilder<Nothing?> {
        return literal<Nothing?>("account")
            .then(getCreateCommand())
            .then(getDeleteCommand())
            .then(getListCommand())
            .then(getShowCommand())
            .then(getStatusCommand())
            .then(getCredentialsCommand())
    }

    private fun getCreateCommand() = literal<Nothing?>("create")
        .then(argument<Nothing?, String>(FIELD_USERNAME, word())
            .then(argument<Nothing?, String>("displayname", greedyString())
                .executes {
                    userManagement.create(
                        it.getArgument(FIELD_USERNAME, String::class.java),
                        it.getArgument("displayname", String::class.java)
                    )
                    1
                }))
    private fun getDeleteCommand() = literal<Nothing?>("delete")
        .then(argument<Nothing?, String>(FIELD_USERNAME, word())
            .executes {
                userManagement.delete(it.getArgument(FIELD_USERNAME, String::class.java))
                1
            })
    private fun getListCommand() = literal<Nothing?>("list")
        .executes {
            val usernames = userManagement.list().map { it.username }
            val out = usernames.joinToString(", ", "- ")
            println(out)
            1
        }
    private fun getShowCommand() = literal<Nothing?>("show")
        .then(argument<Nothing?, String>(FIELD_USERNAME, word())
            .executes {
                val username = it.getArgument(FIELD_USERNAME, String::class.java)
                val user = userManagement.get(username)
                if (user == null) {
                    logger.warn("Cannot show user `{}`: not found", username)
                    return@executes 0
                }
                println("--- user ---")
                println(user.propertiesFormat())
                val credential = userManagement.getCredentials(username)
                if (credential != null) {
                    println("--- credential ---")
                    println(credential.propertiesFormat("userId", "passwordHash"))
                }
                1
            })

    private fun getStatusCommand() = literal<Nothing?>("status")
        .then(argument<Nothing?, String>(FIELD_USERNAME, word())
            .then(argument<Nothing?, Boolean>("enabled", bool())
                .executes {
                    val username = it.getArgument(FIELD_USERNAME, String::class.java)
                    try {
                        userManagement.setStatus(
                            username,
                            it.getArgument("enabled", Boolean::class.java)
                        )
                        1
                    } catch (_: NoSuchUserException) {
                        logger.warn("Cannot change status of `{}`: not found", username)
                        0
                    }
                })
        )

    private fun getCredentialsCommand() = literal<Nothing?>("credentials")
        .then(argument<Nothing?, String>(FIELD_USERNAME, word())
            .then(getCredentialsResetCommand())
            .then(getCredentialsExpireCommand())
        )
    private fun getCredentialsResetCommand() = literal<Nothing?>("reset")
        .executes {
            val password = System.console().readPassword("New password: ")
            try {
                userManagement.credentialsReset(
                    it.getArgument(FIELD_USERNAME, String::class.java),
                    password
                )
            } finally {
                password.fill(' ')
            }
            1
        }
    private fun getCredentialsExpireCommand() = literal<Nothing?>("expire")
        .executes {
            val username = it.getArgument(FIELD_USERNAME, String::class.java)
            try {
                userManagement.credentialsExpire(username)
                1
            } catch (_: NoSuchUserException) {
                logger.warn("Cannot expire credentials of `{}`: not found", username)
                0
            } catch (_: NoCredentialsException) {
                logger.warn("Cannot expire credentials of `{}`: No credentials set", username)
                0
            }
        }

    companion object {
        private const val FIELD_USERNAME = "username"
    }
}

fun Any.propertiesFormat(
    vararg exclude: String
): String =
    this::class.memberProperties
        .filter { it.name !in exclude }
        .joinToString("\n") { prop ->
            "${prop.name.uppercase()}=${prop.getter.call(this)}"
        }