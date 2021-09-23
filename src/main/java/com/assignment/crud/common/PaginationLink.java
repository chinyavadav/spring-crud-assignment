package com.assignment.crud.common;

public class PaginationLink {
    private long totalPages;
    private long totalObjects;
    private long currentPage;

    public PaginationLink(long totalPages, long totalObjects, long currentPage) {
        this.totalPages = totalPages;
        this.totalObjects = totalObjects;
        this.currentPage = currentPage;
    }

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public long getTotalObjects() {
        return totalObjects;
    }

    public void setTotalObjects(long totalObjects) {
        this.totalObjects = totalObjects;
    }

    public long getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(long currentPage) {
        this.currentPage = currentPage;
    }
}
