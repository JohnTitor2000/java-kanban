package ru.yandex.practicum.tasktracker.model;

import java.util.Objects;

public class SubTask extends Task {
    private int epicId;

    public int getEpicId() {
        return epicId;
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
        return getEpicId() == subTask.getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEpicId(), super.hashCode());
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.tasklistapp.SubTask{" +
                "epicId=" + epicId +
                ", title='" + super.getTitle() + '\'' +
                ", status=" + super.getStatus() +
                ", description='" + super.getDescription() + '\'' +
                ", id=" + super.getId() +
                '}';
    }
}
