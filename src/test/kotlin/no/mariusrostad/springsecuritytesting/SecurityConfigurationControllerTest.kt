package no.mariusrostad.springsecuritytesting

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.ApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.security.test.context.support.WithMockUser
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.mockUser
import org.springframework.security.test.web.reactive.server.SecurityMockServerConfigurers.springSecurity
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

/**
 * This is an example of Spring Security configurations we've defined inside SecurityConfig file. My biggest
 * advise is to first test the happy path, but also the negation of the happy path. E.g. /api/resources should be
 * accessible with users that have the role RESOURCE, but also test that normal users CANNOT access the endpoint.
 * Testing security is not only about testing what people can do, but also what that cannot.
 */
@SpringBootTest
@ContextConfiguration
class SecurityConfigurationControllerTest(val context: ApplicationContext) {

    private lateinit var rest: WebTestClient

    @BeforeEach
    fun setup() {
        this.rest = WebTestClient
            .bindToApplicationContext(this.context)
            // add Spring Security test Support
            .apply(springSecurity())
            .build()
    }

    @Test
    @WithMockUser
    fun messageWhenWithMockUserThenForbidden() {
        this.rest.get().uri("/api/resource")
            .exchange()
            .expectStatus().isEqualTo(HttpStatus.FORBIDDEN)
    }

    /**
     * Test that defines a mock user with role=RESOURCE using annotations. This will magically add a user with
     * the specified role along with the request.
     */
    @Test
    @WithMockUser(roles = ["RESOURCE"])
    fun `user with role resource should have access to resources endpoint`() {
        this.rest.get().uri("/api/resources")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("Hei")
    }

    /**
     * Test that defines a mock user inline with the REST call.
     */
    @Test
    fun `admin user should have access to admin endpoint`() {
        this.rest.mutateWith(mockUser().roles("ADMIN"))
            .get().uri("/api/admin")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("Hei Admin")
    }

    /**
     * A user with only RESOURCE role should not be able to access the admin enpoint
     */
    @Test
    fun `RESOURCE role should not allow users to access admin protected endpoints`() {
        this.rest.mutateWith(mockUser().roles("RESOURCE"))
            .get().uri("/api/admin")
            .exchange()
            .expectStatus().isForbidden
    }

    @Test
    fun `users with RESOURCES role should have access to resources properties endpoint`() {
        this.rest.mutateWith(mockUser().roles("RESOURCE"))
            .get().uri("/api/resources/properties")
            .exchange()
            .expectStatus().isOk
            .expectBody<String>().isEqualTo("Hei fra resources mappa som bruker test profilen")
    }

    @Test
    fun `anonymous users should not have access to resources properties endpoint`() {
        this.rest.get().uri("/api/resources/properties")
            .exchange()
            .expectStatus().isUnauthorized
    }
}
