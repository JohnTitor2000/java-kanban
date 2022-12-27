package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.File;
import java.time.LocalDateTime;

class Main {
    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager =FileBackedTasksManager.loadFromFile(new File("resources\\tasks.csv"));
        Epic task = new Epic();
        task.setStartTime(LocalDateTime.now());
        fileBackedTasksManager.addEpic(task);
    }

    private static Task task(int id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}