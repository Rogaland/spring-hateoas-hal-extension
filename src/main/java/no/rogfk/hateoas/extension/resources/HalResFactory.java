package no.rogfk.hateoas.extension.resources;

import no.rogfk.hateoas.extension.HalPagedResources;
import org.springframework.hateoas.ResourceSupport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;
import java.util.Optional;

public class HalResFactory {

    public Res create(Object response) {
        Optional<HttpStatus> responseEntity = Optional.empty();
        if (response instanceof ResponseEntity) {
            responseEntity = Optional.of(((ResponseEntity) response).getStatusCode());
            response = ((ResponseEntity) response).getBody();
        }

        final Res resource;
        if (response instanceof HalPagedResources) {
            HalPagedResources<?> halPagedResources = (HalPagedResources) response;
            resource = new HalRes(halPagedResources);
        } else if (response instanceof ResourceSupport) {
            ResourceSupport resourceSupport = (ResourceSupport) response;
            resource = new ResourceSupportRes(resourceSupport);
        } else if (response instanceof List) {
            List<?> values = (List) response;
            resource = new ListRes(values);
        } else {
            resource = new DtoRes(response);
        }

        if (responseEntity.isPresent()) {
            return new ResponseEntityRes(resource, responseEntity.get());
        } else {
            return resource;
        }
    }

}
