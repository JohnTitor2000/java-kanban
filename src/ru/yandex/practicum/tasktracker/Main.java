package ru.yandex.practicum.tasktracker;

import ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager;
import ru.yandex.practicum.tasktracker.model.Task;
import java.io.File;
import static ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager.loadFromFile;

class Main {
    public static void main(String[] args) {
        int[] nums = {1, 2, 3, 4, 5};
        int length = nums.length;
    }

    private static Task task(int id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}