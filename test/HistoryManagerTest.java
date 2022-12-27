import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.HistoryManager;
import ru.yandex.practicum.tasktracker.manager.InMemoryHistoryManager;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    HistoryManager historyManager;

    @BeforeEach
    void initializationHistoryManager() {
        historyManager = new InMemoryHistoryManager();
    }

    @Test
    void getEmptyHistory() {
        final List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История не пуста");
    }

    @Test
    void getFilledHistory() {
        Task task1 = new Task();
        Task task2 = new Task();
        task1.setId(1);
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        final List<Task> history = historyManager.getHistory();
        assertArrayEquals(new Task[]{task1, task2}, history.toArray(), "Истории не совпадают");
    }

    @Test
    void add() {
        historyManager.add(new Task());
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void remove() {
        Task task1 = new Task();
        task1.setId(1);
        Task task2 = new Task();
        task2.setId(2);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.remove(1);
        final List<Task> history = historyManager.getHistory();
        assertEquals(1, history.size(), "Удаление произошло не верно");
        assertNotNull(history, "История пустая");
        assertArrayEquals(new Task[] {task2}, history.toArray());
    }
}