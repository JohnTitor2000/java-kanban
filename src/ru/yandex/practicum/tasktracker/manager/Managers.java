package ru.yandex.practicum.tasktracker.manager;

import java.io.File;
import java.io.IOException;

public class Managers {

    public static HttpTaskManager getDefault() throws InterruptedException, IOException {
        return HttpTaskManager.load("http://localhost:8078/register");
    }

    public static HistoryManager getDefaultHistory() {
        return new InMemoryHistoryManager();
    }
}
