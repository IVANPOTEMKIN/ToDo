package ru.effective_mobile.todo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static ru.effective_mobile.todo.Utils.*;

@Testcontainers
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.NONE)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class HibernateTodoRepositoryTest {

    @Container
    @ServiceConnection
    public static PostgreSQLContainer<?> postgresContainer = new PostgreSQLContainer<>("postgres:latest");

    @Autowired
    private TodoRepository todoRepository;

    @Test
    @DisplayName("Получение всех элементов")
    @Transactional
    void test_findAll() {
        PaginatedResponse<Todo> expected = findAll();
        PaginatedResponse<Todo> actual = todoRepository.findAll(1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов без фильтров")
    @Transactional
    void test_findAllByFilters_withoutFilters() {
        PaginatedResponse<Todo> expected = findAll();
        PaginatedResponse<Todo> actual = todoRepository.findAllByFilters(
                null,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title")
    @Transactional
    void test_findAllByFilters_withTitle() {
        PaginatedResponse<Todo> expected = findAllByTitle();
        PaginatedResponse<Todo> actual = todoRepository.findAllByFilters(
                Title.STUDY,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title, importance")
    @Transactional
    void test_findAllByFilters_withTitleAndImportance() {
        PaginatedResponse<Todo> expected = findAllByTitleAndImportance();
        PaginatedResponse<Todo> actual = todoRepository.findAllByFilters(
                Title.STUDY,
                Status.NEW,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title, importance, urgency")
    @Transactional
    void test_findAllByFilters_withTitleAndImportanceAndUrgency() {
        PaginatedResponse<Todo> expected = findAllByTitleAndImportanceAndUrgency();
        PaginatedResponse<Todo> actual = todoRepository.findAllByFilters(
                Title.STUDY,
                Status.NEW,
                Importance.IMPORTANT,
                Urgency.URGENT,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title, importance, urgency, deadline")
    @Transactional
    void test_findAllByFilters_withTitleAndImportanceAndUrgencyAndDeadline() {
        PaginatedResponse<Todo> expected = findAllByTitleAndImportanceAndUrgencyAndDeadline();
        PaginatedResponse<Todo> actual = todoRepository.findAllByFilters(
                Title.STUDY,
                Status.NEW,
                Importance.IMPORTANT,
                Urgency.URGENT,
                LocalDate.now().plusDays(3),
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение элемента по его ID")
    @Transactional
    void test_findById() {
        Optional<Todo> expected = findById();
        Optional<Todo> actual = todoRepository.findById(10);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Сохранение нового элемента")
    @Transactional
    void test_save() {
        Todo newTodo = Todo.builder().description("Test description").build();
        Todo saved = getSavedTodo();

        Optional<Todo> todo = todoRepository.findById(16);
        long actual = todoRepository.findAll(1, 20).getTotalElements();

        assertFalse(todo.isPresent());
        assertEquals(15, actual);

        todoRepository.save(newTodo);

        todo = todoRepository.findById(16);
        actual = todoRepository.findAll(1, 20).getTotalElements();

        assertTrue(todo.isPresent());
        assertEquals(saved, todo.get());
        assertEquals(16, actual);
    }

    @Test
    @DisplayName("Изменение элемента")
    @Transactional
    void test_update() {
        Optional<Todo> expectedOld = getOldTodo();
        Optional<Todo> expectedNew = getNewTodo();
        Optional<Todo> actual = todoRepository.findById(10);

        assertTrue(actual.isPresent());
        assertEquals(expectedOld, actual);

        actual.get().setTitle(Title.HOME);
        actual.get().setDescription("Выполнить новую задачу");
        actual.get().setStatus(Status.IN_PROGRESS);
        actual.get().setImportance(Importance.IMPORTANT);
        actual.get().setUrgency(Urgency.URGENT);
        actual.get().setDeadline(LocalDate.now().plusDays(5));

        todoRepository.update(actual.get());

        assertEquals(expectedNew, actual);
    }

    @Test
    @DisplayName("Удаление элемента")
    @Transactional
    void test_delete() {
        Optional<Todo> todo = todoRepository.findById(1);
        long actual = todoRepository.findAll(1, 20).getTotalElements();

        assertTrue(todo.isPresent());
        assertEquals(15, actual);

        todoRepository.delete(todo.get());

        todo = todoRepository.findById(1);
        actual = todoRepository.findAll(1, 20).getTotalElements();

        assertFalse(todo.isPresent());
        assertEquals(14, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов")
    @Transactional
    void test_deleteAll() {
        long actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAll();

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов без фильтров")
    @Transactional
    void test_deleteAllByFilters_withoutFilters() {
        long actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(null, null);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов по title")
    @Transactional
    void test_deleteAllByFilters_withTitle() {
        long actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(Title.OTHER, null);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(12, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов по status")
    @Transactional
    void test_deleteAllByFilters_withStatus() {
        long actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(null, Status.COMPLETED);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(12, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов по title, status")
    @Transactional
    void test_deleteAllByFilters_withTitleAndStatus() {
        long actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(Title.OTHER, Status.COMPLETED);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(14, actual);
    }
}