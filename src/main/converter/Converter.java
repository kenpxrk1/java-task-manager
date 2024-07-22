package main.converter;

import main.tasks.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class Converter {


    public static String taskToString(Task task) {
        return task.getId() + "," + getTaskType(task.getStringTaskType()) + "," + task.getTitle() +
                "," + task.getStatus() + "," + task.getDescription() + ",";
    }


    public static String taskToString(SubTask subTask) {
        return subTask.getId() + "," + getTaskType(subTask.getStringTaskType()) + "," + subTask.getTitle() +
                "," + subTask.getStatus() + "," + subTask.getDescription() + "," + subTask.getEpicId() + ",";
    }


    public static String taskToString(EpicTask epicTask) {
        return epicTask.getId() + "," + getTaskType(epicTask.getStringTaskType()) + "," + epicTask.getTitle() +
                "," + epicTask.getStatus() + "," + epicTask.getDescription() + ",";
    }

    public static SubTask subTaskFromString(String subTask)
    {
        if (subTask.isBlank()) {
            return null;
        }
        String[] taskArray = subTask.split(",");
        int id = Integer.parseInt(taskArray[0]);
        String description = taskArray[4];
        String title = taskArray[2];
        TaskStatus status = TaskStatus.valueOf(taskArray[3]);
        int epicId = Integer.parseInt(taskArray[5]);
        return new SubTask(title, description, status, id, epicId);

    }

    public static Task taskFromString(String task) {
        if (task.isBlank()) {
            return null;
        }
        String[] taskArray = task.split(",");
        int id = Integer.parseInt(taskArray[0]);
        String description = taskArray[4];
        String title = taskArray[2];
        TaskStatus status = TaskStatus.valueOf(taskArray[3]);
        return new Task(title, description, status, id);
    }

    public static EpicTask epicTaskFromString(String epicTask) {
        if (epicTask.isBlank()) {
            return null;
        }
        String[] taskArray = epicTask.split(",");
        int id = Integer.parseInt(taskArray[0]);
        String description = taskArray[4];
        String title = taskArray[2];
        TaskStatus status = TaskStatus.valueOf(taskArray[3]);
        return new EpicTask(title, description, status, id);
    }

    public static String historyToString(List<Task> taskList) {
        ArrayList<String> historyList = new ArrayList<>();
        for (Task task : taskList) {
            historyList.add(String.valueOf(task.getId()));
        }
        return String.join(",", historyList);
    }

    public static List<Integer> historyFromString(String history) {
        List<Integer> historyList = new LinkedList<>();
        String[] idArray = history.split(",");
        for (String id : idArray) {
            historyList.add(Integer.parseInt(id));
        }
        return historyList;
    }

    public static TaskType getTaskType(String value) {
        return TaskType.valueOf(value);
    }

}
