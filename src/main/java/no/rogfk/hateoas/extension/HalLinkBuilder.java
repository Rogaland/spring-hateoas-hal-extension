package no.rogfk.hateoas.extension;

import org.springframework.hateoas.Link;
import org.springframework.web.util.UriComponentsBuilder;

class HalLinkBuilder {
    private final UriComponentsBuilder baseComponent;
    private final int page;
    private final int pageCount;
    private final String queryString;

    HalLinkBuilder(UriComponentsBuilder baseComponent, int page, int pageCount) {
        this(baseComponent, page, pageCount, null);
    }

    HalLinkBuilder(UriComponentsBuilder baseComponent, int page, int pageCount, String queryString) {
        this.baseComponent = baseComponent;
        this.page = page;
        this.pageCount = pageCount;
        if (queryString == null) {
            this.queryString = null;
        } else {
            this.queryString = removePageParam(queryString);
        }
    }

    Link self() {
        return new Link(getHref(page), Link.REL_SELF);
    }

    Link first() {
        return new Link(getHref(), Link.REL_FIRST);
    }

    Link next() {
        int nextPage = (page == pageCount) ? pageCount : page + 1;
        return new Link(getHref(nextPage), Link.REL_NEXT);
    }

    Link previous() {
        int prevPage = (page == 1) ? page : page - 1;
        return new Link(getHref(prevPage), Link.REL_PREVIOUS);
    }

    Link last() {
        return new Link(getHref(pageCount), Link.REL_LAST);
    }

    private String getHref() {
        UriComponentsBuilder localBuilder = baseComponent.cloneBuilder();
        if (queryString == null) {
            return localBuilder.build().toString();
        } else {
            return localBuilder.query(queryString).build().toString();
        }
    }

    private String getHref(int pageParameter) {
        UriComponentsBuilder localBuilder = baseComponent.cloneBuilder();
        if (queryString == null) {
            return localBuilder.queryParam(HalResourceMetadata.PARAM_PAGE, pageParameter).build().toString();
        } else {
            return localBuilder.query(queryString).queryParam(HalResourceMetadata.PARAM_PAGE, pageParameter).build().toString();
        }
    }

    String removePageParam(String queryString) {
        return queryString.replaceAll("(?i)page=\\d+&", "");
    }
}
