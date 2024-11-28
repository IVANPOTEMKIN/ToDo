package ru.effective_mobile.todo.specification;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import ru.effective_mobile.todo.model.Todo;

import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class TodoSpecification implements Specification<Todo> {

    private final TodoFilter filter;

    @Override
    public Predicate toPredicate(Root<Todo> root,
                                 CriteriaQuery<?> query,
                                 CriteriaBuilder criteriaBuilder) {

        List<Predicate> predicates = new ArrayList<>();

        if (filter.title() != null) {
            predicates.add(criteriaBuilder.equal(root.get("title"), filter.title()));
        }
        if (filter.status() != null) {
            predicates.add(criteriaBuilder.equal(root.get("status"), filter.status()));
        }
        if (filter.importance() != null) {
            predicates.add(criteriaBuilder.equal(root.get("importance"), filter.importance()));
        }
        if (filter.urgency() != null) {
            predicates.add(criteriaBuilder.equal(root.get("urgency"), filter.urgency()));
        }
        if (filter.deadline() != null) {
            predicates.add(criteriaBuilder.equal(root.get("deadline"), filter.deadline()));
        }

        return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    }
}