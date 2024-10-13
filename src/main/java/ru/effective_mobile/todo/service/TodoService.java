package ru.effective_mobile.todo.service;

import jakarta.validation.Valid;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Positive;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

public interface TodoService {

    /**
     * Получает все задачи с пагинацией.
     *
     * @param page номер страницы (начинается с 1)
     * @param size количество задач на странице
     * @return объект PaginatedResponse с задачами
     */
    PaginatedResponse<TodoDto> getAll(@Positive(message = "Значение не может быть меньше 1") int page,
                                      @Positive(message = "Значение не может быть меньше 1") int size);

    /**
     * Получает задачи по заданным фильтрам с пагинацией.
     *
     * @param title      заголовок задачи (может быть null)
     * @param status     статус задачи (может быть null)
     * @param importance важность задачи (может быть null)
     * @param urgency    срочность задачи (может быть null)
     * @param deadline   срок выполнения задачи (может быть null)
     * @param page       номер страницы (начинается с 1)
     * @param size       количество задач на странице
     * @return объект PaginatedResponse с задачами
     */
    PaginatedResponse<TodoDto> getAllByFilters(Title title,
                                               Status status,
                                               Importance importance,
                                               Urgency urgency,
                                               LocalDate deadline,
                                               @Positive(message = "Значение не может быть меньше 1") int page,
                                               @Positive(message = "Значение не может быть меньше 1") int size);

    /**
     * Получает задачу по ее идентификатору.
     *
     * @param id идентификатор задачи
     * @return объект TodoDto с данными задачи
     */
    TodoDto getById(@Positive(message = "Значение не может быть меньше 1") long id);

    /**
     * Создает новую задачу.
     *
     * @param dto        данные для создания задачи
     * @param title      заголовок задачи
     * @param status     статус задачи
     * @param importance важность задачи
     * @param urgency    срочность задачи
     * @param deadline   срок выполнения задачи
     */
    void create(@Valid CreateOrUpdateDto dto,
                Title title,
                Status status,
                Importance importance,
                Urgency urgency,
                @FutureOrPresent(message = "Дата выполнения задачи не может быть раньше текущей") LocalDate deadline);

    /**
     * Обновляет описание задачи.
     *
     * @param id  идентификатор задачи
     * @param dto новые данные для обновления
     */
    void updateDescription(@Positive(message = "Значение не может быть меньше 1") long id,
                           @Valid CreateOrUpdateDto dto);

    /**
     * Обновляет параметры задачи по заданному идентификатору.
     *
     * @param id         идентификатор задачи
     * @param title      заголовок задачи (может быть null)
     * @param status     статус задачи (может быть null)
     * @param importance важность задачи (может быть null)
     * @param urgency    срочность задачи (может быть null)
     * @param deadline   срок выполнения задачи (может быть null)
     */
    void updateFilters(@Positive(message = "Значение не может быть меньше 1") long id,
                       Title title,
                       Status status,
                       Importance importance,
                       Urgency urgency,
                       @FutureOrPresent(message = "Дата выполнения задачи не может быть раньше текущей") LocalDate deadline);

    /**
     * Удаляет задачу по ее идентификатору.
     *
     * @param id идентификатор задачи
     */
    void deleteById(@Positive(message = "Значение не может быть меньше 1") long id);

    /**
     * Удаляет все задачи.
     */
    void deleteAll();

    /**
     * Удаляет все задачи по заданным фильтрам.
     *
     * @param title  заголовок задачи (может быть null)
     * @param status статус задачи (может быть null)
     */
    void deleteAllByFilters(Title title,
                            Status status);
}