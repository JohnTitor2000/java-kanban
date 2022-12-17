package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.model.Task;

class Main {
    public static void main(String[] args) {
    }

    private static Task task(int id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}