package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

public class InMemoryTaskManager implements TaskManager {
    protected static int generatorId;
    private final HistoryManager historyManager;
    private final Map<Integer, SubTask> subTasks;
    private final Map<Integer, Epic> epics;
    private final Map<Integer, Task> tasks;
    private final TreeSet<Task> prioritizedTasks;

    public InMemoryTaskManager() {
        Comparator<Task> taskDateComparator = (o1, o2) -> {
            if (o1.getStartTime() == null) {
                return -1;
            } else if (o2.getStartTime() == null) {
                return 1;
            } else {
                return o1.getStartTime().compareTo(o2.getStartTime());
            }
        };
        prioritizedTasks = new TreeSet<>(taskDateComparator.thenComparing(Task::getId));
        subTasks = new HashMap<>();
        epics = new HashMap<>();
        tasks = new HashMap<>();
        historyManager = Managers.getDefaultHistory();
        generatorId = 0;
    }

    public TreeSet<Task> getPrioritizedTasks() {
        return prioritizedTasks;
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
        checkIntersections(task);
        task.setId(generateId());
        tasks.put(task.getId(), task);
        prioritizedTasks.add(task);
    }

    @Override
    public void addEpic(Epic epic) {
        epic.setId(generateId());
        epics.put(epic.getId(), epic);
    }

    @Override
    public void addSubTask(SubTask subTask) {
        checkIntersections(subTask);
        subTask.setId(generateId());
        epics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
        updateEpic(epics.get(subTask.getEpicId()));
        prioritizedTasks.add(subTask);
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
        subTasks.keySet().forEach(historyManager::remove);
        epics.keySet().forEach(historyManager::remove);
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
        if (epics.get(id) == null) {
            return;
        }
        List<Integer> subTaskIds = epics.get(id).getSubTaskIds();
        for (int subTaskId : subTaskIds) {
            subTasks.remove(subTaskId);
            historyManager.remove(subTaskId);
        }
        epics.remove(id);
    }

    @Override
    public void removeSubTaskById(int id) {
        historyManager.remove(id);
        if (subTasks.get(id) == null) {
            return;
        }
        Epic epic = epics.get(subTasks.get(id).getEpicId());
        epic.removeSubtaskId(id);
        updateEpic(epics.get(subTasks.get(id).getEpicId()));
        subTasks.remove(id);
    }

    @Override
    public void updateTask(Task task) {
        tasks.put(task.getId(), task);
    }

    @Override
    public void updateEpic(Epic epic) {
        epics.put(epic.getId(), epic);
        updateEpicStatus(epic.getId());
        updateEpicStartTime(epic.getId());
        updateEpicEndTime(epic.getId());
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        subTasks.put(subTask.getId(), subTask);
        updateEpic(epics.get(subTask.getEpicId()));
    }

    @Override
    public List<SubTask> getSubTasksByEpicId(int epicId) {
        List<SubTask> subTasks = new ArrayList<>();
        Epic epic = epics.get(epicId);
        for (int subTaskId : epic.getSubTaskIds()) {
            subTasks.add(this.subTasks.get(subTaskId));
        }
        return subTasks;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    protected HistoryManager getHistoryManager() {
        return historyManager;
    }

    protected Map<Integer, Task> getTasksWithIds() {
        return tasks;
    }

    protected Map<Integer, Epic> getEpicsWithIds() {
        return epics;
    }

    protected Map<Integer, SubTask> getSubTasksWithIds() {
        return subTasks;
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
    }

    private int generateId() {
        return ++generatorId;
    }

    private void checkIntersections(Task newTask) {
        LocalDateTime startTime = newTask.getStartTime();
        if (startTime == null || startTime == LocalDateTime.MIN) {
            return;
        }
        TreeSet<Task> tasks = getPrioritizedTasks();
        for (Task task : tasks) {
            if (task.getStartTime() == null) {
                continue;
            }
            if ((startTime.isBefore(task.getEndTime()) && startTime.isAfter(task.getStartTime())) ||
                    newTask.getEndTime().isBefore(task.getEndTime()) && newTask.getEndTime().isAfter(task.getStartTime())) {
                throw new IntersectionException("The task execution time intersects with other tasks.");
            }
        }
    }

    private void updateEpicStartTime(int id) {
        Epic epic = epics.get(id);
        LocalDateTime startTime = LocalDateTime.MAX;
        Map<Integer, SubTask> subTasks = getSubTasksWithIds();
        for (Integer subTaskId : epic.getSubTaskIds()) {
            if (subTasks.get(subTaskId).getStartTime() != null) {
                if (subTasks.get(subTaskId).getStartTime().isBefore(startTime)) {
                    startTime = subTasks.get(subTaskId).getStartTime();
                }
            }
        }
        if (startTime == LocalDateTime.MAX) {
            return;
        }
        epic.setStartTime(startTime);
    }

    private void updateEpicEndTime(Integer id) {
        Epic epic = epics.get(id);
        LocalDateTime endTime = LocalDateTime.MIN;
        InMemoryTaskManager inMemoryTaskManager = this;
        Map<Integer, SubTask> subTasks = inMemoryTaskManager.getSubTasksWithIds();
        for (Integer subTaskId : epic.getSubTaskIds()) {
            if (subTasks.get(subTaskId).getEndTime() != null) {
                if (subTasks.get(subTaskId).getEndTime().isAfter(endTime)) {
                    endTime = subTasks.get(subTaskId).getEndTime();
                }
            }
        }
        if (endTime == LocalDateTime.MIN) {
            return;
        }
        epic.setEndTime(endTime);
    }
}