package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.Status;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;

public class TaskManager {
    static int generatorId;

    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;


    public TaskManager() {
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
    }

    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    public void addEpicTask(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(generateId());
        epics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    public void removeAllTasks() {
        tasks.clear();
    }

     public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void removeAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.getSubTaskIds().clear();
        }
    }

    public int generateId() {
        generatorId++;
        return generatorId;
    }

    public Task getTaskById(int id) {
        return tasks.get(id);
    }

    public Epic getEpicById(int id) {
        return epics.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTasks.get(id);
    }

    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    public void removeEpicById(int id) {
        ArrayList<Integer> subTaskIds = epics.get(id).getSubTaskIds();
        for (int subTaskId : subTaskIds) {
            subTasks.remove(subTaskId);
        }
        epics.remove(id);
    }

    public void removeSubTaskById(int id) {
        ArrayList<Integer> subTaskIdsList = epics.get(subTasks.get(id).getEpicId()).getSubTaskIds();
        subTaskIdsList.remove(subTaskIdsList.indexOf(id));
        subTasks.remove(id);
        updateEpicStatus(subTasks.get(id).getEpicId());
    }

    public void updateTask(Task task, int id) {
        task.setId(id);
        tasks.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        ArrayList<Integer> subTasksIdList = epics.get(epic.getId()).getSubTaskIds();
        epic.setSubTaskIds(subTasksIdList);
        epics.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask, int id) {
        subTask.setId(id);
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subTaskIdsList = epic.getSubTaskIds();
        int countDone = 0;
        int countInProgress = 0;
        int countNew = 0;
        for (int subTaskId : subTaskIdsList) {
            Status status = subTasks.get(subTaskId).getStatus();
            switch (status) {
                case DONE:
                    countDone++;
                    break;
                case IN_PROGRESS:
                    countInProgress++;
                    break;
                case NEW:
                    countNew++;
                    break;
            }
        }
        if (countDone == subTaskIdsList.size()) {
            epic.setStatus(Status.DONE);
        } else if (countInProgress > 0 && countDone < subTaskIdsList.size() || countDone > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (countNew == subTaskIdsList.size() || subTaskIdsList.size() == 0) {
            epic.setStatus(Status.NEW);
        }
        epics.put(epic.getId(), epic);
    }

    public ArrayList<SubTask> getSubTasksByEpicId(int epicId) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for(SubTask subTask : this.subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                subTasks.add(subTask);
            }
        }
        return subTasks;
    }
}