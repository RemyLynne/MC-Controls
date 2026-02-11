package dev.lynne.mc_controls.bootstrap

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.persistence.autoconfigure.EntityScan
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@ComponentScan(basePackages = ["dev.lynne.mc_controls"])
@EnableJpaRepositories(basePackages = ["dev.lynne.mc_controls"])
@EntityScan(basePackages = ["dev.lynne.mc_controls"])
class McControlsApplication

fun main(args: Array<String>) {
    Bootstrap().run(McControlsApplication::class.java, args)
}
