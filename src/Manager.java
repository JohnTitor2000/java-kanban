import java.util.HashMap;

public class Manager {
    int generatorId;

    private HashMap<Integer, SubTask> subTaskMap;
    private HashMap<Integer, Epic> epicMap;
    private HashMap<Integer, Task> taskMap;


    public Manager() {
        this.generatorId = 1;
        subTaskMap = new HashMap<>();
        epicMap = new HashMap<>();
        taskMap = new HashMap<>();
    }

    private void addSubTask(int epicId, String title, String description) {
        int id = idGenerator();
        epicMap.get(epicId).addSubTask(id);
        subTaskMap.put(id, new SubTask(title, description, id));
    }

    private void addEpicTask(String title, String description) {
        int id = idGenerator();
        epicMap.put(id, new Epic(title, description, id));
    }

    private void addTask(String title, String description) {
        int id = idGenerator();
        taskMap.put(id, new Task(title, description, id));
    }

    private int idGenerator() {
        generatorId++;
        return generatorId;
    }
}
