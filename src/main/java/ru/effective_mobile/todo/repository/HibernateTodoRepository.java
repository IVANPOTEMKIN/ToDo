package ru.effective_mobile.todo.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;
import java.util.Optional;

@Repository
public class HibernateTodoRepository implements TodoRepository {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public PaginatedResponse<Todo> findAll(int page, int size) {
        var todos = entityManager.createQuery("FROM todo", Todo.class)
                .setFirstResult(page - 1)
                .setMaxResults(size)
                .getResultList();

        var totalElements = countTodos();
        return new PaginatedResponse<>(todos, totalElements, page, size);
    }

    @Override
    public PaginatedResponse<Todo> findAllByFilters(Title title,
                                                    Status status,
                                                    Importance importance,
                                                    Urgency urgency,
                                                    LocalDate deadline,
                                                    int page, int size) {

        var filters = appendExistingFilters(title, status, importance, urgency, deadline);
        var hql = "FROM todo WHERE 1=1" + filters;
        var query = entityManager.createQuery(hql, Todo.class);

        setParameters(title, status, importance, urgency, deadline, query);

        System.out.println(hql);

        var todos = query
                .setFirstResult(page - 1)
                .setMaxResults(size)
                .getResultList();

        var totalElements = countTodosByFilters(title, status, importance, urgency, deadline);
        return new PaginatedResponse<>(todos, totalElements, page, size);
    }

    @Override
    public Optional<Todo> findById(long id) {
        var todo = entityManager.find(Todo.class, id);
        return Optional.ofNullable(todo);
    }

    @Override
    public void save(Todo todo) {
        entityManager.persist(todo);
    }

    @Override
    public void update(Todo todo) {
        entityManager.merge(todo);
    }

    @Override
    public void delete(Todo todo) {
        entityManager.remove(todo);
    }

    @Override
    public void deleteAll() {
        entityManager.createQuery("DELETE FROM todo").executeUpdate();
    }

    @Override
    public void deleteAllByFilters(Title title,
                                   Status status) {

        var hql = new StringBuilder("DELETE FROM todo WHERE 1=1");

        if (title != null) hql.append(" AND title = :title");
        if (status != null) hql.append(" AND status = :status");

        var query = entityManager.createQuery(hql.toString());

        if (title != null) query.setParameter("title", title);
        if (status != null) query.setParameter("status", status);

        query.executeUpdate();
    }

    private Long countTodos() {
        return entityManager.createQuery("SELECT COUNT(*) FROM todo", Long.class).getSingleResult();
    }

    private Long countTodosByFilters(Title title,
                                        Status status,
                                        Importance importance,
                                        Urgency urgency,
                                        LocalDate deadline) {

        var filters = appendExistingFilters(title, status, importance, urgency, deadline);
        var hql = "SELECT COUNT(*) FROM todo WHERE 1=1" + filters;

        var query = entityManager.createQuery(hql, Long.class);
        setParameters(title, status, importance, urgency, deadline, query);

        return query.getSingleResult();
    }

    private StringBuilder appendExistingFilters(Title title,
                                                Status status,
                                                Importance importance,
                                                Urgency urgency,
                                                LocalDate deadline) {

        var hql = new StringBuilder();

        if (title != null) hql.append(" AND title = :title");
        if (status != null) hql.append(" AND status = :status");
        if (importance != null) hql.append(" AND importance = :importance");
        if (urgency != null) hql.append(" AND urgency = :urgency");
        if (deadline != null) hql.append(" AND deadline = :deadline");

        return hql;
    }

    private static void setParameters(Title title,
                                      Status status,
                                      Importance importance,
                                      Urgency urgency,
                                      LocalDate deadline,
                                      TypedQuery<?> query) {

        if (title != null) query.setParameter("title", title);
        if (status != null) query.setParameter("status", status);
        if (importance != null) query.setParameter("importance", importance);
        if (urgency != null) query.setParameter("urgency", urgency);
        if (deadline != null) query.setParameter("deadline", deadline);
    }
}