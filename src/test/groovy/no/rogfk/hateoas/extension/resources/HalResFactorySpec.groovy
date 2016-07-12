package no.rogfk.hateoas.extension.resources

import no.rogfk.hateoas.extension.HalPagedResources
import org.springframework.hateoas.ResourceSupport
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class HalResFactorySpec extends Specification {

    HalResFactory halResFactory

    void setup() {
        halResFactory = new HalResFactory()
    }

    def "Create ResponseEntity resource"() {
        when:
        def responseEntity = halResFactory.create(ResponseEntity.ok(new HalPagedResources([], 1)))

        then:
        responseEntity instanceof ResponseEntityRes
    }

    def "Create HalPaged resource"() {
        when:
        def hal = halResFactory.create(new HalPagedResources([], 1))

        then:
        hal instanceof HalRes
    }

    def "Create ResourceSupport resource"() {
        when:
        def resourceSupport = halResFactory.create(new ResourceSupport())

        then:
        resourceSupport instanceof ResourceSupportRes
    }

    def "Create List resource"() {
        when:
        def list = halResFactory.create(new ArrayList<String>())

        then:
        list instanceof ListRes
    }

    def "Create Dto resource"() {
        when:
        def object = halResFactory.create(new Object())

        then:
        object instanceof DtoRes
    }


}
