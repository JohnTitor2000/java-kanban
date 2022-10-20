import java.util.Objects;

public class SubTask extends Task{
    int epicId;

    public int getEpicId() {
        return epicId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof SubTask)) return false;
        SubTask subTask = (SubTask) o;
        return getEpicId() == subTask.getEpicId();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getEpicId());
    }

    @Override
    public String toString() {
        return "SubTask{" +
                "epicId=" + epicId +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }

    public SubTask(int epicId, String title, String description, Status status) {
        super(title, description, status);
        this.epicId = epicId;
    }
}
