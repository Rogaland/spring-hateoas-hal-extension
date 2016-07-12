package no.rogfk.hateoas.extension.integration.dto

import no.rogfk.hateoas.extension.annotations.EnableHalExtension
import no.rogfk.hateoas.extension.annotations.HalResource
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@EnableHalExtension
@SpringBootApplication
@RestController
@RequestMapping(value = "/dto", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
class DtoResController {

    @HalResource
    @RequestMapping("/single")
    public ResponseEntity getDto() {
        return ResponseEntity.ok(new NonResourceSupportDto(text: "hello world"))
    }
}
