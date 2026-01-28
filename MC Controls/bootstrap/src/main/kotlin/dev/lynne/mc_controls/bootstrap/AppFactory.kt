package dev.lynne.mc_controls.bootstrap

import org.springframework.boot.SpringApplication

interface AppFactory {
    fun create(primarySource: Class<*>): SpringApplication

    companion object {
        /**
         * Default
         */
        operator fun invoke() = object : AppFactory {
            override fun create(primarySource: Class<*>) = SpringApplication(primarySource)
        }
    }
}