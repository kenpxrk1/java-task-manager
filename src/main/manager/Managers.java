package main.manager;



public class Managers {



    public static ITaskManager getDefualtTaskManager(){
        return new InMemoryTaskManager();
    }

    public static IHistoryManager getDefaultHistoryManager(){
        return new InMemoryHistoryManager();
    }
}
