package main.manager;

import main.tasks.Task;

import java.util.ArrayList;
import java.util.List;

public class InMemoryHistoryManager implements IHistoryManager{

    private final CustomLinkedList tasksCheckHistory = new CustomLinkedList();

    @Override
    public List<Task> getHistory() {
        return tasksCheckHistory.getTasks();
    }

    @Override
    public void remove(int id) {
        return;
    }

    @Override
    public void add(Task task) {
            tasksCheckHistory.getNodeMap().remove(task.getId());
            tasksCheckHistory.linkLast(task);
        }
    }
