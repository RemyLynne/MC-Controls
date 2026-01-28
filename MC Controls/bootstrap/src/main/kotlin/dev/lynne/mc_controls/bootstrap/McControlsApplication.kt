package dev.lynne.mc_controls.bootstrap

import org.springframework.boot.Banner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class McControlsApplication

fun main(args: Array<String>) {
    val app = SpringApplication(McControlsApplication::class.java)
    app.setBannerMode(Banner.Mode.LOG)
    app.setBanner(StartupBanner())
    app.run(*args)
}
