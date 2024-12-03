package ru.effective_mobile.todo.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import ru.effective_mobile.todo.model.enums.Importance;
import ru.effective_mobile.todo.model.enums.Status;
import ru.effective_mobile.todo.model.enums.Title;
import ru.effective_mobile.todo.model.enums.Urgency;

import java.time.LocalDate;

@Data
@EqualsAndHashCode(of = "id")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity(name = "todo")
public class Todo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @CreationTimestamp
    @Temporal(TemporalType.DATE)
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Enumerated(EnumType.STRING)
    private Title title;

    @Column(nullable = false)
    private String description;

    @Enumerated(EnumType.STRING)
    private Status status;

    @Enumerated(EnumType.STRING)
    private Importance importance;

    @Enumerated(EnumType.STRING)
    private Urgency urgency;

    @Temporal(TemporalType.DATE)
    private LocalDate deadline;

    @PrePersist
    public void initializingTitleDefault() {
        title = title == null ? Title.OTHER : title;
        status = status == null ? Status.NEW : status;
        importance = importance == null ? Importance.UNIMPORTANT : importance;
        urgency = urgency == null ? Urgency.NON_URGENT : urgency;
        deadline = deadline == null ? LocalDate.now().plusDays(7) : deadline;
    }
}