package ru.effective_mobile.todo.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import ru.effective_mobile.todo.dto.CreateOrUpdateDto;
import ru.effective_mobile.todo.dto.TodoDto;
import ru.effective_mobile.todo.model.Todo;

@Mapper
public interface TodoMapper {

    TodoMapper INSTANCE = Mappers.getMapper(TodoMapper.class);

    TodoDto entityToDto(Todo todo);

    Todo dtoToEntity(CreateOrUpdateDto dto);
}