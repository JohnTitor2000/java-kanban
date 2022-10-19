import java.util.ArrayList;
import java.util.HashMap;

public class Manager {
    int generatorId;

    private HashMap<Integer, SubTask> subTaskMap;
    private HashMap<Integer, Epic> epicsMap;
    private HashMap<Integer, Task> taskMap;


    public Manager() {
        this.generatorId = 1;
        subTaskMap = new HashMap<>();
        epicsMap = new HashMap<>();
        taskMap = new HashMap<>();
    }

    private void addTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    private void addEpicTask(Epic epic) {
        epicsMap.put(epic.getId(), epic);
    }

    private void addSubTask(SubTask subTask) {
        epicsMap.get(subTask.getEpicId()).addSubTask(subTask.getId());
        subTaskMap.put(subTask.getId(), subTask);
    }

    private void removeAllTask() {
        taskMap.clear();
    }

    private void removeAllEpics() {
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

    private int idGenerator() {
        generatorId++;
        return generatorId;
    }

    private Task getTaskById(int id) {
        return taskMap.get(id);
    }

    private Epic getEpicById(int id) {
        return epicsMap.get(id);
    }

    private SubTask getSubTaskById(int id) {
        return subTaskMap.get(id);
    }

    private void removeTaskById(int id) {
        taskMap.remove(id);
    }

    private void removeEpicById(int id) {
        ArrayList<Integer> subTaskIds = epicsMap.get(id).getSubTaskIdList();
        for (int subTaskId : subTaskIds) {
            subTaskMap.remove(subTaskId);
        }
        epicsMap.remove(id);
    }

    private void removeSubTuskById(int id) {
        ArrayList<Integer> subTaskIdsList = epicsMap.get(subTaskMap.get(id).getEpicId()).getSubTaskIdList();
        subTaskIdsList.remove(id);
        epicsMap.get(subTaskMap.get(id).getEpicId()).setSubTaskIdList(subTaskIdsList);
        subTaskMap.remove(id);
    }

    public void updateTask(Task task) {
        taskMap.put(task.getId(), task);
    }

    public void updateEpic(Epic epic) {
        ArrayList<Integer> subTasksIdList = epicsMap.get(epic.getId()).getSubTaskIdList();
        epic.setSubTaskIdList(subTasksIdList);
        epicsMap.put(epic.getId(), epic);
    }

    public void updateSubTask(SubTask subTask) {
        subTaskMap.put(subTask.getId(), subTask);
    }
}
