package main;
import main.manager.InMemoryTaskManager;
import main.tasks.EpicTask;
import main.tasks.SubTask;
import main.tasks.Task;
import static main.tasks.TaskStatus.*;

public class Program {
    public static void main(String[] args) {
        InMemoryTaskManager manager = new InMemoryTaskManager();

        //Создание
        Task task1 = new Task("Task #1", "Task1 description", NEW);
        Task task2 = new Task("Task #2", "Task2 description", IN_PROGRESS);
        final Task taskId1 = manager.createTask(task1);
        final Task taskId2 = manager.createTask(task2);

        EpicTask epic1 = new EpicTask("Epic #1", "Epic1 description", NEW);
        EpicTask epic2 = new EpicTask("Epic #2", "Epic2 description", NEW);
        final EpicTask epicId1 = manager.createEpicTask(epic1);
        final EpicTask epicId2 = manager.createEpicTask(epic2);

        SubTask subtask1 = new SubTask("Subtask #1-1", "Subtask1 description", NEW, epicId1.getId());
        SubTask subtask2 = new SubTask("Subtask #2-1", "Subtask2 description", NEW, epicId2.getId());
        SubTask subtask3 = new SubTask("Subtask #2-2", "Subtask3 description", NEW, epicId2.getId());
        final SubTask subtaskId1 = manager.createSubTask(subtask1);
        final SubTask subtaskId2 = manager.createSubTask(subtask2);
        final SubTask subtaskId3 = manager.createSubTask(subtask3);

        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());

        //Обновление
        System.out.println("ИЗМЕНЕНИЕ ФАЙЛОВ!!!");
        Task taskStatus1 = manager.getTask(taskId1.getId());
        taskStatus1.setStatus(IN_PROGRESS);
        manager.updateTask(taskStatus1);
        System.out.println("Смена статуса Task1: Было:NEW  Стало:IN_PROGRESS");

        SubTask subtaskStatus1 = manager.getSubTask(subtaskId1.getId());
        SubTask subtaskStatus2 = manager.getSubTask(subtaskId2.getId());
        SubTask subtaskStatus3 = manager.getSubTask(subtaskId3.getId());

        manager.updateSubTask(subtaskStatus1);
        subtaskStatus1.setStatus(IN_PROGRESS);
        manager.updateEpicStatus(epicId1.getId());
        manager.updateEpicTask(epic1);
        System.out.println("Смена статуса Subtask1-1: Было:NEW  Стало:IN_PROGRESS");
        subtaskStatus2.setStatus(IN_PROGRESS);
        manager.updateSubTask(subtaskStatus2);
        manager.updateEpicStatus(epicId2.getId());
        manager.updateEpicTask(epic2);
        System.out.println("Смена статуса Subtask2-1: Было:NEW  Стало:DONE");
        subtaskStatus3.setStatus(IN_PROGRESS);
        manager.updateSubTask(subtaskStatus3);
        manager.updateEpicStatus(epicId2.getId());
        manager.updateEpicTask(epic2);
        System.out.println("Смена статуса Subtask2-2: Было:NEW  Стало:IN_PROGRESS");



        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());


        // Удаление
        System.out.println("УДАЛЕНИЕ ФАЙЛОВ!!!");
        manager.deleteSubTask(subtaskId2.getId());
        System.out.println("Удалена задача Subtask 2-1");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());
        manager.deleteEpicTasks();
        System.out.println("Удалены все задачи типа Epic");
        System.out.println(manager.getTasks());
        System.out.println(manager.getEpicTasks());
        System.out.println(manager.getSubTasks());

        System.out.println("ВЫВОД ИСТОРИИ!!!");
        for (Task task : manager.getHistory()) {
            System.out.println(task);
        }
    }
}
