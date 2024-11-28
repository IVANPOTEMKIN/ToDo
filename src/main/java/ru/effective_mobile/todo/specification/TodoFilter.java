package ru.effective_mobile.todo.specification;

import lombok.Builder;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

@Builder
public record TodoFilter(Title title,
                         Status status,
                         Importance importance,
                         Urgency urgency,
                         LocalDate deadline) {
}