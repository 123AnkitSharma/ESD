package com.ankit.Yummy_Project.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;


public record ProductRequest (

    @NotBlank(message = "Product name cannot be empty")
    @JsonProperty("name")
    String name,

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be greater than zero")
    @JsonProperty("price")
    Double price
)
{}
