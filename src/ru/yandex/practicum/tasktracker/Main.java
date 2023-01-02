package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager;
import ru.yandex.practicum.tasktracker.model.Task;
import java.io.File;
import static ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager.loadFromFile;

class Main {
    public static void main(String[] args) {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("resources\\tasks.csv"));
        System.out.println(fileBackedTasksManager.getTaskById(1));
    }

    private static Task task(int id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}