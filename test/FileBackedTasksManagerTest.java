import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.manager.Managers;

import java.io.File;
import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.tasktracker.manager.FileBackedTasksManager.loadFromFile;

class FileBackedTasksManagerTest  extends TaskManagerTest{

    @BeforeEach
    void initializationTaskManager() {
        taskManager = loadFromFile(new File("resources/tasksTest.csv"));
    }

    @AfterEach
    void clearingTestFile() {
        File file = new File("resources/tasksTest.csv");
        file.delete();
        try {
            file.createNewFile();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}