package ru.yandex.practicum.tasktracker.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.tasktracker.server.KVServer;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static ru.yandex.practicum.tasktracker.manager.HttpTaskManager.load;

class HttpTaskManagerTest extends TaskManagerTest{

    HttpTaskManagerTest() throws IOException {
    }

    KVServer kvServer = new KVServer();

    @BeforeEach
    void initializationTaskManager() throws InterruptedException, IOException {
        kvServer.start();
        taskManager = load("http://localhost:8078/register");
    }

    @AfterEach
    void disablingKVServer() throws IOException {
        kvServer.stop();
    }
}