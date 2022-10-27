package ru.yandex.practicum.tasktracker.model;

import java.util.Objects;

public class Task {
    private String title;
    private Status status;
    private String description;
    private int id;

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Task)) {
            return false;
        }
        Task task = (Task) o;
        return getId() == task.getId()
                && Objects.equals(getTitle(), task.getTitle())
                && getStatus() == task.getStatus()
                && Objects.equals(getDescription(), task.getDescription());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTitle(), getStatus(), getDescription(), getId());
    }

    @Override
    public String toString() {
        return "ru.yandex.practicum.tasklistapp.Task{" +
                "title='" + title + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
