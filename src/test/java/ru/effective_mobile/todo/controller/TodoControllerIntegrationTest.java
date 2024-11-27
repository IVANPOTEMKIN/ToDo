package ru.effective_mobile.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.effective_mobile.todo.Utils;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.exception.TodoNotFoundException;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
class TodoControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:latest");

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач - успешно")
    void test_getAll_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getAll());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач - ошибка")
    void test_getAll_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all")
                        .param("Кол-во страниц", "0")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "0"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач без фильтров")
    void test_getAllByFilters_withoutFilters_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getAll());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач по title")
    void test_getAllByFilters_withTitle_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getAllByTitle());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Заголовок", Title.STUDY.name())
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач по title, importance")
    void test_getAllByFilters_withTitleAndImportance_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getAllByTitleAndImportance());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Заголовок", Title.STUDY.name())
                        .param("Статус", Status.NEW.name())
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач по title, importance, urgency")
    void test_getAllByFilters_withTitleAndImportanceAndUrgency_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getAllByTitleAndImportanceAndUrgency());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3")
                        .param("Заголовок", Title.STUDY.name())
                        .param("Статус", Status.NEW.name())
                        .param("Важность", Importance.IMPORTANT.name())
                        .param("Срочность", Urgency.URGENT.name()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач по title, importance, urgency, deadline")
    void test_getAllByFilters_withTitleAndImportanceAndUrgencyAndDeadline_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getAllByTitleAndImportanceAndUrgencyAndDeadline());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3")
                        .param("Заголовок", Title.STUDY.name())
                        .param("Статус", Status.IN_PROGRESS.name())
                        .param("Важность", Importance.IMPORTANT.name())
                        .param("Срочность", Urgency.URGENT.name())
                        .param("Дедлайн", LocalDate.now().plusDays(3).toString()))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение всех задач по фильтрам - ошибка")
    void test_getAllByFilters_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "0")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "0"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Получение задачи по её ID - успешно")
    void test_getById_success() throws Exception {
        String response = objectMapper.writeValueAsString(Utils.getById());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 10))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @Transactional
    @DisplayName("Получение задачи по её ID - ошибка")
    void test_getById_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 20))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Получение задачи по её ID - ошибка")
    void test_getById_BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 0))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Создание новой задачи с дефолтными значениями - успешно")
    void test_creat_success_option1() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("Test description");

        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @DisplayName("Создание новой задачи с заданными значениями - успешно")
    void test_creat_success_option2() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("Test description");

        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .param("Заголовок", Title.HOME.name())
                        .param("Статус", Status.CANCELLED.name())
                        .param("Важность", Importance.IMPORTANT.name())
                        .param("Срочность", Urgency.URGENT.name())
                        .param("Дедлайн", LocalDate.now().plusDays(5).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isCreated());
    }

    @Test
    @Transactional
    @DisplayName("Создание новой задачи - ошибка")
    void test_creat_exception() throws Exception {
        CreateOrUpdateDto empty = new CreateOrUpdateDto("");
        CreateOrUpdateDto dto = new CreateOrUpdateDto("Test description");

        String request1 = objectMapper.writeValueAsString(empty);
        String request2 = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .param("Дедлайн", LocalDate.now().minusDays(5).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Изменение описания задачи по её ID - успешно")
    void test_updateDescription_success() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("Изменение описания задачи по её ID - ошибка")
    void test_updateDescription_NotFoundException() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Изменение описания задачи по её ID - ошибка")
    void test_updateDescription_BadRequestException() throws Exception {
        CreateOrUpdateDto empty = new CreateOrUpdateDto("");
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String request1 = objectMapper.writeValueAsString(dto);
        String request2 = objectMapper.writeValueAsString(empty);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request1))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Изменение параметров задачи по её ID - успешно")
    void test_updateFilters_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 10)
                        .param("Заголовок", Title.FRIENDS.name())
                        .param("Статус", Status.COMPLETED.name())
                        .param("Важность", Importance.UNIMPORTANT.name())
                        .param("Срочность", Urgency.NON_URGENT.name())
                        .param("Дедлайн", LocalDate.now().plusDays(2).toString()))
                .andExpect(status().isOk());
    }

    @Test
    @Transactional
    @DisplayName("Изменение параметров задачи по её ID - ошибка")
    void test_updateFilters_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 20))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Изменение параметров задачи по её ID - ошибка")
    void test_updateFilters_BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 0))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 10)
                        .param("Дедлайн", LocalDate.now().minusDays(3).toString()))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Удаление задачи по её ID - успешно")
    void test_delete_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @DisplayName("Удаление задачи по её ID - ошибка")
    void test_delete_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 20))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Удаление задачи по её ID - ошибка")
    void test_delete_BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 0))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Transactional
    @DisplayName("Удаление всех задач")
    void test_deleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @DisplayName("Удаление всех задач без фильтров")
    void test_deleteAllByFilters_withoutFilters() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters"))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @DisplayName("Удаление всех задач по title")
    void test_deleteAllByFilters_withTitle() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Заголовок", Title.OTHER.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @DisplayName("Удаление всех задач по status")
    void test_deleteAllByFilters_withStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Статус", Status.COMPLETED.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Transactional
    @DisplayName("Удаление всех задач по title, status")
    void test_deleteAllByFilters_withTitleAndStatus() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Заголовок", Title.OTHER.name())
                        .param("Статус", Status.COMPLETED.name()))
                .andExpect(status().isNoContent());
    }
}