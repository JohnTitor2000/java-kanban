package ru.yandex.practicum.tasktracker.model;

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

    public void clearSubtaskIds() {
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
        return "Epic{" +
                "subTaskIdList=" + subTaskIds +
                ", title='" + super.getTitle() + '\'' +
                ", status=" + super.getStatus() +
                ", description='" + super.getDescription() + '\'' +
                ", id=" + super.getId() +
                '}';
    }
}
