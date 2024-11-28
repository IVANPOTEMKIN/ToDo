package ru.effective_mobile.todo.utils;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
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

public class UtilsForUnitTests {

    public static Page<Todo> findAll() {
        return new PageImpl<>(List.of(
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build(),
                Todo.builder()
                        .id(2L)
                        .createdAt(LocalDate.now())
                        .title(Title.OTHER)
                        .description("Выполнить задачу")
                        .status(Status.IN_PROGRESS)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusDays(3))
                        .build(),
                Todo.builder()
                        .id(3L)
                        .createdAt(LocalDate.now())
                        .title(Title.HOME)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.UNIMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusDays(10))
                        .build()));
    }

    public static Page<Todo> findAllByTitle() {
        return new PageImpl<>(List.of(
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build(),
                Todo.builder()
                        .id(4L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.IN_PROGRESS)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusDays(12))
                        .build(),
                Todo.builder()
                        .id(7L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.COMPLETED)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build()));
    }

    public static Page<Todo> findAllByTitleAndStatus() {
        return new PageImpl<>(List.of(
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build(),
                Todo.builder()
                        .id(8L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build()));
    }

    public static Page<Todo> findAllByTitleAndStatusAndImportanceAndUrgency() {
        return new PageImpl<>(List.of(
                Todo.builder()
                        .id(1L)
                        .createdAt(LocalDate.now())
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build()));
    }

    public static Page<Todo> findAllByTitleAndStatusAndImportanceAndUrgencyAndDeadline() {
        return new PageImpl<>(List.of());
    }

    public static Optional<Todo> findById() {
        return Optional.of(
                Todo.builder()
                        .id(10L)
                        .createdAt(LocalDate.now())
                        .title(Title.FRIENDS)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.UNIMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(10))
                        .build());
    }

    public static PaginatedResponse<TodoDto> getAll() {
        return new PaginatedResponse<>(
                List.of(TodoDto.builder()
                                .id(1L)
                                .title(Title.STUDY)
                                .description("Выполнить задачу")
                                .status(Status.NEW)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusWeeks(1))
                                .build(),
                        TodoDto.builder()
                                .id(2L)
                                .title(Title.OTHER)
                                .description("Выполнить задачу")
                                .status(Status.IN_PROGRESS)
                                .importance(Importance.IMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(3))
                                .build(),
                        TodoDto.builder()
                                .id(3L)
                                .title(Title.HOME)
                                .description("Выполнить задачу")
                                .status(Status.NEW)
                                .importance(Importance.UNIMPORTANT)
                                .urgency(Urgency.URGENT)
                                .deadline(LocalDate.now().plusDays(10))
                                .build()),
                3, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitle() {
        return new PaginatedResponse<>(List.of(
                TodoDto.builder()
                        .id(1L)
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build(),
                TodoDto.builder()
                        .id(4L)
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.IN_PROGRESS)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusDays(12))
                        .build(),
                TodoDto.builder()
                        .id(7L)
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.COMPLETED)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build()),
                3, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitleAndStatus() {
        return new PaginatedResponse<>(List.of(
                TodoDto.builder()
                        .id(1L)
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.URGENT)
                        .deadline(LocalDate.now().plusWeeks(1))
                        .build(),
                TodoDto.builder()
                        .id(8L)
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
                        .status(Status.NEW)
                        .importance(Importance.IMPORTANT)
                        .urgency(Urgency.NON_URGENT)
                        .deadline(LocalDate.now().plusDays(4))
                        .build()),
                2, 1, 3);
    }

    public static PaginatedResponse<TodoDto> getAllByTitleAndStatusAndImportanceAndUrgency() {
        return new PaginatedResponse<>(List.of(
                TodoDto.builder()
                        .id(1L)
                        .title(Title.STUDY)
                        .description("Выполнить задачу")
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
                .description("Выполнить задачу")
                .status(Status.NEW)
                .importance(Importance.UNIMPORTANT)
                .urgency(Urgency.NON_URGENT)
                .deadline(LocalDate.now().plusDays(10))
                .build();
    }
}