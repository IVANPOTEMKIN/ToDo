package ru.effective_mobile.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import ru.effective_mobile.todo.Utils;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.exception.TodoNotFoundException;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;
import ru.effective_mobile.todo.repository.JdbcTodoRepository;
import ru.effective_mobile.todo.service.impl.TodoServiceImpl;

import java.time.LocalDate;

import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TodoController.class)
@EnableTransactionManagement(proxyTargetClass = true)
public class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private JdbcTodoRepository todoRepository;

    @MockBean
    private PlatformTransactionManager transactionManager;

    @SpyBean
    private TodoServiceImpl todoService;

    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @DisplayName("Получение всех задач - успешно")
    void test_getAll_success() throws Exception {
        when(todoRepository.findAll(1, 3))
                .thenReturn(Utils.findAll());

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
    @DisplayName("Получение всех задач по набору параметров №1 - успешно")
    void test_getAllByFilters_success_option1() throws Exception {
        when(todoRepository.findAllByFilters(null, null, null, null, null, 1, 3))
                .thenReturn(Utils.findAllByFiltersOption1());

        String response = objectMapper.writeValueAsString(Utils.getAllByFiltersOption1());

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
    @DisplayName("Получение всех задач по набору параметров №2 - успешно")
    void test_getAllByFilters_success_option2() throws Exception {
        when(todoRepository.findAllByFilters(Title.STUDY, null, null, null, null, 1, 3))
                .thenReturn(Utils.findAllByFiltersOption2());

        String response = objectMapper.writeValueAsString(Utils.getAllByFiltersOption2());

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
    @DisplayName("Получение всех задач по набору параметров №3 - успешно")
    void test_getAllByFilters_success_option3() throws Exception {
        when(todoRepository.findAllByFilters(Title.STUDY, Status.NEW, null, null, null, 1, 3))
                .thenReturn(Utils.findAllByFiltersOption3());

        String response = objectMapper.writeValueAsString(Utils.getAllByFiltersOption3());

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
    @DisplayName("Получение всех задач по набору параметров №4 - успешно")
    void test_getAllByFilters_success_option4() throws Exception {
        when(todoRepository.findAllByFilters(Title.STUDY, Status.NEW, Importance.IMPORTANT, Urgency.URGENT, null, 1, 3))
                .thenReturn(Utils.findAllByFiltersOption4());

        String response = objectMapper.writeValueAsString(Utils.getAllByFiltersOption4());

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
    @DisplayName("Получение всех задач по набору параметров №5 - успешно")
    void test_getAllByFilters_success_option5() throws Exception {
        when(todoRepository.findAllByFilters(Title.STUDY, Status.IN_PROGRESS, Importance.IMPORTANT, Urgency.URGENT, LocalDate.now().plusDays(3), 1, 3))
                .thenReturn(Utils.findAllByFiltersOption5());

        String response = objectMapper.writeValueAsString(Utils.getAllByFiltersOption5());

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
    @DisplayName("Получение всех задач по набору параметров - ошибка")
    void test_getAllByFilters_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "0")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
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
    @DisplayName("Получение задачи по её ID - успешно")
    void test_getById_success() throws Exception {
        when(todoRepository.findById(anyLong()))
                .thenReturn(Utils.findById());

        String response = objectMapper.writeValueAsString(Utils.getById());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 1))
                .andExpectAll(
                        status().isOk(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        content().json(response));
    }

    @Test
    @DisplayName("Получение задачи по её ID - ошибка")
    void test_getById_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 100))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(100).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
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
    @DisplayName("Изменение описания задачи по её ID - успешно")
    void test_updateDescription_success() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String request = objectMapper.writeValueAsString(dto);

        when(todoRepository.findById(anyLong()))
                .thenReturn(Utils.findById());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Изменение описания задачи по её ID - ошибка")
    void test_updateDescription_NotFoundException() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String request = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 100)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(100).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
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
                        .patch("/todo/{id}/description", 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(request2))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Изменение параметров задачи по её ID - успешно")
    void test_updateFilters_success() throws Exception {
        when(todoRepository.findById(anyLong()))
                .thenReturn(Utils.findById());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 1)
                        .param("Заголовок", Title.FRIENDS.name())
                        .param("Статус", Status.COMPLETED.name())
                        .param("Важность", Importance.UNIMPORTANT.name())
                        .param("Срочность", Urgency.NON_URGENT.name())
                        .param("Дедлайн", LocalDate.now().plusDays(2).toString()))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Изменение параметров задачи по её ID - ошибка")
    void test_updateFilters_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 100))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(new TodoNotFoundException(100).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
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
                        .patch("/todo/{id}/filters", 1)
                        .param("Дедлайн", LocalDate.now().minusDays(3).toString()))
                .andExpectAll(
                        status().isBadRequest(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @DisplayName("Удаление задачи по её ID - успешно")
    void test_delete_success() throws Exception {
        when(todoRepository.findById(anyLong()))
                .thenReturn(Utils.findById());

        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Удаление задачи по её ID - ошибка")
    void test_delete_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 100))
                .andExpectAll(
                        status().isNotFound(),
                        content().contentType(MediaType.APPLICATION_JSON),
                        jsonPath("$.message").value(new TodoNotFoundException(100).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
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
    @DisplayName("Удаление всех задач")
    void test_deleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all"))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Удаление всех задач по набору параметров №1")
    void test_deleteAllByFilters_option1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Заголовок", Title.OTHER.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Удаление всех задач по набору параметров №2")
    void test_deleteAllByFilters_option2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Статус", Status.COMPLETED.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Удаление всех задач по набору параметров №3")
    void test_deleteAllByFilters_option3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Заголовок", Title.OTHER.name())
                        .param("Статус", Status.COMPLETED.name()))
                .andExpect(status().isNoContent());
    }
}