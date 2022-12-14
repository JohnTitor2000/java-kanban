package ru.yandex.practicum.tasktracker.model;
import com.google.gson.Gson;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Epic extends Task {

    private final List<Integer> subTaskIds = new ArrayList<>();
    private LocalDateTime endTime;

    public List<Integer> getSubTaskIds() {
        return Collections.unmodifiableList(subTaskIds);
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public void removeSubtaskId(Integer id) {
        subTaskIds.remove(id);
    }

    public void addSubTaskId(Integer id) {
        subTaskIds.add(id);
    }

    public void clearSubtaskIds() {
        subTaskIds.clear();
    }

    @Override
    public TaskType getType() {
        return TaskType.EPIC;
    }

    @Override
    public boolean equals(Object o) {
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
                ", title='" + getTitle() + '\'' +
                ", status=" + getStatus() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                '}';
    }
}
