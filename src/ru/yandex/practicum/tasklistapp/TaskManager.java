package ru.yandex.practicum.tasklistapp;

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

    public ArrayList<Task> getTaskList() {
        ArrayList<Task> task = (ArrayList<Task>) tasks.values();
        return task;
    }

    public ArrayList<Epic> getEpicList() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (int id : epics.keySet()) {
            epicList.add(epics.get(id));
        }
        return epicList;
    }

    public ArrayList<SubTask> getSubTaskList() {
        ArrayList<SubTask> subTaskList = new ArrayList<>();
        for (int id : subTasks.keySet()) {
            subTaskList.add(subTasks.get(id));
        }
        return subTaskList;
    }

    public void addTask(Task task) {
        task.setId(idGenerator());
        tasks.put(task.getId(), task);
    }

    public void addEpicTask(Epic epic) {
        epic.setId(idGenerator());
        epics.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(idGenerator());
        epics.get(subTask.getEpicId()).addSubTaskId(subTask.getId());
        subTasks.put(subTask.getId(), subTask);
    }

    public void removeAllTask() {
        tasks.clear();
    }

     public void removeAllEpics() {
        subTasks.clear();
        epics.clear();
    }

    public void removeAllSubTask() {
        subTasks.clear();
        ArrayList<Epic> epicsToRemoveSubTasks = (ArrayList<Epic>) epics.values();
        for (Epic epic : epicsToRemoveSubTasks) {
            epic.getSubTaskIds().clear();
        }
    }

    public int idGenerator() {
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

    public void removeSubTuskById(int id) {
        ArrayList<Integer> subTaskIdsList = epics.get(subTasks.get(id).getEpicId()).getSubTaskIds();
        subTaskIdsList.remove(subTaskIdsList.indexOf(id));
        subTasks.remove(id);
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
        Epic epic = epics.get(subTask.getEpicId());
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

    public ArrayList<SubTask> getSubTaskByEpic (int epicId) {
        ArrayList<SubTask> subTasksForReturn = new ArrayList<>();
        for(SubTask subTask : subTasks.values()) {
            if (subTask.getEpicId() == epicId) {
                subTasksForReturn.add(subTask);
            }
        }
        return subTasksForReturn;
    }
}