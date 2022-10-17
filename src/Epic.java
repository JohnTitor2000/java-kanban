import java.util.ArrayList;

public class Epic  extends Task{

    private ArrayList<SubTask> subTaskList;

    public Epic(String title, String description, int id) {
        super(title, description, id);
    }

}
