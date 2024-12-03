INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Description', 'NEW', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '1 week');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'OTHER', 'Description', 'IN_PROGRESS', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '3 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'HOME', 'Description', 'NEW', 'UNIMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Description', 'IN_PROGRESS', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '12 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'WORK', 'Description', 'COMPLETED', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'FRIENDS', 'Description', 'NEW', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '1 week');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Description', 'COMPLETED', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'STUDY', 'Description', 'NEW', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'WORK', 'Description', 'IN_PROGRESS', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '8 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'FRIENDS', 'Description', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '10 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'OTHER', 'Description', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '8 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'HOME', 'Description', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '11 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'WORK', 'Description', 'NEW', 'UNIMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '1 week');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'FAMILY', 'Description', 'NEW', 'IMPORTANT', 'NON_URGENT',
        CURRENT_DATE + INTERVAL '4 days');

INSERT INTO todo(created_at, title, description, status, importance, urgency, deadline)
    OVERRIDING SYSTEM VALUE
VALUES (CURRENT_DATE, 'OTHER', 'Description', 'COMPLETED', 'IMPORTANT', 'URGENT',
        CURRENT_DATE + INTERVAL '11 days');