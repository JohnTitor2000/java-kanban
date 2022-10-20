import java.util.ArrayList;
import java.util.Objects;

public class Epic  extends Task{

    private ArrayList<Integer> subTaskIdList;

    public Epic(String title, String description) {
        super(title, description, Status.NEW);
        subTaskIdList = new ArrayList<>();

    }

    public ArrayList<Integer> getSubTaskIdList() {
        return subTaskIdList;
    }

    public void setSubTaskIdList(ArrayList<Integer> subTaskIdList) {
        this.subTaskIdList = subTaskIdList;
    }

    public void addSubTask(Integer id) {
        subTaskIdList.add(id);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Epic)) return false;
        Epic epic = (Epic) o;
        return Objects.equals(getSubTaskIdList(), epic.getSubTaskIdList());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getSubTaskIdList());
    }

    @Override
    public String toString() {
        return "Epic{" +
                "subTaskIdList=" + subTaskIdList +
                ", title='" + title + '\'' +
                ", status=" + status +
                ", description='" + description + '\'' +
                ", id=" + id +
                '}';
    }
}
