package com.proxidevcode.spring_react_ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderProductResponse {
    private Long id;
    private Long productId;
    private String productName;
    private Integer quantity;
    private Double price;
}
