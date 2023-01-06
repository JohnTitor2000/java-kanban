package ru.yandex.practicum.tasktracker.manager;

import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.model.Task;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;

class HistoryManagerTest {

    private HistoryManager historyManager = new InMemoryHistoryManager();

    @Test
    void getHistory_shouldReturnEmptyList_ifHistoryEmpty() {
        final List<Task> history = historyManager.getHistory();
        assertEquals(0, history.size(), "История не пуста");
    }

    @Test
    void getHistory_shouldReturnHistoryList_ifHistoryNotEmpty() {
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
    void add_shouldAddNewTaskToHistory() {
        historyManager.add(new Task());
        final List<Task> history = historyManager.getHistory();
        assertNotNull(history, "История не пустая.");
        assertEquals(1, history.size(), "История не пустая.");
    }

    @Test
    void remove_shouldRemoveTaskFromMiddleOfHistory() {
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(2);
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Удаление произошло не верно");
        assertNotNull(history, "История пустая");
        assertArrayEquals(new Task[] {task1, task3}, history.toArray());
    }

    @Test
    void remove_shouldRemoveFirstTaskFromHistory() {
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(1);
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Удаление произошло не верно");
        assertNotNull(history, "История пустая");
        assertArrayEquals(new Task[] {task2, task3}, history.toArray());
    }

    @Test
    void remove_shouldRemoveLastTaskFromHistory() {
        Task task1 = new Task();
        Task task2 = new Task();
        Task task3 = new Task();
        task1.setId(1);
        task2.setId(2);
        task3.setId(3);
        historyManager.add(task1);
        historyManager.add(task2);
        historyManager.add(task3);
        historyManager.remove(3);
        final List<Task> history = historyManager.getHistory();
        assertEquals(2, history.size(), "Удаление произошло не верно");
        assertNotNull(history, "История пустая");
        assertArrayEquals(new Task[] {task1, task2}, history.toArray());
    }
}