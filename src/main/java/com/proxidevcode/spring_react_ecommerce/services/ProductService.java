package com.proxidevcode.spring_react_ecommerce.services;


import com.proxidevcode.spring_react_ecommerce.dtos.PagedResponse;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductRequest;
import com.proxidevcode.spring_react_ecommerce.dtos.ProductResponse;

public interface ProductService {
    
    PagedResponse<ProductResponse> getAllProducts (int page, int size);

    ProductResponse createProduct (ProductRequest dto);

    ProductResponse updateProduct (long id, ProductRequest dto);

    ProductResponse getProduct (long id);

    void deleteProduct (long id);

    PagedResponse<ProductResponse> getProductsByCategory (long id, int page, int size); 
}
