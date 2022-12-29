import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager;
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
import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager.loadFromFile;

class FileBackedTasksManagerTest extends TaskManagerTest {

    @BeforeEach
    void initializationTaskManager() {
        taskManager = loadFromFile(new File("resources/tasksTest.csv"));
    }

    @AfterEach
    void clearingTestFile() throws IOException {
        Files.write(Path.of("resources/tasksTest.csv"), new byte[] {});
    }

    @Test
    void loadFromFile_shouldInitializeCorrectFileBackedTasksManager() {
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("resources/loadFromFileTest.csv"));
        Task task = new Task();
        Epic epic = new Epic();
        SubTask subTask = new SubTask();
        subTask.setEpicId(2);
        task.setId(1);
        task.setTitle("Task1");
        task.setDescription("Description task1");
        epic.setId(2);
        epic.setTitle("Epic2");
        epic.setDescription("Description epic2");
        epic.addSubTaskId(3);
        subTask.setId(3);
        subTask.setStatus(Status.DONE);
        subTask.setTitle("Sub Task2");
        subTask.setDescription("Description sub task3");
        epic.setStatus(Status.DONE);
        assertEquals(task, fileBackedTasksManager.getTaskById(1), "Таск не соответствует заданому");
        assertEquals(epic, fileBackedTasksManager.getEpicById(2), "Эпик не соответствует заданому");
        assertEquals(subTask, fileBackedTasksManager.getSubTaskById(3));
    }

    @Test
    void save_shouldWriteFileBackedTasksManagerDataInCvsFile() throws IOException {
        Files.write(Path.of("resources/saveTest.csv"), new byte[] {});
        FileBackedTasksManager fileBackedTasksManager = loadFromFile(new File("resources/saveTest.csv"));
        Task task = new Task();
        Epic epic = new Epic();
        SubTask subTask = new SubTask();
        subTask.setEpicId(2);
        task.setTitle("Task1");
        task.setDescription("Description task1");
        epic.setTitle("Epic2");
        epic.setDescription("Description epic2");
        epic.addSubTaskId(3);
        subTask.setStatus(Status.DONE);
        subTask.setTitle("Sub Task2");
        subTask.setDescription("Description sub task3");
        epic.setStatus(Status.DONE);
        fileBackedTasksManager.addTask(task);
        fileBackedTasksManager.addEpic(epic);
        fileBackedTasksManager.addSubTask(subTask);
        fileBackedTasksManager.getEpicById(2);
        fileBackedTasksManager.getSubTaskById(3);

        try (BufferedReader readerActual= new BufferedReader(new FileReader(new File("resources/expectedSaveTest.cvs"), StandardCharsets.UTF_8));
                BufferedReader readerExpected = new BufferedReader(new FileReader(new File("resources/saveTest.csv"), StandardCharsets.UTF_8))){
            while (readerActual.ready() && readerExpected.ready()) {
                assertEquals(readerExpected.readLine(), readerActual.readLine(), "Строчки не совпадают");
            }
        }
        Files.write(Path.of("resources/saveTest.csv"), new byte[] {});
    }

}