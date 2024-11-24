package com.proxidevcode.spring_react_ecommerce.dtos;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Set;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@Data @NoArgsConstructor @AllArgsConstructor @Builder @JsonInclude(JsonInclude.Include.NON_EMPTY)
public class ErrorResponseApi {
    private String message;
    private Integer errorCode;
    private String error;
    private String ErrorDescription;
    private Set<String> validationErrors;
    private Map<String, String> errors;
    private LocalDateTime timestamp;
}
