package no.rogfk.hateoas.extension.integration.resourcesupport

import no.rogfk.hateoas.extension.integration.common.JsonAsserts
import no.rogfk.hateoas.extension.HalResourcesConfig
import no.rogfk.hateoas.extension.integration.common.IntegrationSpecification
import no.rogfk.hateoas.extension.integration.common.XmlAsserts
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.hateoas.Link
import org.springframework.http.*

@SpringApplicationConfiguration(classes = [ResourceSupportResController, HalResourcesConfig])
@WebIntegrationTest(randomPort = true)
class ResourceSupportResIntegrationSpecificationSpec extends IntegrationSpecification {

    @Autowired
    private ResourceSupportResController controller

    def "Return DTO wrapped in ResponseEntity"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/resourcesupport/single", String)

        then:
        JsonAsserts.validJson(response)
        JsonAsserts.containsHalLinks(response, Link.REL_SELF)
    }

    def "Return List of DTO in ResponseEntity"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/resourcesupport/list?page=1&text=test", String)

        then:
        JsonAsserts.validPagedHalJson(response)
    }

    def "Return List of DTO in ResponseEntity, custom name for page parameter"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/resourcesupport/list-custom-page-param?page=1", String)

        then:
        JsonAsserts.validPagedHalJson(response)
    }

    def "Throw exception for missing page param"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/resourcesupport/missing-page-param", String)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "Throw exception for wrong page param type"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/resourcesupport/wrong-page-param-type?page=test", String)

        then:
        response.statusCode == HttpStatus.INTERNAL_SERVER_ERROR
    }

    def "Do not add self link if it exists"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/resourcesupport/existing-link", String)

        then:
        JsonAsserts.validJson(response)
        JsonAsserts.containsHalLinks(response, Link.REL_SELF)
        linkDiscoverer.findLinksWithRel(Link.REL_SELF, response.body)[0].href == "http://test"
    }

    def "Return XML"() {
        given:
        HttpHeaders headers = new HttpHeaders()
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_XML))

        when:
        def response = restTemplate.exchange("$baseUrl/resourcesupport/single", HttpMethod.GET, new HttpEntity<>(headers), String)

        then:
        XmlAsserts.validHalXml(response)
    }
}
