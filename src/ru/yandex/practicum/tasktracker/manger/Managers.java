package ru.yandex.practicum.tasktracker.manger;

public class Managers {
    public TaskManager getDefault() {
        return new InMemoryTaskManager();
    }

    public static HistoryManager getDefaoultHistory() {
        return new InMemoryHistoryManager();
    }
}
