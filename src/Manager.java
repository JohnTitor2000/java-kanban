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

    private void addTask(String title, String description) {
        int id = idGenerator();
        taskMap.put(id, new Task(title, description, id));
    }

    private void addEpicTask(String title, String description) {
        int id = idGenerator();
        epicsMap.put(id, new Epic(title, description, id));
    }

    private void addSubTask(int epicId, String title, String description) {
        int id = idGenerator();
        epicsMap.get(epicId).addSubTask(id);
        subTaskMap.put(id, new SubTask(title, description, id));
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
}
