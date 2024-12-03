package ru.effective_mobile.todo.mapper;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class TodoMapperTest {

    @Test
    @DisplayName("Маппинг сущности в дто")
    void test_entityToDto() {
        var todo = Todo.builder()
                .id(1L)
                .createdAt(LocalDate.now())
                .title(Title.STUDY)
                .description("Description")
                .status(Status.NEW)
                .importance(Importance.IMPORTANT)
                .urgency(Urgency.URGENT)
                .deadline(LocalDate.now().plusWeeks(1L))
                .build();

        var expected = TodoDto.builder()
                .id(1L)
                .title(Title.STUDY)
                .description("Description")
                .status(Status.NEW)
                .importance(Importance.IMPORTANT)
                .urgency(Urgency.URGENT)
                .deadline(LocalDate.now().plusWeeks(1L))
                .build();

        var actual = TodoMapper.INSTANCE.entityToDto(todo);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }

    @Test
    @DisplayName("Маппинг дто в сущность")
    void test_dtoToEntity() {
        var dto = new CreateOrUpdateDto("New description");

        var expected = Todo.builder()
                .id(null)
                .createdAt(null)
                .title(null)
                .description("New description")
                .status(null)
                .importance(null)
                .urgency(null)
                .deadline(null)
                .build();

        var actual = TodoMapper.INSTANCE.dtoToEntity(dto);

        assertNotNull(actual);
        assertEquals(expected, actual);
    }
}