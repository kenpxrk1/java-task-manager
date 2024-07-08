package main.manager;


import main.tasks.Task;

import java.util.List;

public interface IHistoryManager {
    List<Task> getHistory();
    void UpdateTaskHistory(Task task);

}
