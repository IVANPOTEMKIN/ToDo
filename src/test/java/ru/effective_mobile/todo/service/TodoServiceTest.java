package ru.effective_mobile.todo.service;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.exception.TodoNotFoundException;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;
import ru.effective_mobile.todo.repository.TodoRepository;
import ru.effective_mobile.todo.service.impl.TodoServiceImpl;
import ru.effective_mobile.todo.specification.TodoSpecification;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static ru.effective_mobile.todo.utils.UtilsForUnitTests.*;

@ExtendWith(MockitoExtension.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    @DisplayName("Получение всех задач")
    void test_getAll() {
        var expected = getAll();

        when(todoRepository.findAll(any(Pageable.class)))
                .thenReturn(findAll());

        var actual = todoService.getAll(1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(any(Pageable.class));
    }

    @Test
    @DisplayName("Получение всех задач без фильтров")
    void test_getAllByFilters_withoutFilters() {
        var expected = getAll();

        when(todoRepository.findAll(any(TodoSpecification.class), any(Pageable.class)))
                .thenReturn(findAll());

        var actual = todoService.getAllByFilters(
                null,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(any(TodoSpecification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение всех задач по title")
    void test_getAllByFilters_withTitle() {
        var expected = getAllByTitle();

        when(todoRepository.findAll(any(TodoSpecification.class), any(Pageable.class)))
                .thenReturn(findAllByTitle());

        var actual = todoService.getAllByFilters(
                Title.STUDY,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(any(TodoSpecification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение всех задач по title, status")
    void test_getAllByFilters_withTitleAndStatus() {
        var expected = getAllByTitleAndStatus();

        when(todoRepository.findAll(any(TodoSpecification.class), any(Pageable.class)))
                .thenReturn(findAllByTitleAndStatus());

        var actual = todoService.getAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(any(TodoSpecification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение всех задач по title, status, importance, urgency")
    void test_getAllByFilters_withTitleAndStatusAndImportanceAndUrgency() {
        var expected = getAllByTitleAndStatusAndImportanceAndUrgency();

        when(todoRepository.findAll(any(TodoSpecification.class), any(Pageable.class)))
                .thenReturn(findAllByTitleAndStatusAndImportanceAndUrgency());

        var actual = todoService.getAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(any(TodoSpecification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение всех задач по title, status, importance, urgency, deadline")
    void test_getAllByFilters_withTitleAndStatusAndImportanceAndUrgencyAndDeadline() {
        var expected = getAllByTitleAndStatusAndImportanceAndUrgencyAndDeadline();

        when(todoRepository.findAll(any(TodoSpecification.class), any(Pageable.class)))
                .thenReturn(findAllByTitleAndStatusAndImportanceAndUrgencyAndDeadline());

        var actual = todoService.getAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                LocalDate.now().plusDays(3),
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(any(TodoSpecification.class), any(Pageable.class));
    }

    @Test
    @DisplayName("Получение задачи по её ID - успешно")
    void test_getById_success() {
        var expected = getById();

        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        var actual = todoService.getById(anyLong());

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @DisplayName("Получение задачи по её ID - ошибка")
    void test_getById_exception() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.getById(anyLong()));

        verify(todoRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @DisplayName("Создание новой задачи")
    void test_create() {
        var newTodo = new CreateOrUpdateDto("Test description");

        todoService.create(
                newTodo,
                null,
                null,
                null,
                null,
                null);

        verify(todoRepository, times(1))
                .save(any(Todo.class));
    }

    @Test
    @DisplayName("Изменение описания задачи по её ID - успешно")
    void test_updateDescription_success() {
        var newTodo = new CreateOrUpdateDto("Test description");

        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        todoService.updateDescription(1, newTodo);

        verify(todoRepository, times(1))
                .save(any(Todo.class));
    }

    @Test
    @DisplayName("Изменение описания задачи по её ID - ошибка")
    void test_updateDescription_exception() {
        var newTodo = new CreateOrUpdateDto("Test description");

        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateDescription(1, newTodo));

        verify(todoRepository, times(0))
                .save(any(Todo.class));
    }

    @Test
    @DisplayName("Изменение параметров задачи по её ID - успешно")
    void test_updateFilters_success() {
        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        todoService.updateFilters(
                10,
                Title.HOME,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                LocalDate.now().plusDays(5));

        verify(todoRepository, times(1))
                .save(any(Todo.class));
    }

    @Test
    @DisplayName("Изменение параметров задачи по её ID - ошибка")
    void test_updateFilters_exception() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateFilters(
                        10,
                        Title.HOME,
                        Status.IN_PROGRESS,
                        Importance.IMPORTANT,
                        Urgency.URGENT,
                        LocalDate.now().plusDays(5)));

        verify(todoRepository, times(0))
                .save(any(Todo.class));
    }

    @Test
    @DisplayName("Удаление задачи по её ID - успешно")
    void test_delete_success() {
        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        todoService.deleteById(1);

        verify(todoRepository, times(1))
                .delete(any(Todo.class));
    }

    @Test
    @DisplayName("Удаление задачи по её ID - ошибка")
    void test_delete_exception() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.deleteById(1));

        verify(todoRepository, times(0))
                .delete(any(Todo.class));
    }

    @Test
    @DisplayName("Удаление всех задач")
    void test_deleteAll() {
        todoService.deleteAll();

        verify(todoRepository, times(1))
                .deleteAll();
    }

    @Test
    @DisplayName("Удаление всех задач без фильтров")
    void test_deleteAllByFilters_withoutFilters() {
        todoService.deleteAllByFilters(null, null);

        verify(todoRepository, times(1))
                .delete(any(TodoSpecification.class));
    }

    @Test
    @DisplayName("Удаление всех задач по title")
    void test_deleteAllByFilters_withTitle() {
        todoService.deleteAllByFilters(Title.OTHER, null);

        verify(todoRepository, times(1))
                .delete(any(TodoSpecification.class));
    }

    @Test
    @DisplayName("Удаление всех задач по status")
    void test_deleteAllByFilters_withStatus() {
        todoService.deleteAllByFilters(null, Status.COMPLETED);

        verify(todoRepository, times(1))
                .delete(any(TodoSpecification.class));
    }

    @Test
    @DisplayName("Удаление всех задач по title, status")
    void test_deleteAllByFilters_withTitleAndStatus() {
        todoService.deleteAllByFilters(Title.OTHER, Status.COMPLETED);

        verify(todoRepository, times(1))
                .delete(any(TodoSpecification.class));
    }
}