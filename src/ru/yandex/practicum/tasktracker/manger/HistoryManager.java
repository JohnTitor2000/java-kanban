package ru.yandex.practicum.tasktracker.manger;
import ru.yandex.practicum.tasktracker.model.Task;
import java.util.List;

public interface HistoryManager {

    List<Task> getHistory();

    void add(Task task);

    void remove(long id);
}
