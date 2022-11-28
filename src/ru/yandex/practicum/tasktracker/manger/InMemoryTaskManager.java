package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryTaskManager implements TaskManager{
    private static int generatorId;
    private final HistoryManager historyManager;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Task> tasks;

    public InMemoryTaskManager() {
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
    }

    @Override
    public List<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public List<Epic> getEpics() {
        return new ArrayList<>(epics.values());
    }

    @Override
    public List<SubTask> getSubTasks() {
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
        for (int id : tasks.keySet()) {
            historyManager.remove(id);
        }
        tasks.clear();
    }

    @Override
    public void removeAllEpics() {
        subTasks.keySet().forEach(id -> historyManager.remove(id));
        epics.keySet().forEach(id -> historyManager.remove(id));
        subTasks.clear();
        epics.clear();
    }

    @Override
    public void removeAllSubTasks() {
        for (int id : subTasks.keySet()) {
            historyManager.remove(id);
        }
        subTasks.clear();
        for (Epic epic : epics.values()) {
            epic.clearSubtaskIds();
            epic.setStatus(Status.NEW);
        }
    }

    @Override
    public Task getTaskById(int id) {
        historyManager.add(tasks.get(id));
        return tasks.get(id);
    }

    @Override
    public Epic getEpicById(int id) {
        historyManager.add(epics.get(id));
        return epics.get(id);
    }

    @Override
    public SubTask getSubTaskById(int id) {
        historyManager.add(subTasks.get(id));
        return subTasks.get(id);
    }

    @Override
    public void removeTaskById(int id) {
        historyManager.remove(id);
        tasks.remove(id);
    }

    @Override
    public void removeEpicById(int id) {
        historyManager.remove(id);
        List<Integer> subTaskIds = epics.get(id).getSubTaskIds();
        for (int subTaskId : subTaskIds) {
            subTasks.remove(subTaskId);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        historyManager.remove(id);
        Epic epic = epics.get(subTasks.get(id).getEpicId());
        epic.removeSubtaskId(id);
        updateEpicStatus(subTasks.get(id).getEpicId());
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        updateEpicStatus(subTask.getEpicId());
    }

    @Override
    public List<SubTask> getSubTasksByEpicId(int epicId) {
        List<SubTask> subTasks = new ArrayList<>();
        for (SubTask subTask : this.subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                subTasks.add(subTask);
            }
        }
        return subTasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    private void updateEpicStatus(int epicId) {
        Epic epic = epics.get(epicId);
        List<Integer> subTaskIds = epic.getSubTaskIds();
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
        } else if (countNew == subTaskIds.size() || subTaskIds.isEmpty()) {
            epic.setStatus(Status.NEW);
        }
        epics.put(epic.getId(), epic);
    }

    private int generateId() {
        return ++generatorId;
    }
}