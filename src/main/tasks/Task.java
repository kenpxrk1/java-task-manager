package main.tasks;

import java.util.Objects;

public class Task {
    protected String title;
    protected String description;
    protected TaskStatus status;
    private static final TaskType taskType = TaskType.TASK;
    protected int id;


    public Task(String title, String description, TaskStatus status, int id) {
        this.title = title;
        this.description = description;
        this.status = status;
        this.id = id;
    }

    public Task(String description, String title, TaskStatus status) {
        this.description = description;
        this.title = title;
        this.status = status;
    }

    public String getStringTaskType() {
        return taskType.toString();
    }

    public TaskType getTaskType(){
        return taskType;
    }

    public int getId() {
        return id;
    }

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

    public TaskStatus getStatus() {
        return status;
    }

    public void setStatus(TaskStatus status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return Objects.equals(title, task.title) && Objects.equals(description, task.description)
                && Objects.equals(status, task.status);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, description, status);
    }


    @Override
    public String toString() {
        return "Task{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status=" + status +
                ", id=" + id +
                '}';
    }
}

