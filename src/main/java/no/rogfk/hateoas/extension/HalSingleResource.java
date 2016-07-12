package no.rogfk.hateoas.extension;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.ResourceSupport;

public class HalSingleResource extends ResourceSupport {
    private final Object content;

    public HalSingleResource(Object content) {
        this.content = content;
    }

    @JsonUnwrapped
    public Object getContent() {
        return content;
    }
}
