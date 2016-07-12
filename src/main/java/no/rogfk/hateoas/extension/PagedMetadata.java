package no.rogfk.hateoas.extension;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlAttribute;

public class PagedMetadata {

    @XmlAttribute
    @JsonProperty("page_size")
    private int pageSize;
    @XmlAttribute
    @JsonProperty("total_items")
    private long totalItems;
    @XmlAttribute
    @JsonProperty("page_count")
    private int pageCount;
    @XmlAttribute
    @JsonProperty("page")
    private int page;

    public PagedMetadata() {
        this.pageSize = 0;
        this.totalItems = 0;
        this.pageCount = 0;
        this.page = 0;
    }

    public int getPageSize() {
        return pageSize;
    }

    public long getTotalItems() {
        return totalItems;
    }

    public int getPageCount() {
        return pageCount;
    }

    public int getPage() {
        return page;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
        calculate();
    }

    public void setTotalItems(long totalItems) {
        this.totalItems = totalItems;
        calculate();
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public void setPage(int page) {
        this.page = page;
    }

    private void calculate() {
        this.pageCount = (int) Math.ceil((double) totalItems / (double) pageSize);
    }

    @Override
    public String toString() {
        return String.format("Metadata { page: %d, page count: %d, total items: %d, page size: %d }",
                page,
                pageCount,
                totalItems,
                pageSize
        );
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || !obj.getClass().equals(getClass())) {
            return false;
        }

        PagedMetadata that = (PagedMetadata) obj;

        return this.page == that.page
                && this.pageSize == that.pageSize
                && this.totalItems == that.totalItems
                && this.pageCount == that.pageCount;
    }

    @Override
    public int hashCode() {
        int result = pageSize;
        result = 31 * result + (int) (totalItems ^ (totalItems >>> 32));
        result = 31 * result + pageCount;
        result = 31 * result + page;
        return result;
    }
}


