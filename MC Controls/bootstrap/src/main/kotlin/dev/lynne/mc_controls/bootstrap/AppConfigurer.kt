package dev.lynne.mc_controls.bootstrap

import dev.lynne.mc_controls.bootstrap.mode.ResolvedMode
import org.springframework.boot.SpringApplication

class AppConfigurer {
    fun configure(app: SpringApplication, resolvedMode: ResolvedMode) {
        app.setAdditionalProfiles(*resolvedMode.profilesToAdd.toTypedArray())
        resolvedMode.configure.invoke(app)
    }
}