package com.ssafy.campcino.dto.responseDto;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
@Getter
@Setter
public class PaginatedResponse<T> {
	private List<T> items;
    private int currentPage;
    private int pageSize;
    private long totalItems;
    private int totalPages;

    // Constructors
    public PaginatedResponse() {}

    public PaginatedResponse(List<T> items, int currentPage, int pageSize, long totalItems, int totalPages) {
        this.items = items;
        this.currentPage = currentPage;
        this.pageSize = pageSize;
        this.totalItems = totalItems;
        this.totalPages = totalPages;
    }
}
