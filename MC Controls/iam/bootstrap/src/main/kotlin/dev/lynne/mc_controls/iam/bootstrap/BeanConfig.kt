package dev.lynne.mc_controls.iam.bootstrap

import dev.lynne.mc_controls.iam.application.port.out.CredentialsPort
import dev.lynne.mc_controls.iam.application.port.out.PasswordEncoder
import dev.lynne.mc_controls.iam.application.port.out.UsersPort
import dev.lynne.mc_controls.iam.application.service.UserManagementService
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class BeanConfig {
    @Bean
    fun userManagement(
        users: UsersPort,
        credentials: CredentialsPort,
        encoder: PasswordEncoder
    ) = UserManagementService(users, credentials, encoder)
}