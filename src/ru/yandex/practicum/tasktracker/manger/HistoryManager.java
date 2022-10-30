package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.Queue;

public interface HistoryManager {
    public void add(Task task);

    public Queue<Task> getHistory();
}
