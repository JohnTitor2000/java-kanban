package ru.yandex.practicum.tasktracker.manager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.model.TaskType;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private static final String CVS_HEADER = "id,type,name,status,description,epic";

    private final File file;

    public FileBackedTasksManager(File file){
        this.file = file;
    }

    @Override
    public void addTask(Task task) {
        super.addTask(task);
        save();
    }

    @Override
    public void addEpic(Epic epic) {
        super.addEpic(epic);
        save();
    }

    @Override
    public void addSubTask(SubTask subTask) {
        super.addSubTask(subTask);
        save();
    }

    @Override
    public void removeAllTasks() {
        super.removeAllTasks();
        save();
    }

    @Override
    public void removeAllEpics() {
        super.removeAllEpics();
        save();
    }

    @Override
    public void removeAllSubTasks() {
        super.removeAllSubTasks();
        save();
    }

    @Override
    public Task getTaskById(int id) {
        Task task = super.getTaskById(id);
        save();
        return task;
    }

    @Override
    public Epic getEpicById(int id) {
        Epic epic =  super.getEpicById(id);
        save();
        return epic;
    }

    @Override
    public SubTask getSubTaskById(int id) {
        SubTask subTask = super.getSubTaskById(id);
        save();
        return subTask;
    }

    @Override
    public void removeTaskById(int id) {
        super.removeTaskById(id);
        save();
    }

    @Override
    public void removeEpicById(int id) {
        super.removeEpicById(id);
        save();
    }

    @Override
    public void removeSubTaskById(int id) {
        super.removeSubTaskById(id);
        save();
    }

    @Override
    public void updateTask(Task task) {
        super.updateTask(task);
        save();
    }

    @Override
    public void updateEpic(Epic epic) {
        super.updateEpic(epic);
        save();
    }

    @Override
    public void updateSubTask(SubTask subTask) {
        super.updateSubTask(subTask);
        save();
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        FileBackedTasksManager fileBackedTasksManager = new FileBackedTasksManager(file);
        int maxId = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(file, StandardCharsets.UTF_8))) {
            while (reader.ready()) {
                String line = reader.readLine();
                if (line.isEmpty()) {
                    break;
                } else if (line.equals(CVS_HEADER)) {
                    continue;
                }
                Task task = FileBackedTasksManager.fromString(line);
                maxId = Math.max(task.getId(), maxId);
                if (task instanceof SubTask) {
                    fileBackedTasksManager.updateSubTask((SubTask) task);
                } else if (task instanceof Epic) {
                    fileBackedTasksManager.updateEpic((Epic) task);
                } else {
                    fileBackedTasksManager.updateTask(task);
                }
            }
            HistoryManager historyManager = fileBackedTasksManager.getHistoryManager();
            List<Integer> history = historyFromString(reader.readLine());
            for (int id : history) {
                if (fileBackedTasksManager.getTasksMap().keySet().contains(id)) {
                    historyManager.add(fileBackedTasksManager.getTaskById(id));
                } else if (fileBackedTasksManager.getEpicsMap().keySet().contains(id)) {
                    historyManager.add(fileBackedTasksManager.getEpicById(id));
                } else if (fileBackedTasksManager.getSubTasksMap().keySet().contains(id)) {
                    historyManager.add(fileBackedTasksManager.getSubTaskById(id));
                }
            }

        } catch (IOException e) {
            throw new ManagerSaveException("File reading error", e);
        }
        generatorId = maxId;
        return fileBackedTasksManager;
    }

    private void save() {
        try (Writer writer = new FileWriter(file)) {
            writer.write(CVS_HEADER + "\n");
            for (Task task : getTasks()) {
                writer.write(toString(task) + "\n");
            }
            for (SubTask subTask : getSubTasks()) {
                writer.write(toString(subTask) + "\n");
            }
            for (Epic epic : getEpics()) {
                writer.write(toString(epic) + "\n");
            }
            writer.write("\n");
            writer.write(historyToString(getHistoryManager()));
        } catch (IOException e) {
            throw new ManagerSaveException("Saving error", e);
        }
    }

    private String toString(Task task) {
        StringBuilder line = new StringBuilder();
        line.append(task.getId() + ",")
                .append(task.getType() + ",")
                .append(task.getTitle() + ",")
                .append(task.getStatus() + ",")
                .append(task.getDescription() + ",");
        if (task.getType() == TaskType.SUBTASK) {
            SubTask sb = (SubTask) task;
            line.append(sb.getEpicId() + ",");
        }
        return line.toString();
    }

    private static Task fromString(String value) {
        String[] values = value.split(",");
        Task task;
        switch (TaskType.valueOf(values[1])) {
            case TASK:
                task = new Task();
                break;
            case EPIC:
                task = new Epic();
                break;
            default:
                task = new SubTask();
                break;
        }
        task.setId(Integer.parseInt(values[0]));
        task.setTitle(values[2]);
        switch (Status.valueOf(values[3])) {
            case NEW:
                task.setStatus(Status.NEW);
                break;
            case IN_PROGRESS:
                task.setStatus(Status.IN_PROGRESS);
                break;
            default:
                task.setStatus(Status.DONE);
                break;
        }
        task.setDescription(values[4]);
        if (values[1].equals("SUBTASK")) {
            SubTask subTask = (SubTask) task;
            subTask.setEpicId(Integer.parseInt(values[5]));
            return subTask;
        }
        return task;
    }

    private static String historyToString(HistoryManager manager) {
        List<Task> history = manager.getHistory();
        StringBuilder cvsHistory = new StringBuilder();
        for (int i = 0; i < history.size(); i++) {
            if (i == history.size() - 1) {
                cvsHistory.append(history.get(i).getId());
                break;
            }
            cvsHistory.append(history.get(i).getId() + ",");
        }
        return cvsHistory.toString();
    }

    private static List<Integer> historyFromString(String value) {
        String[] values = value.split(",");
        List<Integer> history = new ArrayList<>();
        for(String id : values){
            history.add(Integer.parseInt(id));
        }
        return history;
    }
}