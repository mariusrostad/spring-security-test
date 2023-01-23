package no.mariusrostad.springsecuritytesting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.boot.runApplication
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity
import reactor.tools.agent.ReactorDebugAgent

@SpringBootApplication
@EnableWebFluxSecurity
@EnableConfigurationProperties
@ConfigurationPropertiesScan
class SpringSecurityTestingApplication

fun main(args: Array<String>) {
    ReactorDebugAgent.init()
    runApplication<SpringSecurityTestingApplication>(*args)
}
