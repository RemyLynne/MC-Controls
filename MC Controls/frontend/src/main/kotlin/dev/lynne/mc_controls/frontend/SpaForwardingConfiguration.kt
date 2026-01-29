package dev.lynne.mc_controls.frontend

import org.springframework.context.annotation.Configuration
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class SpaForwardingConfiguration : WebMvcConfigurer {
    override fun addViewControllers(registry: ViewControllerRegistry) {
        registry.addViewController("/{path:^(?!api$|ws$|actuator$)[^\\.]+$}")
            .setViewName("forward:/index.html")
        registry.addViewController("/{basePath:^(?!api$|ws$|actuator$)[^\\.]+$}/**/{path:[^\\.]+$}")
            .setViewName("forward:/index.html")
    }
}