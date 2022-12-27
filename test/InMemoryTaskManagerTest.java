import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.InMemoryTaskManager;
import ru.yandex.practicum.tasktracker.manager.Managers;

class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    void initializationTaskManager() {
        taskManager = (InMemoryTaskManager) Managers.getDefault();
    }
}