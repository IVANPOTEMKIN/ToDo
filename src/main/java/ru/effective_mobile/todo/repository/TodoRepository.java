package ru.effective_mobile.todo.repository;

import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;
import java.util.Optional;

public interface TodoRepository {

    PaginatedResponse<Todo> findAll(int page, int size);

    PaginatedResponse<Todo> findAllByFilters(Title title,
                                             Status status,
                                             Importance importance,
                                             Urgency urgency,
                                             LocalDate deadline,
                                             int page, int size);

    Optional<Todo> findById(long id);

    void save(Todo todo);

    void update(Todo todo);

    void delete(Todo todo);

    void deleteAll();

    void deleteAllByFilters(Title title,
                            Status status);
}