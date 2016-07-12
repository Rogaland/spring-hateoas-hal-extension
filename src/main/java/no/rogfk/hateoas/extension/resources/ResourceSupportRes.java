package no.rogfk.hateoas.extension.resources;

import org.springframework.hateoas.Link;
import org.springframework.hateoas.ResourceSupport;

import java.util.List;

class ResourceSupportRes extends AbstractRes {
    private final ResourceSupport resourceSupport;

    ResourceSupportRes(ResourceSupport resourceSupport) {
        this.resourceSupport = resourceSupport;
    }

    @Override
    public Object execute() {
        if (!resourceSupport.hasLink(Link.REL_SELF)) {
            List<Link> navigation = aspectMetadata.getLinks();
            resourceSupport.add(navigation);
        }
        return resourceSupport;
    }
}
