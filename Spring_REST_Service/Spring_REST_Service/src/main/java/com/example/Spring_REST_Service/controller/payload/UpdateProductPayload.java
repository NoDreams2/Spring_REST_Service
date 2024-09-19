package com.example.Spring_REST_Service.controller.payload;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record UpdateProductPayload(
        @NotNull(message = "Название товара не может быть пустым")
        @Size(min = 3, max = 50, message = "Название товара должно содержать от {min} до {max} символов")
        String title,
        @NotNull(message = "Описание товара не может быть пустым")
        @Size(min = 10, max = 1000, message = "Описание товара должно содержать от {min} до {max} символов")
        String details) {
}
