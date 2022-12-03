package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.model.Task;

class Main {
    public static void main(String[] args) {
        HistoryManager historyManager = Managers.getDefaultHistory();
        historyManager.add(task(1));
        historyManager.add(task(2));
        historyManager.add(task(3));
        historyManager.add(task(4));
        historyManager.add(task(5));

        historyManager.getHistory().forEach(System.out::println);
        System.out.println();

        historyManager.remove(3);
        historyManager.getHistory().forEach(System.out::println);
        System.out.println();

        historyManager.remove(5);
        historyManager.getHistory().forEach(System.out::println);
        System.out.println();

        historyManager.remove(1);
        historyManager.getHistory().forEach(System.out::println);
        System.out.println();
    }

    private static Task task(int id) {
        Task task = new Task();
        task.setId(id);
        return task;
    }
}