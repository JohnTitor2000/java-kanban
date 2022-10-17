public class Task {
    private static final String NEW_STATUS = "NEW";
    private static final String IN_PROGRESS_STATUS = "IN_PROGRESS";
    private static final String DONE_STATUS = "DONE";
    String title;
    String description;
    String status;
    int id;

    public Task(String title, String description, int id) {
        this.title = title;
        this.description = description;
        this.status = NEW_STATUS;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
