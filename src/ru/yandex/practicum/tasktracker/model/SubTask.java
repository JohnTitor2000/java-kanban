package ru.yandex.practicum.tasktracker.model;

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;

    public SubTask() {
        super();
    }

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public TaskType getType() {
        return TaskType.SUBTASK;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SubTask) || !(super.equals(o))) {
            return false;
        }
        SubTask subTask = (SubTask) o;
        return epicId == subTask.epicId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEpicId(), super.hashCode());
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", title='" + getTitle() + '\'' +
                ", status=" + getStatus() +
                ", description='" + getDescription() + '\'' +
                ", id=" + getId() +
                ", startTime= " + getStartTime() +
                ", duration= " + getDuration() +
                '}';
    }
}
