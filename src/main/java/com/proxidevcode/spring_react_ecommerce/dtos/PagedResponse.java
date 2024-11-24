package com.proxidevcode.spring_react_ecommerce.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class PagedResponse<T> {
    private long totalElements;
    private int totalPages;
    private int number;
    private int size;
    // private int numberOfElements;
    private boolean first;
    private boolean last;
    private List<T> content;

}
