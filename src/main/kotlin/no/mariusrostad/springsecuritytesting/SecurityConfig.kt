package no.mariusrostad.springsecuritytesting

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.web.server.ServerHttpSecurity
import org.springframework.security.web.server.SecurityWebFilterChain


@Configuration
class SecurityConfig {

    @Bean
    fun securityWebFilterChain(http: ServerHttpSecurity): SecurityWebFilterChain? {
        http
            .httpBasic()
            .and()
            .formLogin()
            .and()
            .authorizeExchange()
            .pathMatchers("/api/admin").hasRole("ADMIN")
            .pathMatchers("/api/resources/**").hasRole("RESOURCE")
        return http.build()
    }

}