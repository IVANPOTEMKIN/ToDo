-- liquibase formatted sql

-- changeset ivan:1
INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'STUDY', 'Выполнить задачу', DEFAULT, 'IMPORTANT', 'URGENT', DEFAULT);

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, DEFAULT, 'Выполнить задачу', 'IN_PROGRESS', 'IMPORTANT', 'URGENT', CURRENT_DATE + INTERVAL '3 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'HOME', 'Выполнить задачу', DEFAULT, DEFAULT, 'URGENT', CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'STUDY', 'Выполнить задачу', 'IN_PROGRESS', 'IMPORTANT', 'URGENT', CURRENT_DATE + INTERVAL '12 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'WORK', 'Выполнить задачу', 'COMPLETED', 'IMPORTANT', DEFAULT, CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'FRIENDS', 'Выполнить задачу', DEFAULT, 'IMPORTANT', DEFAULT, DEFAULT);

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'STUDY', 'Выполнить задачу', 'COMPLETED', 'IMPORTANT', DEFAULT, CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'STUDY', 'Выполнить задачу', DEFAULT, 'IMPORTANT', DEFAULT, CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'WORK', 'Выполнить задачу', 'IN_PROGRESS', 'IMPORTANT', 'URGENT', CURRENT_DATE + INTERVAL '8 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'FRIENDS', 'Выполнить задачу', DEFAULT, DEFAULT, DEFAULT, CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, DEFAULT, 'Выполнить задачу', DEFAULT, DEFAULT, DEFAULT, CURRENT_DATE + INTERVAL '8 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'HOME', 'Выполнить задачу', DEFAULT, DEFAULT, DEFAULT, CURRENT_DATE + INTERVAL '11 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'WORK', 'Выполнить задачу', DEFAULT, DEFAULT, DEFAULT, DEFAULT);

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, 'FAMILY', 'Выполнить задачу', DEFAULT, 'IMPORTANT', DEFAULT, CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (DEFAULT, DEFAULT, 'Выполнить задачу', 'COMPLETED', 'IMPORTANT', 'URGENT', CURRENT_DATE + INTERVAL '11 days');