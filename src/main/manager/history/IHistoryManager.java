package main.manager.history;


import main.tasks.Task;

import java.util.List;

public interface IHistoryManager {
    List<Task> getHistory();
    void add(Task task);
    void remove(int id);

}
