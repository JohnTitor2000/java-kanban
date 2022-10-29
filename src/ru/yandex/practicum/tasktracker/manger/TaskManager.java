package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public interface TaskManager {

    public ArrayList<Task> getTasks();

    public ArrayList<Epic> getEpics();

    public ArrayList<SubTask> getSubTasks();

    public void addTask(Task task);

    public void addEpic(Epic epic);

    public void addSubTask(SubTask subTask);

    public void removeAllTasks();

    public void removeAllEpics();

    public void removeAllSubTasks();

    public Task getTaskById(int id);

    public Epic getEpicById(int id);

    public SubTask getSubTaskById(int id);

    public void removeTaskById(int id);

    public void removeEpicById(int id);

    public void removeSubTaskById(int id);

    public void updateTask(Task task);

    public void updateEpic(Epic epic);

    public void updateSubTask(SubTask subTask);

    public ArrayList<SubTask> getSubTasksByEpicId(int epicId);
}