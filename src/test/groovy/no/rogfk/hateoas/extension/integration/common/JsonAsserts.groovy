package no.rogfk.hateoas.extension.integration.common

import groovy.json.JsonSlurper
import org.springframework.hateoas.Link
import org.springframework.hateoas.hal.HalLinkDiscoverer
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

class JsonAsserts {

    static boolean validPagedHalJson(ResponseEntity<String> responseEntity) {
        if (validHttpResponse(responseEntity) &&
                validJson(responseEntity) &&
                validHalLinks(responseEntity)) {
            def json = new JsonSlurper().parseText(responseEntity.getBody())
            return (json.containsKey("page_size") && json.containsKey("total_items") &&
                    json.containsKey("page_count") && json.containsKey("page"))
        } else {
            return false
        }
    }

    private static boolean validHttpResponse(ResponseEntity<String> responseEntity) {
        return responseEntity.statusCode == HttpStatus.OK
    }

    static boolean validJson(ResponseEntity<String> responseEntity) {
        return responseEntity.headers.getContentType().includes(MediaType.APPLICATION_JSON)
    }

    private static boolean validHalLinks(ResponseEntity<String> responseEntity) {
        return (containsHalLinks(responseEntity, Link.REL_NEXT, Link.REL_PREVIOUS, Link.REL_LAST, Link.REL_FIRST, Link.REL_SELF))
    }

    static boolean jsonContains(String response, String... keys) {
        def json = new JsonSlurper().parseText(response)
        for (String key : keys) {
            if (!json.containsKey(key)) {
                return false
            }
        }
        return true
    }

    static boolean jsonContains(ResponseEntity<String> responseEntity, String... keys) {
        return jsonContains(responseEntity.body, keys);
    }

    static boolean jsonContains(ResponseEntity<String> responseEntity, String key, String value) {
        def json = new JsonSlurper().parseText(responseEntity.getBody())
        return (json.containsKey(key)) && (json[key].toString() == value.toString())
    }

    static boolean containsHalLinks(ResponseEntity<String> response, String... links) {
        HalLinkDiscoverer halLinkDiscoverer = new HalLinkDiscoverer()
        for (String link : links) {
            def halLink = halLinkDiscoverer.findLinkWithRel(link, response.body)
            if (halLink == null)
                return false
        }
        return true
    }
}
