package ru.effective_mobile.todo.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import ru.effective_mobile.todo.Utils;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.exception.TodoNotFoundException;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;
import ru.effective_mobile.todo.service.TodoService;

import java.time.LocalDate;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Testcontainers
@TestMethodOrder(value = MethodOrderer.OrderAnnotation.class)
class TodoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    private TodoService todoService;

    @Container
    @ServiceConnection
    static PostgreSQLContainer<?> postgreSQLContainer = new PostgreSQLContainer<>("postgres:16.2");

    ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
    }

    @Test
    @Order(1)
    void testGetAll_success() throws Exception {
        String jsonObj = objectMapper.writeValueAsString(Utils.getAll());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(jsonObj));
    }

    @Test
    @Order(2)
    void testGetAll_exception() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all")
                        .param("Кол-во страниц", "0")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "0"))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(3)
    void testGetAllByFilters_option1() throws Exception {
        String jsonObj = objectMapper.writeValueAsString(Utils.getAllByFiltersOption1());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(jsonObj));
    }

    @Test
    @Order(4)
    void testGetAllByFilters_option2() throws Exception {
        String jsonObj = objectMapper.writeValueAsString(Utils.getAllByFiltersOption2());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Заголовок", Title.STUDY.name())
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(jsonObj));
    }

    @Test
    @Order(5)
    void testGetAllByFilters_option3() throws Exception {
        String jsonObj = objectMapper.writeValueAsString(Utils.getAllByFiltersOption3());

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/all/filters")
                        .param("Заголовок", Title.STUDY.name())
                        .param("Статус", Status.NEW.name())
                        .param("Кол-во страниц", "1")
                        .param("Кол-во задач", "3"))
                .andExpectAll(
                        status().isOk(),
                        content().contentType("application/json"),
                        content().json(jsonObj));
    }

    @Test
    @Order(6)
    void testGetAllByFilters_option4() throws Exception {
        String jsonObj = objectMapper.writeValueAsString(Utils.getAllByFiltersOption4());

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
                        content().contentType("application/json"),
                        content().json(jsonObj));
    }

    @Test
    @Order(7)
    void testGetAllByFilters_option5() throws Exception {
        String jsonObj = objectMapper.writeValueAsString(Utils.getAllByFiltersOption5());

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
                        content().contentType("application/json"),
                        content().json(jsonObj));
    }

    @Test
    @Order(8)
    void testGetAllByFilters_exception() throws Exception {
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
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(9)
    void testGetById_success() throws Exception {
        TodoDto expected = Utils.getById();

        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", expected.id()))
                .andExpectAll(
                        status().isOk(),
                        jsonPath("$.id").value(expected.id()),
                        jsonPath("$.title").value(expected.title().name()),
                        jsonPath("$.description").value(expected.description()),
                        jsonPath("$.status").value(expected.status().name()),
                        jsonPath("$.importance").value(expected.importance().name()),
                        jsonPath("$.urgency").value(expected.urgency().name()),
                        jsonPath("$.deadline").value(expected.deadline().toString()));
    }

    @Test
    @Order(10)
    void testGetById_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 20))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(11)
    void testGetById_BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .get("/todo/{id}", 0))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(12)
    void testCreat_success_option1() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("Test description");

        String jsonObj = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(13)
    void testCreat_success_option2() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("Test description");

        String jsonObj = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .param("Заголовок", Title.HOME.name())
                        .param("Статус", Status.CANCELLED.name())
                        .param("Важность", Importance.IMPORTANT.name())
                        .param("Срочность", Urgency.URGENT.name())
                        .param("Дедлайн", LocalDate.now().plusDays(5).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(14)
    void testCreat_exception() throws Exception {
        CreateOrUpdateDto empty = new CreateOrUpdateDto("");
        CreateOrUpdateDto dto = new CreateOrUpdateDto("Test description");

        String jsonObj1 = objectMapper.writeValueAsString(empty);
        String jsonObj2 = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj1))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .post("/todo")
                        .param("Дедлайн", LocalDate.now().minusDays(5).toString())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj2))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(13)
    void testUpdateDescription_success() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String jsonObj = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj))
                .andExpect(status().isOk());
    }

    @Test
    @Order(16)
    void testUpdateDescription_NotFoundException() throws Exception {
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String jsonObj = objectMapper.writeValueAsString(dto);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 20)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(16)
    void testUpdateDescription_BadRequestException() throws Exception {
        CreateOrUpdateDto empty = new CreateOrUpdateDto("");
        CreateOrUpdateDto dto = new CreateOrUpdateDto("New test description");

        String jsonObj1 = objectMapper.writeValueAsString(dto);
        String jsonObj2 = objectMapper.writeValueAsString(empty);

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 0)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj1))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/description", 10)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObj2))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(17)
    void testUpdateFilters_success() throws Exception {
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
    @Order(18)
    void testUpdateFilters_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 20))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(19)
    void testUpdateFilters_BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 0))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());

        mockMvc.perform(MockMvcRequestBuilders
                        .patch("/todo/{id}/filters", 10)
                        .param("Дедлайн", LocalDate.now().minusDays(3).toString()))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(20)
    void testDelete_success() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 1))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(20)
    void testDelete_NotFoundException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 20))
                .andExpectAll(
                        status().isNotFound(),
                        jsonPath("$.message").value(new TodoNotFoundException(20).getMessage()),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(20)
    void testDelete_BadRequestException() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/{id}", 0))
                .andExpectAll(
                        status().isBadRequest(),
                        jsonPath("$.message").isNotEmpty(),
                        jsonPath("$.timestamp").isNotEmpty());
    }

    @Test
    @Order(21)
    void testDeleteAllByFilters_option1() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Заголовок", Title.OTHER.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(22)
    void testDeleteAllByFilters_option2() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Статус", Status.COMPLETED.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(23)
    void testDeleteAllByFilters_option3() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all/filters")
                        .param("Заголовок", Title.OTHER.name())
                        .param("Статус", Status.COMPLETED.name()))
                .andExpect(status().isNoContent());
    }

    @Test
    @Order(24)
    void testDeleteAll() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders
                        .delete("/todo/all"))
                .andExpect(status().isNoContent());
    }
}