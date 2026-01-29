package dev.lynne.mc_controls.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
class McControlsApplication

fun main(args: Array<String>) {
    Bootstrap().run(McControlsApplication::class.java, args)
}
