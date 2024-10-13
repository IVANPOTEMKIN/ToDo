package ru.effective_mobile.todo.model;

import lombok.Builder;
import lombok.Data;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

@Data
@Builder
public class Todo {

    private final long id;
    private final LocalDate createdAt;
    private Title title;
    private String description;
    private Status status;
    private Importance importance;
    private Urgency urgency;
    private LocalDate deadline;
}