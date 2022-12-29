package ru.yandex.practicum.tasktracker.manager;

public class IntersectionException extends RuntimeException{
    public IntersectionException(String message, Throwable cause) {
        super(message, cause);
    }
}