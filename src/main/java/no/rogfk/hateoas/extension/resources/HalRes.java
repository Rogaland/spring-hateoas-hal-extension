package no.rogfk.hateoas.extension.resources;


import no.rogfk.hateoas.extension.HalPagedResources;
import no.rogfk.hateoas.extension.PagedMetadata;
import org.springframework.hateoas.Link;

import java.util.List;
import java.util.stream.Collectors;

class HalRes extends AbstractRes {
    private final HalPagedResources<?> halPagedResources;

    HalRes(HalPagedResources<?> halPagedResources) {
        this.halPagedResources = halPagedResources;
    }

    @Override
    public Object execute() {
        PagedMetadata pagedMetadata = halPagedResources.getPagedMetadata();
        pagedMetadata.setPageSize(aspectMetadata.pageSize());

        List<Link> links = aspectMetadata.getLinks(pagedMetadata.getPage(), pagedMetadata.getPageCount());
        List<Link> linksToAdd = links.stream().filter(link -> {
            Link existingLink = halPagedResources.getLink(link.getRel());
            return (existingLink == null);
        }).collect(Collectors.toList());
        halPagedResources.add(linksToAdd);

        return halPagedResources;
    }

}
