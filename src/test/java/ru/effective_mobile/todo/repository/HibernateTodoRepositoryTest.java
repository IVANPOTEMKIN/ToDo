package ru.effective_mobile.todo.repository;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;
import static ru.effective_mobile.todo.utils.Utils.*;

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
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findAll() {
        var expected = findAll();
        var actual = todoRepository.findAll(1, 3);

        System.out.println("actual = " + actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов без фильтров")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findAllByFilters_withoutFilters() {
        var expected = findAllWithoutFilters();
        var actual = todoRepository.findAllByFilters(
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
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findAllByFilters_withTitle() {
        var expected = findAllByTitle();
        var actual = todoRepository.findAllByFilters(
                Title.STUDY,
                null,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title, status")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findAllByFilters_withTitleAndStatus() {
        var expected = findAllByTitleAndStatus();
        var actual = todoRepository.findAllByFilters(
                Title.STUDY,
                Status.NEW,
                null,
                null,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title, status, importance, urgency")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findAllByFilters_withTitleAndStatusAndImportanceAndUrgency() {
        var expected = findAllByTitleAndStatusAndImportanceAndUrgency();
        var actual = todoRepository.findAllByFilters(
                Title.STUDY,
                Status.NEW,
                Importance.IMPORTANT,
                Urgency.URGENT,
                null,
                1, 3);

        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Получение всех элементов по title, status, importance, urgency, deadline")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findAllByFilters_withTitleAndStatusAndImportanceAndUrgencyAndDeadline() {
        var expected = findAllByTitleAndStatusAndImportanceAndUrgencyAndDeadline();
        var actual = todoRepository.findAllByFilters(
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
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_findById() {
        var expected = findById();
        var actual = todoRepository.findById(10);

        assertEquals(expected, actual);
    }

    @Test
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Сохранение нового элемента")
    void test_save() {
        var newTodo = Todo.builder().description("Description").build();
        var saved = getSavedTodo();

        var todo = todoRepository.findById(16);
        var actual = todoRepository.findAll(1, 3).getTotalElements();

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
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @DisplayName("Изменение элемента")
    void test_update() {
        var expectedOld = getOldTodo();
        var expectedNew = getUpdatedTodo();
        var actual = todoRepository.findById(10);

        assertTrue(actual.isPresent());
        assertEquals(expectedOld, actual.get());

        actual.get().setTitle(Title.HOME);
        actual.get().setDescription("Updated description");
        actual.get().setStatus(Status.IN_PROGRESS);
        actual.get().setImportance(Importance.IMPORTANT);
        actual.get().setUrgency(Urgency.URGENT);
        actual.get().setDeadline(LocalDate.now().plusWeeks(2));

        todoRepository.update(actual.get());

        assertEquals(expectedNew, actual.get());
    }

    @Test
    @DisplayName("Удаление элемента")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_delete() {
        var todo = todoRepository.findById(1);
        var actual = todoRepository.findAll(1, 20).getTotalElements();

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
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_deleteAll() {
        var actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAll();

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов без фильтров")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_deleteAllByFilters_withoutFilters() {
        var actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(null, null);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(0, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов по title")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_deleteAllByFilters_withTitle() {
        var actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(Title.OTHER, null);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(12, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов по status")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_deleteAllByFilters_withStatus() {
        var actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(null, Status.COMPLETED);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(12, actual);
    }

    @Test
    @DisplayName("Удаление всех элементов по title, status")
    @Sql(scripts = {
            "/db/sql/create_table.sql",
            "/db/sql/insert_values.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(scripts = "/db/sql/drop_table.sql", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void test_deleteAllByFilters_withTitleAndStatus() {
        var actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(15, actual);

        todoRepository.deleteAllByFilters(Title.OTHER, Status.COMPLETED);

        actual = todoRepository.findAll(1, 20).getTotalElements();
        assertEquals(14, actual);
    }
}