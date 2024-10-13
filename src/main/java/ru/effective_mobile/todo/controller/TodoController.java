package ru.effective_mobile.todo.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.effective_mobile.todo.controller.swagger.TodoControllerDoc;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;
import ru.effective_mobile.todo.service.TodoService;

import java.time.LocalDate;

import static ru.effective_mobile.todo.controller.swagger.Constants.*;

@RestController
@RequestMapping("/todo")
@RequiredArgsConstructor
public class TodoController implements TodoControllerDoc {

    private final TodoService todoService;

    @GetMapping("/all")
    public ResponseEntity<PaginatedResponse<TodoDto>> getAll(@RequestParam(name = PAGE, defaultValue = "1") int page,
                                                             @RequestParam(name = SIZE, defaultValue = "20") int size) {

        var todos = todoService.getAll(page, size);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/all/filters")
    public ResponseEntity<PaginatedResponse<TodoDto>> getAllByFilters(@RequestParam(name = TITLE, required = false) Title title,
                                                                      @RequestParam(name = STATUS, required = false) Status status,
                                                                      @RequestParam(name = IMPORTANCE, required = false) Importance importance,
                                                                      @RequestParam(name = URGENCY, required = false) Urgency urgency,
                                                                      @RequestParam(name = DEADLINE, required = false) LocalDate deadline,
                                                                      @RequestParam(name = PAGE, defaultValue = "1") int page,
                                                                      @RequestParam(name = SIZE, defaultValue = "20") int size) {

        var todos = todoService.getAllByFilters(title, status, importance, urgency, deadline, page, size);
        return ResponseEntity.ok(todos);
    }

    @GetMapping("/{ID}")
    public ResponseEntity<TodoDto> getById(@PathVariable(name = ID) long id) {
        var todo = todoService.getById(id);
        return ResponseEntity.ok(todo);
    }

    @PostMapping
    public ResponseEntity<Void> create(@RequestBody CreateOrUpdateDto dto,
                                       @RequestParam(name = TITLE, required = false) Title title,
                                       @RequestParam(name = STATUS, required = false) Status status,
                                       @RequestParam(name = IMPORTANCE, required = false) Importance importance,
                                       @RequestParam(name = URGENCY, required = false) Urgency urgency,
                                       @RequestParam(name = DEADLINE, required = false) LocalDate deadline) {

        todoService.create(dto, title, status, importance, urgency, deadline);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @PatchMapping("/{ID}/description")
    public ResponseEntity<Void> updateDescription(@PathVariable(name = ID) long id,
                                                  @RequestBody CreateOrUpdateDto dto) {

        todoService.updateDescription(id, dto);
        return ResponseEntity.ok().build();
    }

    @PatchMapping("/{ID}/filters")
    public ResponseEntity<Void> updateFilters(@PathVariable(name = ID) long id,
                                              @RequestParam(name = TITLE, required = false) Title title,
                                              @RequestParam(name = STATUS, required = false) Status status,
                                              @RequestParam(name = IMPORTANCE, required = false) Importance importance,
                                              @RequestParam(name = URGENCY, required = false) Urgency urgency,
                                              @RequestParam(name = DEADLINE, required = false) LocalDate deadline) {

        todoService.updateFilters(id, title, status, importance, urgency, deadline);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{ID}")
    public ResponseEntity<Void> deleteById(@PathVariable(name = ID) long id) {
        todoService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all")
    public ResponseEntity<Void> deleteAll() {
        todoService.deleteAll();
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping("/all/filters")
    public ResponseEntity<Void> delete(@RequestParam(name = TITLE, required = false) Title title,
                                       @RequestParam(name = STATUS, required = false) Status status) {

        todoService.deleteAllByFilters(title, status);
        return ResponseEntity.noContent().build();
    }
}