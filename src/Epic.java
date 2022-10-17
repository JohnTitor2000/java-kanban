import java.util.ArrayList;

public class Epic  extends Task{

    private ArrayList<Integer> subTaskIdList;

    public Epic(String title, String description, int id) {
        super(title, description, id);
        subTaskIdList = new ArrayList<>();
    }

    public void addSubTask(Integer id) {
        subTaskIdList.add(id);
    }

}
