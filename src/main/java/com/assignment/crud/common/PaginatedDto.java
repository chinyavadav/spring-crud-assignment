package com.assignment.crud.common;

import java.util.List;

public class PaginatedDto<T> {
    List<T> objects;
    PaginationLink links;

    public PaginatedDto(List<T> objects, PaginationLink links) {
        this.objects = objects;
        this.links = links;
    }

    public List<T> getObjects() {
        return objects;
    }

    public void setObjects(List<T> objects) {
        this.objects = objects;
    }

    public PaginationLink getLinks() {
        return links;
    }

    public void setLinks(PaginationLink links) {
        this.links = links;
    }
}

