package ru.effective_mobile.todo.exception;

public class TodoNotFoundException extends RuntimeException {
    public TodoNotFoundException(long id) {
        super("Задача не найдена по идентификатору " + id);
    }
}