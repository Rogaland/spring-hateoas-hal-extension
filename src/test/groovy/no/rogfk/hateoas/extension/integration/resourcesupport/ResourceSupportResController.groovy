package no.rogfk.hateoas.extension.integration.resourcesupport

import no.rogfk.hateoas.extension.annotations.EnableHalExtension
import no.rogfk.hateoas.extension.annotations.HalResource
import no.rogfk.hateoas.extension.integration.common.TestDto
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.hateoas.Link
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@EnableHalExtension
@SpringBootApplication
@RestController
@RequestMapping(value = "/resourcesupport", method = RequestMethod.GET, produces = [MediaType.APPLICATION_JSON_VALUE, MediaType.APPLICATION_XML_VALUE])
class ResourceSupportResController {

    @HalResource
    @RequestMapping("/single")
    ResponseEntity<TestDto> getSingle() {
        return ResponseEntity.ok(TestDto.create())
    }

    @HalResource(pageSize = 5)
    @RequestMapping("/list")
    ResponseEntity<List<TestDto>> getList(@RequestParam int page, @RequestParam String text) {
        return ResponseEntity.ok([TestDto.create(), TestDto.create()])
    }

    @HalResource(pageSize = 5)
    @RequestMapping("/list-custom-page-param")
    ResponseEntity<List<TestDto>> getListCustomPageParam(@RequestParam(name = "page") int pageNumber) {
        return ResponseEntity.ok([TestDto.create(), TestDto.create()])
    }

    @HalResource
    @RequestMapping("/missing-page-param")
    ResponseEntity<List<TestDto>> missingPageParam() {
        return ResponseEntity.ok([TestDto.create(), TestDto.create()])
    }

    @HalResource
    @RequestMapping("/wrong-page-param-type")
    ResponseEntity<List<TestDto>> wrongPageParamType(@RequestParam String page) {
        return ResponseEntity.ok([TestDto.create(), TestDto.create()])
    }

    @HalResource
    @RequestMapping("/existing-link")
    ResponseEntity<TestDto> getExistingLink() {
        TestDto testDto = TestDto.create()
        testDto.add(new Link("http://test", Link.REL_SELF))
        return ResponseEntity.ok(testDto)
    }
}
