package no.rogfk.hateoas.extension.integration.pagedresources

import no.rogfk.hateoas.extension.HalResourcesConfig
import no.rogfk.hateoas.extension.integration.common.IntegrationSpecification
import groovy.json.JsonSlurper
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest

@SpringApplicationConfiguration(classes = [PagedResourcesController, HalResourcesConfig])
@WebIntegrationTest(randomPort = true)
class PagedResourcesIntegrationSpec extends IntegrationSpecification {

    @Autowired
    private PagedResourcesController testController

    def "Verify paged values"() {
        when:
        def response = restTemplate.getForObject("${baseUrl}/pagedresources", String)
        def json = new JsonSlurper().parseText(response)

        then:
        json.page_size == 5
        json.total_items == 50
        json.page_count == 10
        json.page == 1
    }
}
