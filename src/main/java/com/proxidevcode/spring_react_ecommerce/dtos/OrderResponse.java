package com.proxidevcode.spring_react_ecommerce.dtos;

import java.util.Date;
import java.util.Set;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderResponse {
    private String id;
    private String lastName;
    private String firstName;
    private String email;
    private Date date;
    private String address;
    private Set<OrderProductResponse> orderProducts;    
}
