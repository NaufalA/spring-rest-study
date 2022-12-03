package com.enigmacamp.restapiintro.shared.classes;

import org.springframework.data.domain.Page;

public class PagedResponse<T> {
    private Integer page;
    private Integer size;
    private Integer fetchedSize;
    private Long totalSize;
    private Integer totalPage;
    private Iterable<T> content;

    public PagedResponse() {
    }

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

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getFetchedSize() {
        return fetchedSize;
    }

    public void setFetchedSize(Integer fetchedSize) {
        this.fetchedSize = fetchedSize;
    }

    public Long getTotalSize() {
        return totalSize;
    }

    public void setTotalSize(Long totalSize) {
        this.totalSize = totalSize;
    }

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public Iterable<T> getContent() {
        return content;
    }

    public void setContent(Iterable<T> content) {
        this.content = content;
    }
}
