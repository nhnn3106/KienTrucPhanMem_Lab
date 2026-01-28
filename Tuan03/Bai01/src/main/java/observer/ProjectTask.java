package observer;

public class ProjectTask extends Subject {
    private String taskName;
    private String status;

    public ProjectTask(String taskName) {
        this.taskName = taskName;
        this.status = "New";
    }

    public void setStatus(String status) {
        this.status = status;
        notifyObservers("Task '" + taskName + "' đã chuyển trạng thái sang: " + status);
    }
}
