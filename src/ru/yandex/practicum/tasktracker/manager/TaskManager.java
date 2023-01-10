package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public interface TaskManager {

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    void addTask(Task task) throws IOException, InterruptedException;

    void addEpic(Epic epic) throws IOException, InterruptedException;

    void addSubTask(SubTask subTask) throws IOException, InterruptedException;

    void removeAllTasks() throws IOException, InterruptedException;

    void removeAllEpics() throws IOException, InterruptedException;

    void removeAllSubTasks() throws IOException, InterruptedException;

    Task getTaskById(int id) throws IOException, InterruptedException;

    Epic getEpicById(int id) throws IOException, InterruptedException;

    SubTask getSubTaskById(int id) throws IOException, InterruptedException;

    void removeTaskById(int id) throws IOException, InterruptedException;

    void removeEpicById(int id) throws IOException, InterruptedException;

    void removeSubTaskById(int id) throws IOException, InterruptedException;

    void updateTask(Task task) throws IOException, InterruptedException;

    void updateEpic(Epic epic) throws IOException, InterruptedException;

    void updateSubTask(SubTask subTask) throws IOException, InterruptedException;

    List<SubTask> getSubTasksByEpicId(int epicId);

    List<Task> getHistory();

    TreeSet<Task> getPrioritizedTasks();

    Map<Integer, Task> getTasksWithIds();

    Map<Integer, Epic> getEpicsWithIds();

    Map<Integer, SubTask> getSubTasksWithIds();
}