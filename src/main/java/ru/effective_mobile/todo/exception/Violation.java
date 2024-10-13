package ru.effective_mobile.todo.exception;

import java.time.LocalDateTime;

public record Violation(LocalDateTime timestamp,
                        String message) {
}