package no.rogfk.hateoas.extension.integration.common

import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity

import javax.xml.bind.JAXBContext

class XmlAsserts {

    static boolean validHalXml(ResponseEntity<String> responseEntity) {
        if (validHttpResponse(responseEntity) && validXml(responseEntity)) {
            def unmarshaller = JAXBContext.newInstance(TestDto).createUnmarshaller()
            TestDto testDto = (TestDto) unmarshaller.unmarshal(new StringReader(responseEntity.body))
            def link = testDto.getLink(Link.REL_SELF)
            return (link != null)
        } else {
            return false;
        }
    }

    private static boolean validHttpResponse(ResponseEntity<String> responseEntity) {
        return responseEntity.statusCode == HttpStatus.OK
    }

    private static boolean validXml(ResponseEntity<String> responseEntity) {
        return responseEntity.headers.getContentType().includes(MediaType.APPLICATION_XML)
    }

}
