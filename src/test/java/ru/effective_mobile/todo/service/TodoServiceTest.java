package ru.effective_mobile.todo.service;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.exception.TodoNotFoundException;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;
import ru.effective_mobile.todo.repository.TodoRepository;
import ru.effective_mobile.todo.service.impl.TodoServiceImpl;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static ru.effective_mobile.todo.Utils.*;

@ExtendWith(MockitoExtension.class)
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class TodoServiceTest {

    @Mock
    private TodoRepository todoRepository;

    @InjectMocks
    private TodoServiceImpl todoService;

    @Test
    @Order(1)
    void testGetAll() {
        PaginatedResponse<TodoDto> expected = getAll();

        when(todoRepository.findAll(1, 3))
                .thenReturn(findAll());

        PaginatedResponse<TodoDto> actual = todoService.getAll(1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAll(1, 3);
    }

    @Test
    @Order(2)
    void testGetAllByFilters_option1() {
        PaginatedResponse<TodoDto> expected = getAllByFiltersOption1();

        when(todoRepository.findAllByFilters(
                null,
                null,
                null,
                null,
                null,
                1, 3))
                .thenReturn(findAllByFiltersOption1());

        PaginatedResponse<TodoDto> actual = todoService.getAllByFilters(
                null,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAllByFilters(
                        null,
                        null,
                        null,
                        null,
                        null,
                        1, 3);
    }

    @Test
    @Order(3)
    void testGetAllByFilters_option2() {
        PaginatedResponse<TodoDto> expected = getAllByFiltersOption2();

        when(todoRepository.findAllByFilters(
                Title.STUDY,
                null,
                null,
                null,
                null,
                1, 3))
                .thenReturn(findAllByFiltersOption2());

        PaginatedResponse<TodoDto> actual = todoService.getAllByFilters(
                Title.STUDY,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAllByFilters(
                        Title.STUDY,
                        null,
                        null,
                        null,
                        null,
                        1, 3);
    }

    @Test
    @Order(4)
    void testGetAllByFilters_option3() {
        PaginatedResponse<TodoDto> expected = getAllByFiltersOption3();

        when(todoRepository.findAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                null,
                null,
                null,
                1, 3))
                .thenReturn(findAllByFiltersOption3());

        PaginatedResponse<TodoDto> actual = todoService.getAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAllByFilters(
                        Title.STUDY,
                        Status.IN_PROGRESS,
                        null,
                        null,
                        null,
                        1, 3);
    }

    @Test
    @Order(5)
    void testGetAllByFilters_option4() {
        PaginatedResponse<TodoDto> expected = getAllByFiltersOption4();

        when(todoRepository.findAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                null,
                1, 3))
                .thenReturn(findAllByFiltersOption4());

        PaginatedResponse<TodoDto> actual = todoService.getAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                null,
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAllByFilters(
                        Title.STUDY,
                        Status.IN_PROGRESS,
                        Importance.IMPORTANT,
                        Urgency.URGENT,
                        null,
                        1, 3);
    }

    @Test
    @Order(6)
    void testGetAllByFilters_option5() {
        PaginatedResponse<TodoDto> expected = getAllByFiltersOption5();

        when(todoRepository.findAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                LocalDate.now().plusDays(3),
                1, 3))
                .thenReturn(findAllByFiltersOption5());

        PaginatedResponse<TodoDto> actual = todoService.getAllByFilters(
                Title.STUDY,
                Status.IN_PROGRESS,
                Importance.IMPORTANT,
                Urgency.URGENT,
                LocalDate.now().plusDays(3),
                1, 3);

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findAllByFilters(
                        Title.STUDY,
                        Status.IN_PROGRESS,
                        Importance.IMPORTANT,
                        Urgency.URGENT,
                        LocalDate.now().plusDays(3),
                        1, 3);
    }

    @Test
    @Order(7)
    void testGetById_success() {
        TodoDto expected = getById();

        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        TodoDto actual = todoService.getById(anyLong());

        assertEquals(expected, actual);

        verify(todoRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(8)
    void testGetById_exception() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.getById(anyLong()));

        verify(todoRepository, times(1))
                .findById(anyLong());
    }

    @Test
    @Order(9)
    void testCreate() {
        CreateOrUpdateDto newTodo = new CreateOrUpdateDto("Test description");

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
    @Order(10)
    void testUpdateDescription_success() {
        CreateOrUpdateDto newTodo = new CreateOrUpdateDto("Test description");

        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        todoService.updateDescription(10, newTodo);

        verify(todoRepository, times(1))
                .update(any(Todo.class));
    }

    @Test
    @Order(11)
    void testUpdateDescription_exception() {
        CreateOrUpdateDto newTodo = new CreateOrUpdateDto("Test description");

        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateDescription(10, newTodo));

        verify(todoRepository, times(0))
                .update(any(Todo.class));
    }

    @Test
    @Order(12)
    void testUpdateFilters() {
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
                .update(any(Todo.class));
    }

    @Test
    @Order(13)
    void testUpdateFilters_exception() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.updateFilters(
                        10,
                        Title.HOME,
                        Status.IN_PROGRESS,
                        Importance.IMPORTANT,
                        Urgency.URGENT,
                        LocalDate.now().plusDays(5)));

        verify(todoRepository, times(0))
                .update(any(Todo.class));
    }

    @Test
    @Order(14)
    void testDelete() {
        when(todoRepository.findById(anyLong()))
                .thenReturn(findById());

        todoService.deleteById(10);

        verify(todoRepository, times(1))
                .delete(any(Todo.class));
    }

    @Test
    @Order(15)
    void testDelete_exception() {
        assertThrows(TodoNotFoundException.class,
                () -> todoService.deleteById(10));

        verify(todoRepository, times(0))
                .delete(any(Todo.class));
    }

    @Test
    @Order(16)
    void testDeleteAll() {
        todoService.deleteAll();

        verify(todoRepository, times(1))
                .deleteAll();
    }

    @Test
    @Order(17)
    void testDeleteAllByFilters_option1() {
        todoService.deleteAllByFilters(Title.OTHER, Status.COMPLETED);

        verify(todoRepository, times(1))
                .deleteAllByFilters(Title.OTHER, Status.COMPLETED);
    }

    @Test
    @Order(18)
    void testDeleteAllByFilters_option2() {
        todoService.deleteAllByFilters(Title.OTHER, null);

        verify(todoRepository, times(1))
                .deleteAllByFilters(Title.OTHER, null);
    }

    @Test
    @Order(19)
    void testDeleteAllByFilters_option3() {
        todoService.deleteAllByFilters(null, Status.COMPLETED);

        verify(todoRepository, times(1))
                .deleteAllByFilters(null, Status.COMPLETED);
    }
}