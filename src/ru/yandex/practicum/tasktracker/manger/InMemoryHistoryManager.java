package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.LinkedList;
import java.util.Queue;

public class InMemoryHistoryManager implements HistoryManager{

    private static Queue<Task> history = new LinkedList<>();

    @Override
    public void add(Task task) {
        if(history.size() > 10) {
            history.poll();
        }
        history.add(task);
    }

    @Override
    public Queue<Task> getHistory() {
        return history;
    }
}
