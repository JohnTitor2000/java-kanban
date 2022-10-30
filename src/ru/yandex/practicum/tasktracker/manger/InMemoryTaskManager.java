package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class InMemoryTaskManager implements TaskManager{
    static int generatorId;
    private static HistoryManager historyManager = new Managers().getDefaoultHistory();
    private static Queue<Task> history = new LinkedList<>();
    private final HashMap<Integer, SubTask> subTasks;
    private final HashMap<Integer, Epic> epics;
    private final HashMap<Integer, Task> tasks;

    public InMemoryTaskManager() {
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }

    @Override
    public void addTask(Task task) {
        task.setId(generateId());
        tasks.put(task.getId(), task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        subTask.setId(generateId());
        epics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    @Override
    public void removeAllTasks() {
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            epic.setStatus(Status.NEW);
        }
    }

    public int generateId() {
        generatorId++;
        return generatorId;
    }

    @Override
    public Task getTaskById(int id) {
        addNewHistoryElement(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        addNewHistoryElement(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        addNewHistoryElement(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void removeTaskById(int id) {
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        ArrayList<Integer> subTaskIds = epics.get(id).getSubTaskIds();
        for (int subTaskId : subTaskIds) {
            subTasks.remove(subTaskId);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        ArrayList<Integer> subTaskIds    = epics.get(subTasks.get(id).getEpicId()).getSubTaskIds();
        subTaskIds.remove(subTaskIds.indexOf(id));
        subTasks.remove(id);
        updateEpicStatus(subTasks.get(id).getEpicId());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        ArrayList<Integer> subTasksIds = epics.get(epic.getId()).getSubTaskIds();
        epic.setSubTaskIds(subTasksIds);
        epics.put(epic.getId(), epic);
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    @Override
    public ArrayList<SubTask> getSubTasksByEpicId(int epicId) {
        ArrayList<SubTask> subTasks = new ArrayList<>();
        for(SubTask subTask : this.subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                subTasks.add(subTask);
            }
        }
        return subTasks;
    }

    public Queue<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        ArrayList<Integer> subTaskIds = epic.getSubTaskIds();
        int countDone = 0;
        int countInProgress = 0;
        int countNew = 0;
        for (int subTaskId : subTaskIds) {
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
        if (countDone == subTaskIds.size()) {
            epic.setStatus(Status.DONE);
        } else if (countInProgress > 0 && countDone < subTaskIds.size() || countDone > 0) {
            epic.setStatus(Status.IN_PROGRESS);
        } else if (countNew == subTaskIds.size() || subTaskIds.size() == 0) {
            epic.setStatus(Status.NEW);
        }
        epics.put(epic.getId(), epic);
    }

    private static void addNewHistoryElement(Task task) {
        historyManager.add(task);
    }
}