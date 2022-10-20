import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    static int generatorId;

    private HashMap<Integer, SubTask> subTaskMap;
    private HashMap<Integer, Epic> epicsMap;
    private HashMap<Integer, Task> taskMap;


    public Manager() {
        this.generatorId = 0;
        subTaskMap = new HashMap<>();
        epicsMap = new HashMap<>();
        taskMap = new HashMap<>();
    }

    public ArrayList<Task> getTaskList() {
        ArrayList<Task> taskList = new ArrayList<>();
        for (int id : taskMap.keySet()) {
            taskList.add(taskMap.get(id));
        }
        return taskList;
    }

    public ArrayList<Epic> getEpicList() {
        ArrayList<Epic> epicList = new ArrayList<>();
        for (int id : epicsMap.keySet()) {
            epicList.add(epicsMap.get(id));
        }
        return epicList;
    }

    public ArrayList<SubTask> getSubTaskList() {
        ArrayList<SubTask> subTaskList = new ArrayList<>();
        for (int id : subTaskMap.keySet()) {
            subTaskList.add(subTaskMap.get(id));
        }
        return subTaskList;
    }

    public void addTask(Task task) {
        task.setId(idGenerator());
        taskMap.put(task.getId(), task);
    }

    public void addEpicTask(Epic epic) {
        epic.setId(idGenerator());
        epicsMap.put(epic.getId(), epic);
    }

    public void addSubTask(SubTask subTask) {
        subTask.setId(idGenerator());
        epicsMap.get(subTask.getEpicId()).addSubTask(subTask.getId());
        subTaskMap.put(subTask.getId(), subTask);
    }

    public void removeAllTask() {
        taskMap.clear();
    }

     public void removeAllEpics() {
        subTaskMap.clear();
        epicsMap.clear();
    }

    public void removeAllSubTask(int epicId) {
        ArrayList<Integer> subTaskIdList = epicsMap.get(epicId).getSubTaskIdList();
        for (int subTaskId : subTaskIdList) {
            subTaskMap.remove(subTaskId);
        }
        subTaskIdList.clear();
        epicsMap.get(epicId).setSubTaskIdList(subTaskIdList);
    }

    public int idGenerator() {
        generatorId++;
        return generatorId;
    }

    public Task getTaskById(int id) {
        return taskMap.get(id);
    }

    public Epic getEpicById(int id) {
        return epicsMap.get(id);
    }

    public SubTask getSubTaskById(int id) {
        return subTaskMap.get(id);
    }

    public void removeTaskById(int id) {
        taskMap.remove(id);
    }

    public void removeEpicById(int id) {
        ArrayList<Integer> subTaskIds = epicsMap.get(id).getSubTaskIdList();
        for (int subTaskId : subTaskIds) {
            subTaskMap.remove(subTaskId);
        }
        epicsMap.remove(id);
    }

    public void removeSubTuskById(int id) {
        ArrayList<Integer> subTaskIdsList = epicsMap.get(subTaskMap.get(id).getEpicId()).getSubTaskIdList();
        subTaskIdsList.remove(subTaskIdsList.indexOf(id));
        epicsMap.get(subTaskMap.get(id).getEpicId()).setSubTaskIdList(subTaskIdsList);
        subTaskMap.remove(id);
    }

    public void updateTask(Task task, int id) {
        task.setId(id);
        taskMap.put(task.getId(), task);
    }

    public void updateEpic(Epic epic, int id) {
        epic.setId(id);
        ArrayList<Integer> subTasksIdList = epicsMap.get(epic.getId()).getSubTaskIdList();
        epic.setSubTaskIdList(subTasksIdList);
        epicsMap.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask, int id) {
        subTask.setId(id);
        subTaskMap.put(subTask.getId(), subTask);
        Epic epic = epicsMap.get(subTask.getEpicId());
        ArrayList<Integer> subTaskIdsList = epic.getSubTaskIdList();
        int countDone = 0;
        int countInProgress = 0;
        int countNew = 0;
        for (int subTaskId : subTaskIdsList) {
            Status status = subTaskMap.get(subTaskId).getStatus();
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
        epicsMap.put(epic.getId(), epic);
    }

    public ArrayList<Integer> getSubTaskByEpic (int epicId) {
        return epicsMap.get(epicId).getSubTaskIdList();
    }
}
