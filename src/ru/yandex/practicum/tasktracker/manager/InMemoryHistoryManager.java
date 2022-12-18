package ru.yandex.practicum.tasktracker.manager;

import ru.yandex.practicum.tasktracker.model.Task;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InMemoryHistoryManager implements HistoryManager{

    private final Map<Integer, Node> idsToNode = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        linkLast(task);
        remove(task.getId());
        idsToNode.put(task.getId(), last);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node node = first;
        while (node != null) {
            history.add(node.task);
            node = node.next;
        }
        return history;
    }

    @Override
    public void remove(int id) {
        removeNode(id);
    }

    private void linkLast(Task task) {
        final Node rightNode = last;
        final Node newNode = new Node(task, rightNode, null);
        last = newNode;
        if (rightNode == null) {
            first = newNode;
        } else {
            rightNode.next = newNode;
        }
    }

    private void removeNode(int id) {
        if (idsToNode.get(id) == null) {
            return;
        }
        Node oldNode = idsToNode.get(id);
        final Node next = oldNode.next;
        final Node prev = oldNode.previous;

        if (prev == null) {
            first = next;
        } else {
            prev.next = next;
            oldNode.previous = null;
        }

        if (next == null) {
            last = prev;
        } else {
            next.previous = prev;
            oldNode.next = null;
        }

        oldNode.task = null;
    }

    private static class Node {
        Task task;
        Node previous;
        Node next;

        Node(Task task, Node previous, Node next) {
            this.task = task;
            this.previous = previous;
            this.next = next;
        }
    }
}