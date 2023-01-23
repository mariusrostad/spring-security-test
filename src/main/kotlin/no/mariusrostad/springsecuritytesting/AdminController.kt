package no.mariusrostad.springsecuritytesting

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/admin")
class AdminController {

    @GetMapping
    fun adminProtectedEndpoint(): Mono<String> = Mono.just("Hei Admin")
}