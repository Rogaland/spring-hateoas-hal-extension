package no.rogfk.hateoas.extension;

import com.fasterxml.jackson.annotation.JsonUnwrapped;
import org.springframework.hateoas.Resources;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class HalPagedResources<T> extends Resources {
    private final PagedMetadata pagedMetadata;
    private final Collection<T> content;

    public HalPagedResources(Collection<T> content, Integer page) {
        this.content = content;
        this.pagedMetadata = new PagedMetadata();
        this.pagedMetadata.setPage(page == null ? 1 : page);
        this.pagedMetadata.setTotalItems(content.size());
    }

    @JsonUnwrapped
    public PagedMetadata getPagedMetadata() {
        return pagedMetadata;
    }

    @Override
    public Collection<T> getContent() {
        long page = pagedMetadata.getPage();
        int pageSize = pagedMetadata.getPageSize();

        long fromIndex = (page - 1) * pageSize;
        long toIndex = page * pageSize;
        if (toIndex > content.size())
            toIndex = content.size();

        List<T> returnContent = new ArrayList<>(content);
        return returnContent.subList((int) fromIndex, (int) toIndex);
    }
}
