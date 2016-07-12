package no.rogfk.hateoas.extension.integration.annotations

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.IntegrationTest
import org.springframework.boot.test.SpringApplicationConfiguration
import spock.lang.Specification

@SpringApplicationConfiguration(classes = [TestConfig, TestRelProvider])
@IntegrationTest
class EnableHalHypermediaSupportSpec extends Specification {

    @Autowired
    private TestConfig testConfig

    def "Validate @EnableHalHypermediaSupport"() {
        when:
        def provider = testConfig.delegatingRelProvider
        def factory = testConfig.halResFactory

        then:
        provider != null
        factory != null
    }

}
