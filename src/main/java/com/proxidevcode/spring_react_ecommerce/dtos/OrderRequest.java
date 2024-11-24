package com.proxidevcode.spring_react_ecommerce.dtos;

import java.util.Date;
import java.util.Set;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class OrderRequest {
    @NotNull (message = "First name is required")
    private String lastName;
    @NotNull (message = "Last name is required")
    private String firstName;
    @Email (message = "Invalid email")
    @NotNull (message = "Email is required")
    private String email;
    @NotNull (message = "Address is required")
    private String address;
    @NotNull (message = "Date is required")
    private Date date;
    @NotEmpty (message = "Order must constain at least one product")
    private Set<OrderProductRequest> orderProducts;  
}
