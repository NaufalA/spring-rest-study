package com.enigmacamp.restapiintro.shared.classes;

import org.springframework.data.domain.Page;

import java.io.Serializable;

public class PagedResponse<T> {
    private Integer page;
    private Integer size;
    private Integer fetchedSize;
    private Long totalSize;
    private Integer totalPage;
    private Iterable<T> content;

    public PagedResponse(Page<T> pagedResult) {
        this.page = pagedResult.getNumber();
        this.size = pagedResult.getSize();
        this.fetchedSize = pagedResult.getNumberOfElements();
        this.totalSize = pagedResult.getTotalElements();
        this.totalPage = pagedResult.getTotalPages();
        this.content = pagedResult.getContent();
    }

    public Integer getPage() {
        return page;
    }

    public Integer getSize() {
        return size;
    }

    public Integer getFetchedSize() {
        return fetchedSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public Iterable<T> getContent() {
        return content;
    }
}
