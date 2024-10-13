package ru.effective_mobile.todo.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.exception.TodoNotFoundException;
import ru.effective_mobile.todo.mapper.TodoMapper;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;
import ru.effective_mobile.todo.repository.TodoRepository;
import ru.effective_mobile.todo.service.TodoService;

import java.time.LocalDate;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Validated
public class TodoServiceImpl implements TodoService {

    private final TodoRepository todoRepository;

    @Override
    public PaginatedResponse<TodoDto> getAll(int page, int size) {
        var todos = todoRepository.findAll(page, size);
        return mapPaginatedResponse(todos);
    }

    @Override
    public PaginatedResponse<TodoDto> getAllByFilters(Title title,
                                                      Status status,
                                                      Importance importance,
                                                      Urgency urgency,
                                                      LocalDate deadline,
                                                      int page, int size) {

        var todos = todoRepository.findAllByFilters(title, status, importance, urgency, deadline, page, size);
        return mapPaginatedResponse(todos);
    }

    @Cacheable(value = "todos", key = "#id")
    @Override
    public TodoDto getById(long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        return TodoMapper.INSTANCE.entityToDto(todo);
    }

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    @Override
    public void create(CreateOrUpdateDto dto,
                       Title title,
                       Status status,
                       Importance importance,
                       Urgency urgency,
                       LocalDate deadline) {

        var todo = TodoMapper.INSTANCE.dtoToEntity(dto);
        setExistingFields(title, status, importance, urgency, deadline, todo);
        todoRepository.save(todo);
    }

    @CachePut(value = "todos", key = "#id")
    @Transactional
    @Override
    public void updateDescription(long id, CreateOrUpdateDto dto) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        Optional.ofNullable(dto.description()).ifPresent(todo::setDescription);
        todoRepository.update(todo);
    }

    @CachePut(value = "todos", key = "#id")
    @Transactional
    @Override
    public void updateFilters(long id,
                              Title title,
                              Status status,
                              Importance importance,
                              Urgency urgency,
                              LocalDate deadline) {

        var todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        setExistingFields(title, status, importance, urgency, deadline, todo);
        todoRepository.update(todo);
    }

    @CacheEvict(value = "todos", key = "#id")
    @Transactional
    @Override
    public void deleteById(long id) {
        var todo = todoRepository.findById(id).orElseThrow(() -> new TodoNotFoundException(id));
        todoRepository.delete(todo);
    }

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    @Override
    public void deleteAll() {
        todoRepository.deleteAll();
    }

    @CacheEvict(value = "todos", allEntries = true)
    @Transactional
    @Override
    public void deleteAllByFilters(Title title, Status status) {
        todoRepository.deleteAllByFilters(title, status);
    }

    /**
     * Устанавливает существующие поля задачи.
     *
     * @param title      заголовок задачи
     * @param status     статус задачи
     * @param importance важность задачи
     * @param urgency    срочность задачи
     * @param deadline   срок выполнения задачи
     * @param todo       задача, для которой устанавливаются поля
     */
    private static void setExistingFields(Title title,
                                          Status status,
                                          Importance importance,
                                          Urgency urgency,
                                          LocalDate deadline,
                                          Todo todo) {

        Optional.ofNullable(title).ifPresent(todo::setTitle);
        Optional.ofNullable(status).ifPresent(todo::setStatus);
        Optional.ofNullable(importance).ifPresent(todo::setImportance);
        Optional.ofNullable(urgency).ifPresent(todo::setUrgency);
        Optional.ofNullable(deadline).ifPresent(todo::setDeadline);
    }

    /**
     * Преобразует PaginatedResponse<Todo> в PaginatedResponse<TodoDto>.
     *
     * @param paginatedResponse объект PaginatedResponse<Todo> для преобразования
     * @return объект PaginatedResponse<TodoDto>
     */
    private PaginatedResponse<TodoDto> mapPaginatedResponse(PaginatedResponse<Todo> paginatedResponse) {
        return new PaginatedResponse<>(
                paginatedResponse.getItems()
                        .stream()
                        .map(TodoMapper.INSTANCE::entityToDto)
                        .toList(),
                paginatedResponse.getTotalElements(),
                paginatedResponse.getCurrentPage(),
                paginatedResponse.getPageSize()
        );
    }
}