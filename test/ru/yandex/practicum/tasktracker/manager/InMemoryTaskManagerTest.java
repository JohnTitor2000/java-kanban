package ru.yandex.practicum.tasktracker.manager;

import org.junit.jupiter.api.BeforeEach;

class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    void initializationTaskManager() {
        taskManager =  new InMemoryTaskManager();
    }
}