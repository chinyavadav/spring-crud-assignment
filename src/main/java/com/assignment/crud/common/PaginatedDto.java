package com.assignment.crud.common;

import java.util.List;

public class PaginatedDto<T> {
    List<T> data;
    PaginationLink links;

    public PaginatedDto(List<T> data, PaginationLink links) {
        this.data = data;
        this.links = links;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> objects) {
        this.data = data;
    }

    public PaginationLink getLinks() {
        return links;
    }

    public void setLinks(PaginationLink links) {
        this.links = links;
    }
}

