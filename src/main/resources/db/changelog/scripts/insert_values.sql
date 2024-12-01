-- liquibase formatted sql

-- changeset ivan:1
INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Выполнить задачу', 'NEW', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '1 week');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'OTHER', 'Выполнить задачу', 'IN_PROGRESS', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '3 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'HOME', 'Выполнить задачу', 'NEW', 'UNIMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Выполнить задачу', 'IN_PROGRESS', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '12 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'WORK', 'Выполнить задачу', 'COMPLETED', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'FRIENDS', 'Выполнить задачу', 'NEW', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '1 week');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Выполнить задачу', 'COMPLETED', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Выполнить задачу', 'NEW', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'WORK', 'Выполнить задачу', 'IN_PROGRESS', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '8 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'FRIENDS', 'Выполнить задачу', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'OTHER', 'Выполнить задачу', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '8 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'HOME', 'Выполнить задачу', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '11 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'WORK', 'Выполнить задачу', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '1 week');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'FAMILY', 'Выполнить задачу', 'NEW', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'OTHER', 'Выполнить задачу', 'COMPLETED', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '11 days');