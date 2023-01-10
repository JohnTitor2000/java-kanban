package ru.yandex.practicum.tasktracker.server;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import ru.yandex.practicum.tasktracker.manager.Managers;
import ru.yandex.practicum.tasktracker.manager.TaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.util.regex.Pattern;

import static java.nio.charset.StandardCharsets.UTF_8;

public class HttpTaskServer {

    private static final int PORT = 8080;
    private final TaskManager tasksManager;
    private final HttpServer httpServer;
    private final Gson gson;

    public HttpTaskServer() throws IOException, InterruptedException {
        gson = new Gson();
        tasksManager = Managers.getDefault();
        httpServer = HttpServer.create(new InetSocketAddress(PORT), 0);
        httpServer.createContext("/task", this::handleTask);
        httpServer.createContext("/epic", this::handleEpic);
        httpServer.createContext("/subtask", this::handleSubTask);
        httpServer.createContext("/history", this::handleHistory);
    }

    public void handleTask(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    if (Pattern.matches("^/task$", path)) {
                        String response = gson.toJson(tasksManager.getTasks());
                        sendText(exchange, response, 200);
                        break;
                    } else if (Pattern.matches("^/task/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[2].substring(4));
                        if (id != -1) {
                            String response = gson.toJson(tasksManager.getTaskById(id));
                            sendText(exchange, response, 200);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        break;
                    }
                case "DELETE":
                    if (Pattern.matches("^/task/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[2].substring(4));
                        if (id != -1) {
                            tasksManager.removeTaskById(id);
                            exchange.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else if (Pattern.matches("^/task$", path)) {
                        tasksManager.removeAllTasks();
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        break;
                    }
                case "POST":
                    InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String value = br.readLine();
                    try {
                        Task task = gson.fromJson(value, Task.class);
                        if (tasksManager.getTasksWithIds().containsKey(task.getId())) {
                            tasksManager.updateTask(task);
                            sendText(exchange, "The task has been successfully completed.", 200);
                            break;
                        } else {
                            tasksManager.addTask(task);
                            sendText(exchange, "Task successfully added.", 200);
                        }
                    } catch (JsonSyntaxException e) {
                        sendText(exchange, "Invalid json received.", 400);
                        break;
                    }
                default: {
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public void handleEpic(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    if (Pattern.matches("^/epic$", path)) {
                        String response = gson.toJson(tasksManager.getEpics());
                        sendText(exchange, response, 200);
                        break;
                    } else if (Pattern.matches("^/epic/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[2].substring(4));
                        if (id != -1) {
                            String response = gson.toJson(tasksManager.getEpicById(id));
                            sendText(exchange, response, 200);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        break;
                    }
                case "DELETE":
                    if (Pattern.matches("^/epic/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[2].substring(4));
                        if (id != -1) {
                            tasksManager.removeEpicById(id);
                            exchange.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else if (Pattern.matches("^/epic$", path)) {
                        tasksManager.removeAllEpics();
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        break;
                    }
                case "POST":
                    InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String value = br.readLine();
                    try {
                        Epic epic = gson.fromJson(value, Epic.class);
                        if (tasksManager.getEpicsWithIds().containsKey(epic.getId())) {
                            tasksManager.updateEpic(epic);
                            sendText(exchange, "The task has been successfully completed.", 200);
                            break;
                        } else {
                            tasksManager.addEpic(epic);
                            sendText(exchange, "Task successfully added.", 200);
                        }
                    } catch (JsonSyntaxException e) {
                        sendText(exchange, "Invalid json received.", 400);
                        break;
                    }
                default: {
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public void handleSubTask(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            String path = exchange.getRequestURI().getPath();
            switch (method) {
                case "GET":
                    if (Pattern.matches("^/subtask$", path)) {
                        String response = gson.toJson(tasksManager.getSubTasks());
                        sendText(exchange, response, 200);
                        break;
                    } else if (Pattern.matches("^/subtask/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[2].substring(4));
                        if (id != -1) {
                            String response = gson.toJson(tasksManager.getSubTaskById(id));
                            sendText(exchange, response, 200);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else if (Pattern.matches("^/subtask/epic/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[3].substring(4));
                        if (id != -1) {
                            String response = gson.toJson(tasksManager.getSubTasksByEpicId(id));
                            sendText(exchange, response, 200);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        break;
                    }
                case "DELETE":
                    if (Pattern.matches("^/epic/\\?id=\\d+$", path)) {
                        int id = parsePathId(path.split("/")[2].substring(4));
                        if (id != -1) {
                            tasksManager.removeSubTaskById(id);
                            exchange.sendResponseHeaders(200, 0);
                            break;
                        } else {
                            exchange.sendResponseHeaders(405, 0);
                            break;
                        }
                    } else if (Pattern.matches("^/subtask$", path)) {
                        tasksManager.removeAllSubTasks();
                        exchange.sendResponseHeaders(200, 0);
                    } else {
                        exchange.sendResponseHeaders(405, 0);
                        break;
                    }
                case "POST":
                    InputStreamReader isr =  new InputStreamReader(exchange.getRequestBody(),"utf-8");
                    BufferedReader br = new BufferedReader(isr);
                    String value = br.readLine();
                    try {
                        SubTask subTask = gson.fromJson(value, SubTask.class);
                        if (tasksManager.getSubTasksWithIds().containsKey(subTask.getId())) {
                            tasksManager.updateSubTask(subTask);
                            sendText(exchange, "The task has been successfully completed.", 200);
                            break;
                        } else {
                            tasksManager.addSubTask(subTask);
                            sendText(exchange, "Task successfully added.", 200);
                        }
                    } catch (JsonSyntaxException e) {
                        sendText(exchange, "Invalid json received.", 400);
                        break;
                    }
                default: {
                    exchange.sendResponseHeaders(405, 0);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    public void handleHistory(HttpExchange exchange) throws IOException {
        try {
            String method = exchange.getRequestMethod();
            if (method.equals("GET")) {
                String response = gson.toJson(tasksManager.getHistory());
                sendText(exchange, response, 200);
            } else {
                exchange.sendResponseHeaders(405, 0);
            }
            return;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            exchange.close();
        }
    }

    private static int parsePathId(String path) {
        try {
            return Integer.parseInt(path);
        } catch (NumberFormatException exception) {
            return  -1;
        }
    }

    private void sendText(HttpExchange exchange, String text, int responseCode) throws IOException {
        byte[] response = text.getBytes(UTF_8);
        exchange.getResponseHeaders().add("Content-Type", "application/json;charset=utf-8");
        exchange.sendResponseHeaders(responseCode, response.length);
        exchange.getResponseBody().write(response);
    }
}