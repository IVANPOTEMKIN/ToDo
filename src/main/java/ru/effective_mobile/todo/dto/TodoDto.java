package ru.effective_mobile.todo.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

@Schema(name = "Ответ", description = "Ответ с описанием задачи")
@Builder
public record TodoDto(@Schema(example = "1") long id,
                      @Schema(example = "STUDY") Title title,
                      @Schema(example = "Выполнить задачу") String description,
                      @Schema(example = "IN_PROGRESS") Status status,
                      @Schema(example = "IMPORTANT") Importance importance,
                      @Schema(example = "NON_URGENT") Urgency urgency,
                      @Schema(example = "2024-11-13") LocalDate deadline) {
}