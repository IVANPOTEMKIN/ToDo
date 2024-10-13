package ru.effective_mobile.todo.repository;

import lombok.RequiredArgsConstructor;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class JdbcTodoRepository implements TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    private final RowMapper<Todo> todoRowMapper = (rs, rowNum) -> mapRowToTodo(rs);

    @Override
    public PaginatedResponse<Todo> findAll(int page, int size) {
        String sql = "SELECT * FROM todo ORDER BY id LIMIT ? OFFSET ?";
        List<Todo> todos = jdbcTemplate.query(sql, todoRowMapper, size, (page - 1) * size);
        int totalElements = countTodos();
        return new PaginatedResponse<>(todos, totalElements, page, size);
    }

    @Override
    public PaginatedResponse<Todo> findAllByFilters(Title title,
                                                    Status status,
                                                    Importance importance,
                                                    Urgency urgency,
                                                    LocalDate deadline,
                                                    int page, int size) {

        StringBuilder filters = appendExistingFilters(title, status, importance, urgency, deadline);
        String sql = String.format("SELECT * FROM todo WHERE 1=1%s ORDER BY deadline LIMIT ? OFFSET ?", filters);
        List<Todo> todos = jdbcTemplate.query(sql, todoRowMapper, size, (page - 1) * size);
        int totalElements = countTodosByFilters(title, status, importance, urgency, deadline);
        return new PaginatedResponse<>(todos, totalElements, page, size);
    }

    @Override
    public Optional<Todo> findById(long id) {
        String sql = "SELECT * FROM todo WHERE id = ?";
        return jdbcTemplate.query(sql, todoRowMapper, id).stream().findFirst();
    }

    @Override
    public void save(Todo todo) {
        StringBuilder sql = new StringBuilder("INSERT INTO todo (");
        StringBuilder values = new StringBuilder("VALUES (");

        List<Object> params = new ArrayList<>();

        addFieldAndValue(sql, values, params, "title", todo.getTitle() != null ? todo.getTitle().name() : null);
        addFieldAndValue(sql, values, params, "description", todo.getDescription());
        addFieldAndValue(sql, values, params, "status", todo.getStatus() != null ? todo.getStatus().name() : null);
        addFieldAndValue(sql, values, params, "importance", todo.getImportance() != null ? todo.getImportance().name() : null);
        addFieldAndValue(sql, values, params, "urgency", todo.getUrgency() != null ? todo.getUrgency().name() : null);
        addFieldAndValue(sql, values, params, "deadline", todo.getDeadline() != null ? todo.getDeadline() : null);

        sql.setLength(sql.length() - 2);
        values.setLength(values.length() - 2);

        sql.append(") ").append(values.append(")"));
        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    @Override
    public void update(Todo todo) {
        String sql = "UPDATE todo SET title = ?, description = ?, status = ?, importance = ?, urgency = ?, deadline = ? WHERE id = ?";
        jdbcTemplate.update(sql,
                todo.getTitle().name(),
                todo.getDescription(),
                todo.getStatus().name(),
                todo.getImportance().name(),
                todo.getUrgency().name(),
                todo.getDeadline(),
                todo.getId());
    }

    @Override
    public void delete(Todo todo) {
        String sql = "DELETE FROM todo WHERE id = ?";
        jdbcTemplate.update(sql, todo.getId());
    }

    @Override
    public void deleteAll() {
        String sql = "TRUNCATE todo";
        jdbcTemplate.update(sql);
    }

    @Override
    public void deleteAllByFilters(Title title,
                                   Status status) {

        if (title == null && status == null) {
            return;
        }

        StringBuilder sql = new StringBuilder("DELETE FROM todo WHERE 1=1");
        List<Object> params = new ArrayList<>();

        if (title != null) {
            sql.append(" AND title = ?");
            params.add(title.name());
        }

        if (status != null) {
            sql.append(" AND status = ?");
            params.add(status.name());
        }

        jdbcTemplate.update(sql.toString(), params.toArray());
    }

    private Todo mapRowToTodo(ResultSet rs) throws SQLException {
        return Todo.builder()
                .id(rs.getLong("id"))
                .createdAt(rs.getObject("created_at", LocalDate.class))
                .title(Title.valueOf(rs.getString("title")))
                .description(rs.getString("description"))
                .status(Status.valueOf(rs.getString("status")))
                .importance(Importance.valueOf(rs.getString("importance")))
                .urgency(Urgency.valueOf(rs.getString("urgency")))
                .deadline(rs.getObject("deadline", LocalDate.class))
                .build();
    }

    private Integer countTodos() {
        String sql = "SELECT COUNT(*) FROM todo";
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private Integer countTodosByFilters(Title title,
                                        Status status,
                                        Importance importance,
                                        Urgency urgency,
                                        LocalDate deadline) {

        StringBuilder filters = appendExistingFilters(title, status, importance, urgency, deadline);
        String sql = "SELECT COUNT(*) FROM todo WHERE 1=1" + filters;
        return jdbcTemplate.queryForObject(sql, Integer.class);
    }

    private void addFieldAndValue(StringBuilder sql,
                                  StringBuilder values,
                                  List<Object> params,
                                  String fieldName,
                                  Object value) {

        if (value != null) {
            sql.append(fieldName).append(", ");
            values.append("?, ");
            params.add(value);

        } else {
            sql.append(fieldName).append(", ");
            values.append("DEFAULT, ");
        }
    }

    private StringBuilder appendExistingFilters(Title title,
                                                Status status,
                                                Importance importance,
                                                Urgency urgency,
                                                LocalDate deadline) {

        StringBuilder sql = new StringBuilder();
        sql.append(title == null ? "" : sql.append(" AND title = '").append(title.name()).append("'"));
        sql.append(status == null ? "" : sql.append(" AND status = '").append(status.name()).append("'"));
        sql.append(importance == null ? "" : sql.append(" AND importance = '").append(importance.name()).append("'"));
        sql.append(urgency == null ? "" : sql.append(" AND urgency = '").append(urgency.name()).append("'"));
        sql.append(deadline == null ? "" : sql.append(" AND deadline = '").append(deadline).append("'"));
        return sql;
    }
}