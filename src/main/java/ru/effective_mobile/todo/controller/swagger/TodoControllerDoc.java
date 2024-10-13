package ru.effective_mobile.todo.controller.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;
import static ru.effective_mobile.todo.controller.swagger.Constants.*;

@Tag(name = "TODO", description = "Контроллер для управления задачами")
public interface TodoControllerDoc {

    @Operation(
            summary = "Получение всех задач",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = MESSAGE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TodoDto.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<PaginatedResponse<TodoDto>> getAll(int page,
                                                      int size);

    @Operation(
            summary = "Получение всех задач с заданными фильтрами",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = MESSAGE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = TodoDto.class)
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<PaginatedResponse<TodoDto>> getAllByFilters(Title title,
                                                               Status status,
                                                               Importance importance,
                                                               Urgency urgency,
                                                               LocalDate deadline,
                                                               int page,
                                                               int size);

    @Operation(
            summary = "Получение задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = MESSAGE_200,
                            content = @Content(
                                    mediaType = APPLICATION_JSON_VALUE,
                                    schema = @Schema(implementation = TodoDto.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = MESSAGE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = MESSAGE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<TodoDto> getById(long id);

    @Operation(
            summary = "Создание новой задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_201,
                            description = MESSAGE_201,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = MESSAGE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<Void> create(CreateOrUpdateDto dto,
                                Title title,
                                Status status,
                                Importance importance,
                                Urgency urgency,
                                LocalDate deadline);

    @Operation(
            summary = "Изменение описания существующей задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = MESSAGE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = MESSAGE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = MESSAGE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<Void> updateDescription(long id,
                                           CreateOrUpdateDto dto);

    @Operation(
            summary = "Изменение фильтров существующей задачи",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_200,
                            description = MESSAGE_200,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = MESSAGE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<Void> updateFilters(long id,
                                       Title title,
                                       Status status,
                                       Importance importance,
                                       Urgency urgency,
                                       LocalDate deadline);

    @Operation(
            summary = "Удаление задачи по ID",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_204,
                            description = MESSAGE_204,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_400,
                            description = MESSAGE_400,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_404,
                            description = MESSAGE_404,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<Void> deleteById(long id);

    @Operation(
            summary = "Удаление всех задач",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_204,
                            description = MESSAGE_204,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<Void> deleteAll();

    @Operation(
            summary = "Удаление всех задач с заданными фильтрами",
            responses = {
                    @ApiResponse(
                            responseCode = CODE_204,
                            description = MESSAGE_204,
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = CODE_500,
                            description = MESSAGE_500,
                            content = @Content()
                    )
            }
    )
    ResponseEntity<Void> delete(Title title,
                                Status status);
}