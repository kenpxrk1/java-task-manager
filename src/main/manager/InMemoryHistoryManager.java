package main.manager;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements IHistoryManager{

    private final List<Task> tasksCheckHistory = new ArrayList<>();
    static final int HISTORY_SIZE = 10;

    @Override
    public List<Task> getHistory() {
        return tasksCheckHistory;
    }

    @Override
    public void UpdateTaskHistory(Task task) {
        if (tasksCheckHistory.size() >= HISTORY_SIZE){
            tasksCheckHistory.remove(0);
        }
        tasksCheckHistory.add(task);
    }
}
