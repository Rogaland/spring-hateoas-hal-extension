package no.rogfk.hateoas.extension

import org.springframework.web.util.UriComponentsBuilder
import spock.lang.Specification

class HalLinkBuilderSpec extends Specification {

    private HalLinkBuilder halLinkBuilder

    void setup() {
        halLinkBuilder = new HalLinkBuilder(UriComponentsBuilder.fromHttpUrl("http://localhost"), 2, 10)
    }

    def "Create self link"() {
        when:
        def self = halLinkBuilder.self()

        then:
        self.href == "http://localhost?page=2"
    }

    def "Create first link"() {
        when:
        def first = halLinkBuilder.first()

        then:
        first.href == "http://localhost"
    }

    def "Create next link"() {
        when:
        def next = halLinkBuilder.next()

        then:
        next.href == "http://localhost?page=3"
    }

    def "Create last link"() {
        when:
        def last = halLinkBuilder.last()

        then:
        last.href == "http://localhost?page=10"
    }

    def "Create link with query string"() {
        given:
        halLinkBuilder = new HalLinkBuilder(UriComponentsBuilder.fromHttpUrl("http://localhost"), 2, 10, "test=1")

        when:
        def last = halLinkBuilder.last()

        then:
        last.href == "http://localhost?test=1&page=10"
    }

    def "Multiple calls should not create duplicate links"() {
        when:
        halLinkBuilder.self()
        halLinkBuilder.last()
        halLinkBuilder.last()
        def last = halLinkBuilder.self()

        then:
        last.href == "http://localhost?page=2"
    }

    def "Create link with query string that contains page param"() {
        given:
        halLinkBuilder = new HalLinkBuilder(UriComponentsBuilder.fromHttpUrl("http://localhost"), 2, 10, "page=10&test=1")

        when:
        def last = halLinkBuilder.last()

        then:
        last.href == "http://localhost?test=1&page=10"
    }

    def "Remove page param from query string"() {
        when:
        def queryString = halLinkBuilder.removePageParam("page=10&test1=1&test2=2")

        then:
        queryString == "test1=1&test2=2"
    }
}
