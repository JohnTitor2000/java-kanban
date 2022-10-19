public class SubTask extends Task{

    int epicId;

    public int getEpicId() {
        return epicId;
    }

    public void setEpicId(int epicId) {
        this.epicId = epicId;
    }

    public SubTask(int epicId , String title, String description, int id) {
        super(title, description, id);
        this.epicId = epicId;
    }
}
