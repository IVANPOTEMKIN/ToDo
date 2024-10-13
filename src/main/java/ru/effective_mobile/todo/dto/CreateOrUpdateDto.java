package ru.effective_mobile.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(name = "Запрос", description = "Запрос на создание или изменение задачи")
public record CreateOrUpdateDto(@Schema(example = "Выполнить задачу")
                                @NotBlank(message = "Описание не может быть пустым")
                                @Size(max = 255, message = "Описание не может превышать 255 символов") String description) {
}