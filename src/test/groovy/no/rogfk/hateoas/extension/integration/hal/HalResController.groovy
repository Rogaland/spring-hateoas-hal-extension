package no.rogfk.hateoas.extension.integration.hal

import no.rogfk.hateoas.extension.HalPagedResources
import no.rogfk.hateoas.extension.annotations.EnableHalExtension
import no.rogfk.hateoas.extension.annotations.HalResource
import no.rogfk.hateoas.extension.integration.common.TestDto
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.hateoas.Link
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@EnableHalExtension
@SpringBootApplication
@RestController
@RequestMapping(value = "/hal", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
class HalResController {

    @HalResource(pageSize = 5)
    @RequestMapping("/halpagedresources")
    HalPagedResources<TestDto> getHal() {
        return new HalPagedResources<>([TestDto.create()], 1)
    }

    @HalResource(pageSize = 5)
    @RequestMapping("/responseentity")
    ResponseEntity<HalPagedResources<TestDto>> getResponseEntity() {
        return ResponseEntity.ok(new HalPagedResources<>([TestDto.create()], 1))
    }

    @HalResource(pageSize = 5)
    @RequestMapping("/existing-link")
    HalPagedResources<TestDto> getExistingLink() {
        HalPagedResources<TestDto> halPagedResources = new HalPagedResources<>([TestDto.create()], 1)
        halPagedResources.add(new Link("http://test", Link.REL_PREVIOUS))
        return halPagedResources
    }

    @HalResource(pageSize = 5, allowAllQueryParams = true)
    @RequestMapping("/allqueryparams")
    HalPagedResources<TestDto> getAllQueryParams() {
        return new HalPagedResources<>([TestDto.create()], 1)
    }

    @HalResource(pageSize = 5, allowedQueryParams = "test1")
    @RequestMapping("/allowedqueryparams")
    HalPagedResources<TestDto> getAllowedQueryParams() {
        return new HalPagedResources<>([TestDto.create()], 1)
    }

    @HalResource(pageSize = 1)
    @RequestMapping("/pagesizeparam")
    HalPagedResources<TestDto> getPageSizeParam() {
        return new HalPagedResources<>([TestDto.create(), TestDto.create()], 1)
    }

    @HalResource(pageSize = 1, allowClientPageSize = false)
    @RequestMapping("/disabledpagesize")
    HalPagedResources<TestDto> getDisabledPageSize() {
        return new HalPagedResources<>([TestDto.create(), TestDto.create()], 1)
    }

    @HalResource(pageSize = 1, maxPageSize = 2)
    @RequestMapping("/maxpagesize")
    HalPagedResources<TestDto> getMaxPageSize() {
        return new HalPagedResources<>([TestDto.create(), TestDto.create()], 1)
    }
}
