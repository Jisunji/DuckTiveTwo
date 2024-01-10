package Model;

public class TaskHistoryData {
    private String task;
    private String category;
    private String description;
    private String priority;
    private String date;
    private String id;
    private String status;
    private String time;
    public TaskHistoryData(String task, String category, String description, String priority, String date, String status, String time) {
        this.task = task;
        this.category = category;
        this.description = description;
        this.priority = priority;
        this.date = date;
        this.status = status;
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPriority() {
        return priority;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
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

    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    public TaskHistoryData(){

    }
}
