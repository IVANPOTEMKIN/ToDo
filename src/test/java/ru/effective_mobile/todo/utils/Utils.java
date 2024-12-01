package ru.effective_mobile.todo.utils;

import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public class Utils {

    public static PaginatedResponse<Todo> findAll() {
        return new PaginatedResponse<>(
                List.of(Todo.builder()
                                .id(1L)
                                .createdAt(LocalDate.now())
                                .title(Title.STUDY)
                                .description("Description")
                                .status(Status.NEW)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusWeeks(1))
                                .build(),
                        Todo.builder()
                                .id(2L)
                                .createdAt(LocalDate.now())
                                .title(Title.OTHER)
                                .description("Description")
                                .status(Status.IN_PROGRESS)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(3))
                                .build(),
                        Todo.builder()
                                .id(3L)
                                .createdAt(LocalDate.now())
                                .title(Title.HOME)
                                .description("Description")
                                .status(Status.NEW)
                                .importance(Importance.UNIMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(10))
                                .build()),
                15, 1, 3);
    }

    public static PaginatedResponse<Todo> findAllWithoutFilters() {
        return new PaginatedResponse<>(
                List.of(Todo.builder()
                                .id(2L)
                                .createdAt(LocalDate.now())
                                .title(Title.OTHER)
                                .description("Description")
                                .status(Status.IN_PROGRESS)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(3))
                                .build(),
                        Todo.builder()
                                .id(7L)
                                .createdAt(LocalDate.now())
                                .title(Title.STUDY)
                                .description("Description")
                                .status(Status.COMPLETED)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.NON_URGENT)
                                .deadline(LocalDate.now().plusDays(4))
                                .build(),
                        Todo.builder()
                                .id(8L)
                                .createdAt(LocalDate.now())
                                .title(Title.STUDY)
                                .description("Description")
                                .status(Status.NEW)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.NON_URGENT)
                                .deadline(LocalDate.now().plusDays(4))
                                .build()),
                15, 1, 3);
    }

    public static PaginatedResponse<Todo> findAllByTitle() {
        return new PaginatedResponse<>(List.of(
                Todo.builder()
                        .id(7L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.COMPLETED)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build(),
                Todo.builder()
                        .id(8L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build(),
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()),
                4, 1, 3);
    }

    public static PaginatedResponse<Todo> findAllByTitleAndStatus() {
        return new PaginatedResponse<>(List.of(
                Todo.builder()
                        .id(8L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build(),
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()),
                2, 1, 3);
    }

    public static PaginatedResponse<Todo> findAllByTitleAndStatusAndImportanceAndUrgency() {
        return new PaginatedResponse<>(List.of(
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()),
                1, 1, 3);
    }

    public static PaginatedResponse<Todo> findAllByTitleAndStatusAndImportanceAndUrgencyAndDeadline() {
        return new PaginatedResponse<>(List.of(), 0, 1, 3);
    }

    public static Optional<Todo> findById() {
        return Optional.ofNullable(Todo.builder()
                .id(10L)
                .createdAt(LocalDate.now())
                .title(Title.FRIENDS)
                .description("Description")
                .status(Status.NEW)
                .importance(Importance.UNIMPORTANT)
                .urgency(Urgency.NON_URGENT)
                .deadline(LocalDate.now().plusDays(10))
                .build());
    }

    public static Todo getSavedTodo() {
        return Todo.builder()
                .id(16L)
                .createdAt(LocalDate.now())
                .title(Title.OTHER)
                .description("Description")
                .status(Status.NEW)
                .importance(Importance.UNIMPORTANT)
                .urgency(Urgency.NON_URGENT)
                .deadline(LocalDate.now().plusWeeks(1))
                .build();
    }

    public static Todo getOldTodo() {
        return Todo.builder()
                .id(10L)
                .createdAt(LocalDate.now())
                .title(Title.FRIENDS)
                .description("Description")
                .status(Status.NEW)
                .importance(Importance.UNIMPORTANT)
                .urgency(Urgency.NON_URGENT)
                .deadline(LocalDate.now().plusDays(10))
                .build();
    }

    public static Todo getUpdatedTodo() {
        return Todo.builder()
                .id(10L)
                .createdAt(LocalDate.now())
                .title(Title.HOME)
                .description("Updated description")
                .status(Status.IN_PROGRESS)
                .importance(Importance.IMPORTANT)
                .urgency(Urgency.URGENT)
                .deadline(LocalDate.now().plusWeeks(2))
                .build();
    }

    public static PaginatedResponse<TodoDto> getAll() {
        return new PaginatedResponse<>(
                List.of(TodoDto.builder()
                                .id(1L)
                                .title(Title.STUDY)
                                .description("Description")
                                .status(Status.NEW)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusWeeks(1))
                                .build(),
                        TodoDto.builder()
                                .id(2L)
                                .title(Title.OTHER)
                                .description("Description")
                                .status(Status.IN_PROGRESS)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(3))
                                .build(),
                        TodoDto.builder()
                                .id(3L)
                                .title(Title.HOME)
                                .description("Description")
                                .status(Status.NEW)
                                .importance(Importance.UNIMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(10))
                                .build()),
                15, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllWithoutFilters() {
        return new PaginatedResponse<>(
                List.of(TodoDto.builder()
                                .id(2L)
                                .title(Title.OTHER)
                                .description("Description")
                                .status(Status.IN_PROGRESS)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(3))
                                .build(),
                        TodoDto.builder()
                                .id(7L)
                                .title(Title.STUDY)
                                .description("Description")
                                .status(Status.COMPLETED)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.NON_URGENT)
                                .deadline(LocalDate.now().plusDays(4))
                                .build(),
                        TodoDto.builder()
                                .id(8L)
                                .title(Title.STUDY)
                                .description("Description")
                                .status(Status.NEW)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.NON_URGENT)
                                .deadline(LocalDate.now().plusDays(4))
                                .build()),
                15, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitle() {
        return new PaginatedResponse<>(List.of(
                TodoDto.builder()
                        .id(7L)
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.COMPLETED)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build(),
                TodoDto.builder()
                        .id(8L)
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build(),
                TodoDto.builder()
                        .id(1L)
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()),
                4, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitleAndStatus() {
        return new PaginatedResponse<>(List.of(
                TodoDto.builder()
                        .id(8L)
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build(),
                TodoDto.builder()
                        .id(1L)
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()),
                2, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitleAndStatusAndImportanceAndUrgency() {
        return new PaginatedResponse<>(List.of(
                TodoDto.builder()
                        .id(1L)
                        .title(Title.STUDY)
                        .description("Description")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()),
                1, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitleAndStatusAndImportanceAndUrgencyAndDeadline() {
        return new PaginatedResponse<>(List.of(), 0, 1, 3);
    }

    public static TodoDto getById() {
        return TodoDto.builder()
                .id(10)
                .title(Title.FRIENDS)
                .description("Description")
                .status(Status.NEW)
                .importance(Importance.UNIMPORTANT)
                .urgency(Urgency.NON_URGENT)
                .deadline(LocalDate.now().plusDays(10))
                .build();
    }
}