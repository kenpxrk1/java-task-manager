package main.manager;


import main.manager.history.IHistoryManager;
import main.manager.history.InMemoryHistoryManager;
import main.manager.tasks.ITaskManager;
import main.manager.tasks.memory.InMemoryTaskManager;

public class Managers {



    public static ITaskManager getDefualtTaskManager(){
        return new InMemoryTaskManager();
    }

    public static IHistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }
}
