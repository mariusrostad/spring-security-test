package no.mariusrostad.springsecuritytesting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/resources")
class ResourceController(
    val fromConfiguration: FromConfiguration,
) {

    @GetMapping
    fun stringResponse(): Mono<String> {
        return Mono.just("Hei")
    }

    @GetMapping("/properties")
    fun fromResourcesFile(): Mono<String> = Mono.just(fromConfiguration.resource)
}