package no.rogfk.hateoas.extension.integration.pagedresources

import no.rogfk.hateoas.extension.HalPagedResources
import no.rogfk.hateoas.extension.annotations.EnableHalExtension
import no.rogfk.hateoas.extension.annotations.HalResource
import no.rogfk.hateoas.extension.integration.common.TestDto
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod
import org.springframework.web.bind.annotation.RestController

@EnableHalExtension
@SpringBootApplication
@RestController
@RequestMapping(value = "/pagedresources", method = RequestMethod.GET)
class PagedResourcesController {

    @HalResource(pageSize = 5)
    @RequestMapping
    HalPagedResources<TestDto> getList() {
        def values = []
        (1..50).each { i ->
            values << new TestDto(text: "hello world")
        }

        return new HalPagedResources<TestDto>(values, 1)
    }
}
