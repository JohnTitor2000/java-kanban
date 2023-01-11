package ru.yandex.practicum.tasktracker.manager;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager.loadFromFile;

class FileBackedTasksManagerTest extends TaskManagerTest {

    @BeforeEach
    void initializationTaskManager() throws InterruptedException {
        taskManager = loadFromFile(new File("resources/tasksTest.csv"));
    }

    @AfterEach
    void clearingTestFile() throws IOException {
        Files.write(Path.of("resources/tasksTest.csv"), new byte[] {});
    }

    @Test
    void loadFromFile_shouldInitializeCorrectFileBackedTasksManager() throws IOException, InterruptedException {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("resources/loadFromFileTest.csv"));
        Task task = createTask("Task1", "Description task1");
        Epic epic = createEpic("Epic2", "Description epic2", new int[] {3});
        SubTask subTask = createSubTask("Sub Task2", "Description sub task3", Status.DONE, 2);
        task.setId(1);
        epic.setId(2);
        subTask.setId(3);
        epic.setStatus(Status.DONE);
        assertEquals(task, fileBackedTasksManager.getTaskById(1), "Таск не соответствует заданому");
        assertEquals(epic, fileBackedTasksManager.getEpicById(2), "Эпик не соответствует заданому");
        assertEquals(subTask, fileBackedTasksManager.getSubTaskById(3));
    }

    @Test
    void save_shouldWriteFileBackedTasksManagerDataInCvsFile() throws IOException, InterruptedException {
        Files.write(Path.of("resources/saveTest.csv"), new byte[] {});
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("resources/saveTest.csv"));
        Task task = createTask("Task1", "Description task1");
        Epic epic = createEpic("Epic2", "Description epic2", new int[] {3});
        SubTask subTask = createSubTask("Sub Task2", "Description sub task3", Status.DONE, 2);
        epic.setStatus(Status.DONE);
        fileBackedTasksManager.addTask(task);
        fileBackedTasksManager.addEpic(epic);
        fileBackedTasksManager.addSubTask(subTask);
        fileBackedTasksManager.getEpicById(2);
        fileBackedTasksManager.getSubTaskById(3);
        try (BufferedReader readerActual= new BufferedReader(new FileReader(new File("resources/expectedSaveTest.cvs"), StandardCharsets.UTF_8));
                BufferedReader readerExpected = new BufferedReader(new FileReader(new File("resources/saveTest.csv"), StandardCharsets.UTF_8))) {
            while (readerExpected.ready()) {
                assertEquals(readerExpected.readLine(), readerActual.readLine(), "Строчки не совпадают");
            }
        }
        Files.write(Path.of("resources/saveTest.csv"), new byte[] {});
    }
}