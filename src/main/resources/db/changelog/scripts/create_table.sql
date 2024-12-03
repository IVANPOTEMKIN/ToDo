-- liquibase formatted sql

-- changeset ivan:1
CREATE TABLE IF NOT EXISTS todo
(
    id          BIGSERIAL PRIMARY KEY,
    created_at  DATE         NOT NULL,
    title       VARCHAR(255) CHECK (title IN ('STUDY',
                                              'WORK',
                                              'HOME',
                                              'FAMILY',
                                              'FRIENDS',
                                              'OTHER')),
    description VARCHAR(255) NOT NULL,
    status      VARCHAR(255) CHECK (status IN ('NEW',
                                               'IN_PROGRESS',
                                               'COMPLETED',
                                               'CANCELLED')),
    importance  VARCHAR(255) CHECK (importance IN ('IMPORTANT',
                                                   'UNIMPORTANT')),
    urgency     VARCHAR(255) CHECK (urgency IN ('URGENT',
                                                'NON_URGENT')),
    deadline    DATE
);