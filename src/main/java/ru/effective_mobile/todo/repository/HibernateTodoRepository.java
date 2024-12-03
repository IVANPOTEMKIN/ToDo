package ru.effective_mobile.todo.repository;

import lombok.Cleanup;
import lombok.RequiredArgsConstructor;
import org.hibernate.SessionFactory;
import org.hibernate.query.criteria.HibernateCriteriaBuilder;
import org.hibernate.query.criteria.JpaPredicate;
import org.hibernate.query.criteria.JpaRoot;
import org.springframework.stereotype.Repository;
import ru.effective_mobile.todo.model.PaginatedResponse;
import ru.effective_mobile.todo.model.Todo;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class HibernateTodoRepository implements TodoRepository {

    private final SessionFactory sessionFactory;

    @Override
    public PaginatedResponse<Todo> findAll(int page, int size) {

        List<Todo> resultList = null;
        long resultCount = 0;

        @Cleanup var session = sessionFactory.openSession();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Todo.class);
        var todo = criteria.from(Todo.class);

        try {
            session.beginTransaction();

            criteria.select(todo)
                    .orderBy(cb.asc(todo.get("id")));

            var query = session.createQuery(criteria);

            resultCount = query.getResultList().size();

            resultList = query.setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();

            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return new PaginatedResponse<>(resultList, resultCount, page, size);
    }

    @Override
    public PaginatedResponse<Todo> findAllByFilters(Title title,
                                                    Status status,
                                                    Importance importance,
                                                    Urgency urgency,
                                                    LocalDate deadline,
                                                    int page, int size) {

        List<Todo> resultList = null;
        long resultCount = 0;

        @Cleanup var session = sessionFactory.openSession();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createQuery(Todo.class);
        var todo = criteria.from(Todo.class);

        var predicate = cb.conjunction();

        predicate = getFilters(title, status, importance, urgency, deadline, predicate, cb, todo);

        try {
            session.beginTransaction();

            criteria.select(todo)
                    .where(predicate)
                    .orderBy(cb.asc(todo.get("deadline")));

            var query = session.createQuery(criteria);

            resultCount = query.getResultList().size();

            resultList = query.setFirstResult((page - 1) * size)
                    .setMaxResults(size)
                    .getResultList();

            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return new PaginatedResponse<>(resultList, resultCount, page, size);
    }

    @Override
    public Optional<Todo> findById(long id) {
        Optional<Todo> result = Optional.empty();

        @Cleanup var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            result = Optional.ofNullable(session.find(Todo.class, id));
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

        return result;
    }

    @Override
    public void save(Todo todo) {
        @Cleanup var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.persist(todo);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

    }

    @Override
    public void update(Todo todo) {
        @Cleanup var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.merge(todo);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

    }

    @Override
    public void delete(Todo todo) {
        @Cleanup var session = sessionFactory.openSession();

        try {
            session.beginTransaction();
            session.remove(todo);
            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

    }

    @Override
    public void deleteAll() {
        @Cleanup var session = sessionFactory.openSession();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createCriteriaDelete(Todo.class);

        try {
            session.beginTransaction();

            criteria.from(Todo.class);
            session.createQuery(criteria).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }

    }

    @Override
    public void deleteAllByFilters(Title title,
                                   Status status) {

        @Cleanup var session = sessionFactory.openSession();

        var cb = session.getCriteriaBuilder();
        var criteria = cb.createCriteriaDelete(Todo.class);
        var todo = criteria.from(Todo.class);

        var predicate = cb.conjunction();

        if (title != null) {
            predicate = cb.and(predicate, cb.equal(todo.get("title"), title));
        }
        if (status != null) {
            predicate = cb.and(predicate, cb.equal(todo.get("status"), status));
        }

        try {
            session.beginTransaction();

            criteria.where(predicate);
            session.createQuery(criteria).executeUpdate();

            session.getTransaction().commit();

        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }

    private static JpaPredicate getFilters(Title title,
                                           Status status,
                                           Importance importance,
                                           Urgency urgency,
                                           LocalDate deadline,
                                           JpaPredicate predicate,
                                           HibernateCriteriaBuilder cb,
                                           JpaRoot<Todo> root) {

        if (title != null) {
            predicate = cb.and(predicate, cb.equal(root.get("title"), title));
        }
        if (status != null) {
            predicate = cb.and(predicate, cb.equal(root.get("status"), status));
        }
        if (importance != null) {
            predicate = cb.and(predicate, cb.equal(root.get("importance"), importance));
        }
        if (urgency != null) {
            predicate = cb.and(predicate, cb.equal(root.get("urgency"), urgency));
        }
        if (deadline != null) {
            predicate = cb.and(predicate, cb.equal(root.get("deadline"), deadline));
        }
        return predicate;
    }
}