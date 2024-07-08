package main.tasks;

import java.util.ArrayList;
import java.util.Objects;

public class EpicTask extends Task {
    private ArrayList<Integer> subTaskId = new ArrayList<>();

    public EpicTask(String title, String description, String status, int id, ArrayList<Integer> subTaskId) {
        super(title, description, status, id);
        this.subTaskId = subTaskId;
    }

    public EpicTask(String description, String title, String status) {
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
