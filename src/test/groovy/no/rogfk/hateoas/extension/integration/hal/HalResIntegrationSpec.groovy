package no.rogfk.hateoas.extension.integration.hal

import no.rogfk.hateoas.extension.integration.common.JsonAsserts
import no.rogfk.hateoas.extension.HalResourcesConfig
import no.rogfk.hateoas.extension.integration.common.IntegrationSpecification
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.SpringApplicationConfiguration
import org.springframework.boot.test.WebIntegrationTest
import org.springframework.hateoas.Link

@SpringApplicationConfiguration(classes = [HalResController, HalResourcesConfig])
@WebIntegrationTest(randomPort = true)
class HalResIntegrationSpec extends IntegrationSpecification {

    @Autowired
    private HalResController controller

    def "Return HalPagedResources"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/halpagedresources", String)

        then:
        JsonAsserts.validPagedHalJson(response)
    }

    def "Return HalPagedResources in ResponseEntity"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/responseentity", String)

        then:
        JsonAsserts.validPagedHalJson(response)
        JsonAsserts.jsonContains(response, "_embedded")
    }

    def "Do not add previous link if it exists"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/existing-link", String)

        then:
        JsonAsserts.validPagedHalJson(response)
        linkDiscoverer.findLinksWithRel(Link.REL_PREVIOUS, response.getBody())[0].href == "http://test"
    }

    def "Add all request parameters"() {
        given:
        def initial = "${baseUrl}/hal/allqueryparams?test1=1&test2=2"

        when:
        def response = restTemplate.getForEntity(initial, String)
        def links = linkDiscoverer.findLinksWithRel(Link.REL_SELF, response.getBody())

        then:
        JsonAsserts.validPagedHalJson(response)
        links[0].href == "${initial}&page=1".toString()
    }

    def "Add allowed query parameters"() {
        given:
        def initial = "${baseUrl}/hal/allowedqueryparams?test1=1&test2=2"
        def expected = "${baseUrl}/hal/allowedqueryparams?test1=1&page=1"

        when:
        def response = restTemplate.getForEntity(initial, String)
        def links = linkDiscoverer.findLinksWithRel(Link.REL_SELF, response.getBody())

        then:
        JsonAsserts.validPagedHalJson(response)
        links[0].href == expected.toString()
    }

    def "Set pageSize parameter in url"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/pagesizeparam?pageSize=2", String)

        then:
        JsonAsserts.jsonContains(response, "page_size", "2")
    }

    def "Do not change pageSize for invalid input"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/pagesizeparam?pageSize=2abc", String)

        then:
        JsonAsserts.jsonContains(response, "page_size", "1")
    }

    def "Disable client page size"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/disabledpagesize?pageSize=2", String)

        then:
        JsonAsserts.jsonContains(response, "page_size", "1")
    }

    def "Do not return more than max page size"() {
        when:
        def response = restTemplate.getForEntity("${baseUrl}/hal/maxpagesize?pageSize=3000", String)

        then:
        JsonAsserts.jsonContains(response, "page_size", "2")
    }

}
