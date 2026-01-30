package dev.lynne.mc_controls.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.ComponentScan

@SpringBootApplication
@ComponentScan(basePackages = [
    "dev.lynne.mc_controls.frontend"
])
class McControlsApplication

fun main(args: Array<String>) {
    Bootstrap().run(McControlsApplication::class.java, args)
}
