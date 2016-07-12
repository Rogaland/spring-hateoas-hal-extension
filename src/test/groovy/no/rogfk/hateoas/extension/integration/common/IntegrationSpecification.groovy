package no.rogfk.hateoas.extension.integration.common

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.TestRestTemplate
import org.springframework.hateoas.hal.HalLinkDiscoverer
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

abstract class IntegrationSpecification extends Specification {

    @Value('${local.server.port}')
    private int port

    protected String baseUrl;
    protected RestTemplate restTemplate
    protected HalLinkDiscoverer linkDiscoverer

    void setup() {
        baseUrl = "http://localhost:${port}"
        restTemplate = new TestRestTemplate()
        linkDiscoverer = new HalLinkDiscoverer()
    }
}
