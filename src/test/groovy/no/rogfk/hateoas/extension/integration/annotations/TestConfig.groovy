package no.rogfk.hateoas.extension.integration.annotations

import no.rogfk.hateoas.extension.resources.HalResFactory
import no.rogfk.hateoas.extension.annotations.EnableHalHypermediaSupport
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Configuration
import org.springframework.hateoas.core.DelegatingRelProvider

@EnableHalHypermediaSupport
@SpringBootApplication
@Configuration
class TestConfig {

    @Autowired
    DelegatingRelProvider delegatingRelProvider

    @Autowired
    HalResFactory halResFactory
}
