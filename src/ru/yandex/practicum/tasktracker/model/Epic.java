package ru.yandex.practicum.tasktracker.model;

import ru.yandex.practicum.tasktracker.Status;

import java.util.ArrayList;
import java.util.Objects;

public class Epic extends Task {

    private ArrayList<Integer> subTaskIds = new ArrayList<>();

    public ArrayList<Integer> getSubTaskIds() {
        return subTaskIds;
    }

    public void setSubTaskIds(ArrayList<Integer> subTaskIds) {
        this.subTaskIds = subTaskIds;
    }

    public void addSubTaskId(Integer id) {
        subTaskIds.add(id);
    }

    public void clearSubTaskIds() {
        subTaskIds.clear();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Epic) || !(super.equals(o))) {
            return false;
        }
        Epic epic = (Epic) o;
        return Objects.equals(getSubTaskIds(), epic.getSubTaskIds());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubTaskIds(), super.hashCode());
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.tasklistapp.Epic{" +
                "subTaskIdList=" + subTaskIds +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
