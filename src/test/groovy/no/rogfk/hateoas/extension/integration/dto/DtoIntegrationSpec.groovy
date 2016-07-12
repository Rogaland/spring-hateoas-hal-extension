package no.rogfk.hateoas.extension.integration.dto

import no.rogfk.hateoas.extension.integration.common.IntegrationSpecification
import no.rogfk.hateoas.extension.integration.common.JsonAsserts
import no.rogfk.hateoas.extension.integration.hal.HalResController
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.hateoas.Link

@SpringApplicationConfiguration(classes = [HalResController, DtoResController])
@WebIntegrationTest(randomPort = true)
class DtoIntegrationSpec extends IntegrationSpecification {

    @Autowired
    private DtoResController dtoResController

    def "Return DTO resource"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/dto/single", String)

        then:
        JsonAsserts.validJson(response)
        JsonAsserts.containsHalLinks(response, Link.REL_SELF)
    }
}
