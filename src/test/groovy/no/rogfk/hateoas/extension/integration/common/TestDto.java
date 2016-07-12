package no.rogfk.hateoas.extension.integration.common;

import org.springframework.hateoas.ResourceSupport;

import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class TestDto extends ResourceSupport {
    private String text;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public static TestDto create() {
        TestDto testDto = new TestDto();
        testDto.setText("hello world");
        return testDto;
    }
}
