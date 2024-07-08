package main.manager;

import main.tasks.EpicTask;
import main.tasks.SubTask;
import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public interface ITaskManager {
    ArrayList<Task> getTasks();
    ArrayList<SubTask> getSubTasks();
    ArrayList<EpicTask> getEpicTasks();
    ArrayList<SubTask> getEpicSubTasks(int epicId);

    Task getTask(int id);
    SubTask getSubTask(int id);
    EpicTask getEpicTask(int id);

    boolean addSubtaskIdForEpicTask(int subTaskId, int epicId);
    Task createTask(Task task);
    SubTask createSubTask(SubTask subTask);
    EpicTask createEpicTask(EpicTask epicTask);

    Task updateTask(Task task);
    SubTask updateSubTask(SubTask task);
    EpicTask updateEpicTask(EpicTask task);

    void deleteTask(int id);
    void deleteSubTask(int id);
    void deleteEpicTask(int id);

    void deleteTasks();
    void deleteSubTasks();
    void deleteEpicTasks();

    List<Task> getHistory();
}
