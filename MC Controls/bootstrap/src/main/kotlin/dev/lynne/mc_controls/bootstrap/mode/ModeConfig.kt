package dev.lynne.mc_controls.bootstrap.mode

import dev.lynne.mc_controls.spring.ServerType
import org.springframework.boot.WebApplicationType

class ModeConfig(
    private val resolvedRoleMap: Map<Mode, ResolvedMode> = mapOf(
        Mode.SERVER to ResolvedMode(
            setOf(ServerType.SERVER())
        ) {
            it.setWebApplicationType(WebApplicationType.SERVLET)
        },
        Mode.CLI to ResolvedMode(
            setOf(ServerType.CLI())
        ) {
            it.setWebApplicationType(WebApplicationType.NONE)
        }
    )
) {
    fun forMode(mode: Mode): ResolvedMode {
        return resolvedRoleMap[mode] ?: throw IllegalArgumentException("Mode $mode not found")
    }
}