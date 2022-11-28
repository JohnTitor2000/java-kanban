package ru.yandex.practicum.tasktracker.manger;

import ru.yandex.practicum.tasktracker.model.Task;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class InMemoryHistoryManager implements HistoryManager{

    private int size = 0;
    private final Map<Integer, Node> mappingAnIdsToNode = new HashMap<>();
    private Node first;
    private Node last;

    @Override
    public void add(Task task) {
        linkLast(task);
        remove(task.getId());
        mappingAnIdsToNode.put(task.getId(), last);
    }

    @Override
    public List<Task> getHistory() {
        List<Task> history = new ArrayList<>();
        Node node = first;
        for(int i = 0; i < size; i++) {
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
        final Node l = last;
        final Node newNode = new Node(task, l, null);
        last = newNode;
        if (l == null) {
            first = newNode;
        } else {
            l.next = newNode;
        }
        size++;
    }

    private void removeNode(int id) {
        Node oldNode = mappingAnIdsToNode.get(id);
        final Task element = oldNode.task;
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
        size--;
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