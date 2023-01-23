package no.mariusrostad.springsecuritytesting

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("from")
data class FromConfiguration(
    val resource: String,
)
