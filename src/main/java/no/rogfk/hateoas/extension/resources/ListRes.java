package no.rogfk.hateoas.extension.resources;

import no.rogfk.hateoas.extension.HalPagedResources;
import no.rogfk.hateoas.extension.PagedMetadata;
import org.springframework.hateoas.Link;

import java.util.List;

class ListRes extends AbstractRes {
    private final List<?> values;

    ListRes(List<?> values) {
        this.values = values;
    }

    @SuppressWarnings("unchecked")
    @Override
    public Object execute() {
        HalPagedResources halPagedResources = new HalPagedResources<>(values, aspectMetadata.getPageParam());
        if (values.size() > 0) {
            PagedMetadata pagedMetadata = halPagedResources.getPagedMetadata();
            pagedMetadata.setPageSize(aspectMetadata.pageSize());

            List<Link> links = aspectMetadata.getLinks(pagedMetadata.getPageCount());
            halPagedResources.add(links);
        }
        return halPagedResources;
    }
}
