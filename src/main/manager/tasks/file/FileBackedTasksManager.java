package main.manager.tasks.file;

import main.converter.Converter;
import main.exceptions.ManagerSaveException;
import main.manager.tasks.memory.InMemoryTaskManager;
import main.tasks.EpicTask;
import main.tasks.SubTask;
import main.tasks.Task;

import java.io.*;
import java.nio.file.Path;
import java.util.List;

import static main.tasks.TaskStatus.IN_PROGRESS;
import static main.tasks.TaskStatus.NEW;

public class FileBackedTasksManager extends InMemoryTaskManager {

    private final Path savingPath;

    public FileBackedTasksManager(Path savingPath) {
        this.savingPath = savingPath;
    }

    private void save() {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(String.valueOf(savingPath)))) {
            writer.write("id,type,name,status,description,epic\n");
            for (Task task : getTasks()) {
                writer.write(Converter.taskToString(task) + "\n");
            }
            for (EpicTask task : getEpicTasks()) {
                writer.write(Converter.taskToString(task) + "\n");
            }
            for (SubTask task : getSubTasks()) {
                writer.write(Converter.taskToString(task) + "\n");
            }
            System.out.println(historyManager.getHistory());
            if (historyManager.getHistory() != null) {
                writer.write("\n" + Converter.historyToString(historyManager.getHistory()));
            }
        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при сохранении файла");
        }
    }

    public static FileBackedTasksManager loadFromFile(File file) {
        final FileBackedTasksManager manager = new FileBackedTasksManager(file.toPath());
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            int maxId = 0;
            br.readLine();
            Boolean isHistory = false;
            if (!isHistory) {
                String line = br.readLine();
                if (line.isEmpty()) {
                    isHistory = true;
                } else {
                    switch (Converter.getTaskType(line.split(",")[1])) {
                        case TASK:
                            Task task = Converter.taskFromString(line);
                            if (task.getId() > maxId) {
                                maxId = task.getId();
                            }
                            tasks.put(task.getId(), task);
                            break;

                        case EPIC:
                            EpicTask epic = Converter.epicTaskFromString(line);
                            if (epic.getId() > maxId) {
                                maxId = epic.getId();
                            }
                            epicTasks.put(epic.getId(), epic);
                            break;

                        case SUBTASK:
                            SubTask subTask = Converter.subTaskFromString(line);
                            if (subTask.getId() > maxId) {
                                maxId = subTask.getId();
                            }
                            subTasks.put(subTask.getId(), subTask);
                            break;
                    }
                }

            } else {
                String line = br.readLine();
                List<Integer> history = Converter.historyFromString(line);
                for (Integer id : history) {
                    if (tasks.containsKey(id)) {
                        historyManager.add(tasks.get(id));
                        continue;
                    }
                    if (subTasks.containsKey(id)) {
                        historyManager.add(subTasks.get(id));
                        continue;
                    }
                    if (epicTasks.containsKey(id)) {
                        historyManager.add(epicTasks.get(id));
                    }
                }

            }


            return manager;

        } catch (IOException e) {
            throw new ManagerSaveException("Ошибка при чтении файла");
        }
    }

    @Override
    public Task getTask(int id) {
        Task task = super.getTask(id);
        save();
        return task;
    }

    @Override
    public SubTask getSubTask(int id) {
        SubTask subTask = super.getSubTask(id);
        save();
        return subTask;
    }

    @Override
    public EpicTask getEpicTask(int id) {
        EpicTask epicTask = super.getEpicTask(id);
        save();
        return epicTask;
    }

    @Override
    public Task createTask(Task task) {
        Task createdTask = super.createTask(task);
        save();
        return createdTask;
    }

    @Override
    public SubTask createSubTask(SubTask subTask) {
        SubTask createdTask = super.createSubTask(subTask);
        save();
        return createdTask;
    }

    @Override
    public EpicTask createEpicTask(EpicTask epicTask) {
        EpicTask createdTask = super.createEpicTask(epicTask);
        save();
        return createdTask;
    }

    public static void main(String[] args) {

        String projectDir = System.getProperty("user.dir");


        final Path filePath = Path.of("src/resources/tasks.csv");
        FileBackedTasksManager manager = new FileBackedTasksManager(filePath);

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
        manager.getTask(1);
        manager.getEpicTask(3);
        manager.getTask(2);

        FileBackedTasksManager manager2 =
                FileBackedTasksManager.loadFromFile(new File("src/resources/tasks.csv"));
//        System.out.println("Загруженные таски");
//        System.out.println(manager2.getTasks());
//        System.out.println("Загруженные эпики");
//        System.out.println(manager2.getEpicTasks());
//        System.out.println("Загруженные субтаски");
//        System.out.println(manager2.getSubTasks());
    }


}
