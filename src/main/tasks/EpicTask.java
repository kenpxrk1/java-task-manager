package main.tasks;

import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task {
    private ArrayList<Integer> subTaskId = new ArrayList<>();
    private static final TaskType taskType = TaskType.EPIC;

    public EpicTask(String title, String description, TaskStatus status, int id, ArrayList<Integer> subTaskId) {
        super(title, description, status, id);
        this.subTaskId = subTaskId;
    }

    @Override
    public String getStringTaskType() {
        return taskType.toString();
    }

    @Override
    public TaskType getTaskType() {
        return taskType;
    }

    public EpicTask(String title, String description, TaskStatus status, int id){
        super(title, description, status, id);

    }

    public EpicTask(String description, String title, TaskStatus status) {
        super(description, title, status);
    }

    public ArrayList<Integer> getSubTaskId() {
        return subTaskId;
    }

    public void setSubTaskId(ArrayList<Integer> subTaskId) {
        this.subTaskId = subTaskId;
    }

    @Override
    public String toString() {
        return "EpicTask{" +
                "title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", status='" + status + '\'' +
                ", id=" + id +
                ", subTaskId=" + subTaskId +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        EpicTask epicTask = (EpicTask) o;
        return Objects.equals(subTaskId, epicTask.subTaskId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), subTaskId);
    }
}
