package main.manager;

import main.tasks.EpicTask;
import main.tasks.SubTask;
import main.tasks.Task;
import main.tasks.TaskStatus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class InMemoryTaskManager implements ITaskManager {
    HashMap<Integer, Task> tasks = new HashMap<>();
    HashMap<Integer, SubTask> subTasks = new HashMap<>();
    HashMap<Integer, EpicTask> epicTasks = new HashMap<>();

    private final IHistoryManager historyManager = Managers.getDefaultHistoryManager();

    private int idCounter = 0;

    public int getIdCounter() {
        return idCounter;
    }

    @Override
    public ArrayList<Task> getTasks() {
        return new ArrayList<>(tasks.values());
    }

    @Override
    public ArrayList<SubTask> getSubTasks() {
        return new ArrayList<>(subTasks.values());
    }


    @Override
    public ArrayList<EpicTask> getEpicTasks() {
        return new ArrayList<>(epicTasks.values());
    }

    @Override
    public ArrayList<SubTask> getEpicSubTasks(int epicId) {
        ArrayList<SubTask> epicSubTasks = new ArrayList<>();
        if (epicTasks.containsKey(epicId)) {
            EpicTask epicTask = epicTasks.get(epicId);
            ArrayList<Integer> epicSubTaskId = epicTask.getSubTaskId();
            if (!epicSubTaskId.isEmpty()) {
                for (int id : epicSubTaskId) {
                    epicSubTasks.add(getSubTask(id));
                }
            }
        }
        return epicSubTasks;
    }

    @Override
    public Task getTask(int id) {
        if (!tasks.containsKey(id)){
            return null;
        }
        Task task = tasks.get(id);
        if (task == null){
            return null;
        }
        historyManager.UpdateTaskHistory(task);
        return task;

    }

    @Override
    public SubTask getSubTask(int id) {
        if (!subTasks.containsKey(id)){
            return null;
        }
        SubTask subTask = subTasks.get(id);
        if (subTask == null){
            return null;
        }
        historyManager.UpdateTaskHistory(subTask);
        return subTask;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        if (!epicTasks.containsKey(id)){
            return null;
        }
        EpicTask epicTask = epicTasks.get(id);
        if (epicTask == null){
            return null;
        }
        historyManager.UpdateTaskHistory(epicTask);
        return epicTask;
    }

    @Override
    public Task createTask(Task task) {
        int id = ++this.idCounter;
        task.setId(id);
        tasks.put(id, task);
        return task;
    }

    @Override
    public boolean addSubtaskIdForEpicTask(int subTaskId, int epicId) {
        EpicTask epicTask = epicTasks.get(epicId);
        if (epicTask == null) {
            System.out.println("Такой задачи не существует");
            return false;
        }
        epicTask.getSubTaskId().add(subTaskId);
        return true;
    }

    @Override
    public List<Task> getHistory() {
        return historyManager.getHistory();
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        int epicId = subTask.getEpicId();

        int subTaskId = ++idCounter;
        subTask.setId(subTaskId);
        if (!addSubtaskIdForEpicTask(subTaskId, epicId)) {
            return null;
        }
        subTasks.put(subTaskId, subTask);
        updateEpicStatus(epicId);
        return subTask;

    }

    @Override
    public EpicTask createEpicTask(EpicTask epicTask) {
        int id = ++this.idCounter;
        epicTask.setId(id);
        updateEpicStatus(id);
        epicTasks.put(id, epicTask);
        return epicTask;
    }

    @Override
    public Task updateTask(Task task) {
        if (task == null) {
            return null;
        }
        int taskId = task.getId();
        if (tasks.containsKey(taskId)) {
            tasks.put(taskId, task);
            return task;
        }
        return null;
    }

    @Override
    public SubTask updateSubTask(SubTask task) {
        if (task == null) {
            return null;
        }
        int epicId = task.getEpicId();
        if (!epicTasks.containsKey(epicId)) {
            System.out.println("Такого эпика не существует");
            return null;
        }
        int subTaskId = task.getId();
        if (!subTasks.containsKey(subTaskId)) {
            System.out.println("Такого субтаска не существует");
            return null;
        }
        subTasks.put(subTaskId, task);
        updateEpicStatus(epicId);
        return task;
    }

    @Override
    public EpicTask updateEpicTask(EpicTask task) {

        if (task == null) {
            return null;
        }
        int epicID = task.getId();
        if (!epicTasks.containsKey(epicID)) {
            return null;
        }
        epicTasks.put(epicID, task);
        updateEpicStatus(epicID);
        return task;
    }

    @Override
    public void deleteTask(int id) {
        if (!tasks.containsKey(id)) {
            System.out.println("Такой таск не существует");
            return;
        }
        tasks.remove(id);
    }

    @Override
    public void deleteSubTask(int id) {
        if (subTasks.containsKey(id)) {
            SubTask subTask = subTasks.get(id);
            subTasks.remove(id);
            int epicId = subTask.getEpicId();
            if (epicTasks.containsKey(epicId)) {
                EpicTask epicTask = getEpicTask(epicId);
                if (epicTask.getSubTaskId().contains(subTask.getId())) {
                    epicTask.getSubTaskId().remove((Object) subTask.getId());
                }
            }
        }
    }

    @Override
    public void deleteEpicTask(int id) {
        if (epicTasks.containsKey(id)) {
            EpicTask epicTask = getEpicTask(id);
            ArrayList<Integer> subTaskIds = epicTask.getSubTaskId();
            epicTask.getSubTaskId().clear();
            for (int num : subTaskIds) {
                subTasks.remove(num);
            }
        }
    }

    @Override
    public void deleteTasks() {
        tasks.clear();
    }

    @Override
    public void deleteSubTasks() {
        subTasks.clear();
    }

    @Override
    public void deleteEpicTasks() {
        epicTasks.clear();
        subTasks.clear();
    }


    public void updateEpicStatus(int epicID) {
        if (epicTasks.containsKey(epicID)) {
            EpicTask epicTask = epicTasks.get(epicID);
            ArrayList<Integer> epicSubTasks = epicTask.getSubTaskId();

            if (epicSubTasks.isEmpty()) {
                epicTask.setStatus(TaskStatus.NEW);
                return;
            }

            int newCounter = 0;
            int inProgressCounter = 0;
            int doneCounter = 0;

            for (int subId : epicSubTasks) {
                TaskStatus status = subTasks.get(subId).getStatus();
                switch (status) {
                    case DONE:
                        doneCounter++;
                        break;
                    case IN_PROGRESS:
                        inProgressCounter++;
                        break;
                    case NEW:
                        newCounter++;
                        break;
                }
            }
            if (doneCounter == epicSubTasks.size()) {
                epicTask.setStatus(TaskStatus.DONE);
            } else if (inProgressCounter > 0) {
                epicTask.setStatus(TaskStatus.IN_PROGRESS);
            } else {
                epicTask.setStatus(TaskStatus.NEW);
            }
        }
    }
}
