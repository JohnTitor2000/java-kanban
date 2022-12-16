package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.ManagerSaveException;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.List;

public interface TaskManager {

    List<Task> getTasks();

    List<Epic> getEpics();

    List<SubTask> getSubTasks();

    void addTask(Task task) throws ManagerSaveException;

    void addEpic(Epic epic) throws ManagerSaveException;

    void addSubTask(SubTask subTask) throws ManagerSaveException;

    void removeAllTasks() throws ManagerSaveException;

    void removeAllEpics() throws ManagerSaveException;

    void removeAllSubTasks() throws ManagerSaveException;

    Task getTaskById(int id) throws ManagerSaveException;

    Epic getEpicById(int id) throws ManagerSaveException;

    SubTask getSubTaskById(int id) throws ManagerSaveException;

    void removeTaskById(int id) throws ManagerSaveException;

    void removeEpicById(int id) throws ManagerSaveException;

    void removeSubTaskById(int id) throws ManagerSaveException;

    void updateTask(Task task) throws ManagerSaveException;

    void updateEpic(Epic epic) throws ManagerSaveException;

    void updateSubTask(SubTask subTask) throws ManagerSaveException;

    List<SubTask> getSubTasksByEpicId(int epicId) throws ManagerSaveException;

    List<Task> getHistory();
}