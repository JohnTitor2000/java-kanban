package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.LinkedList;
import java.util.List;

public class InMemoryHistoryManager implements HistoryManager{

    private static final int HISTORY_SIZE = 10;
    private List<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if (history.size() >= HISTORY_SIZE) {
            history.remove(0);
        }
        history.add(history.size(), task);
    }

    @Override
    public List<Task> getHistory() {
        return history;
    }
}
