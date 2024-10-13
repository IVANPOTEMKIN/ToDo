package ru.effective_mobile.todo.model;

import lombok.Data;

import java.util.List;

@Data
public class PaginatedResponse<T> {

    private final List<T> items;
    private final int totalElements;
    private final int totalPages;
    private final int currentPage;
    private final int pageSize;

    public PaginatedResponse(List<T> items, int totalElements, int currentPage, int pageSize) {
        this.items = items;
        this.totalElements = totalElements;
        this.totalPages = (int) Math.ceil((double) totalElements / pageSize);
        this.currentPage = currentPage;
        this.pageSize = pageSize;
    }
}