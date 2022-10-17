import java.util.ArrayList;

public class Epic  extends Task{

    private ArrayList<Integer> subTaskIdList;

    public Epic(String title, String description, int id) {
        super(title, description, id);
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

}
