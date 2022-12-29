import org.junit.jupiter.api.BeforeEach;
import ru.yandex.practicum.tasktracker.manager.Managers;

class InMemoryTaskManagerTest extends TaskManagerTest {
    @BeforeEach
    void initializationTaskManager() {
        taskManager =  Managers.getDefault();
    }
}