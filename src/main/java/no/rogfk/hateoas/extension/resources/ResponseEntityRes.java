package no.rogfk.hateoas.extension.resources;


import no.rogfk.hateoas.extension.HalResourceMetadata;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

class ResponseEntityRes implements Res {
    private final Res resource;
    private final HttpStatus status;

    ResponseEntityRes(Res resource, HttpStatus status) {
        this.resource = resource;
        this.status = status;
    }

    @Override
    public void setAspectMetadata(HalResourceMetadata aspectMetadata) {
        resource.setAspectMetadata(aspectMetadata);
    }

    @Override
    public Object execute() {
        Object response = resource.execute();
        return new ResponseEntity<>(response, status);
    }
}
