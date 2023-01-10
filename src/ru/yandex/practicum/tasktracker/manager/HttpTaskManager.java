package ru.yandex.practicum.tasktracker.manager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;
import ru.yandex.practicum.tasktracker.server.KVTaskClient;

import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

public class HttpTaskManager extends FileBackedTasksManager {

    public static final String TASK_KEY = "TASK";
    public static final String EPIC_KEY = "EPIC";
    public static final String SUBTASK_KEY = "SUBTASK";
    public static final String HISTORY_KEY = "HISTORY";
    private final KVTaskClient kvTaskClient;
    private final Gson gson;

    public HttpTaskManager(String uri) throws IOException, InterruptedException {
        kvTaskClient = new KVTaskClient(URI.create(uri));
        gson = new Gson();
    }

    @Override
    protected void save() throws IOException, InterruptedException {
        System.out.println(gson.toJson(this.getTasks()));
        kvTaskClient.put(TASK_KEY, gson.toJson(this.getTasks()));
        kvTaskClient.put(EPIC_KEY, gson.toJson(this.getEpics()));
        kvTaskClient.put(SUBTASK_KEY, gson.toJson(this.getSubTasks()));
        kvTaskClient.put(HISTORY_KEY, gson.toJson(getHistory().toArray()));
    }

    public static HttpTaskManager load(String uri) throws IOException, InterruptedException {
        Gson gson = new Gson();
        HttpTaskManager httpTaskManager = new HttpTaskManager(uri);
        KVTaskClient kvTaskClientLoad = httpTaskManager.getKvTaskClient();
        List<Task> tasks = gson.fromJson(kvTaskClientLoad.load(TASK_KEY), new TypeToken<ArrayList<Task>>() {}.getType());
        List<Epic> epics = gson.fromJson(kvTaskClientLoad.load(EPIC_KEY), new TypeToken<ArrayList<Epic>>() {}.getType());
        List<SubTask> subTasks = gson.fromJson(kvTaskClientLoad.load(SUBTASK_KEY), new TypeToken<ArrayList<SubTask>>() {}.getType());
        List<Task> history = gson.fromJson(kvTaskClientLoad.load(HISTORY_KEY), new TypeToken<ArrayList<Task>>() {}.getType());
        if (tasks != null && tasks.size() != 0) {
            for (Task task : tasks) {
                httpTaskManager.updateTask(task);
            }
        }
        if (epics != null && epics.size() != 0) {
            for (Epic epic : epics) {
                httpTaskManager.updateEpic(epic);
            }
        }
        if (subTasks != null && subTasks.size() != 0) {
            for (SubTask subTask : subTasks) {
                httpTaskManager.updateSubTask(subTask);
            }
        }
        if (history != null && history.size() != 0) {
            for (Task task : history) {
                httpTaskManager.historyManager.add(task);
            }
        }
        return httpTaskManager;
    }

    public KVTaskClient getKvTaskClient() {
        return kvTaskClient;
    }
}
