package dev.lynne.mc_controls.iam.adapter_out_security

import dev.lynne.mc_controls.iam.application.port.out.PasswordEncoder as PE
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@Configuration
class Encoders {
    @Bean
    fun encoder() =
        BCryptPasswordEncoder()

    @Bean
    fun passwordEncoder(encoder: PasswordEncoder) =
        PE { encoder.encode(it.toString())!! }
}