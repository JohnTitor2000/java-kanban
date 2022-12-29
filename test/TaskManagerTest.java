import org.junit.jupiter.api.Test;
import ru.yandex.practicum.tasktracker.manager.TaskManager;
import ru.yandex.practicum.tasktracker.model.Epic;
import ru.yandex.practicum.tasktracker.model.Status;
import ru.yandex.practicum.tasktracker.model.SubTask;
import ru.yandex.practicum.tasktracker.model.Task;
import java.util.List;
import static org.junit.jupiter.api.Assertions.*;
import static ru.yandex.practicum.tasktracker.model.Status.NEW;

abstract class TaskManagerTest {
    TaskManager taskManager;

    @Test
    void updateEpicStatus_shouldUpdateEpicStatusToNew_ifEpicDoesNotHaveSubtasks() {
        Epic testEpic = new Epic();
        taskManager.addEpic(testEpic);
        assertEquals(NEW, taskManager.getEpicById(1).getStatus(), "Статус эпика не соответствует сабтаскам");
    }

    @Test
    void updateEpicStatus_shouldUpdateEpicStatusToNew_ifEpicHaveOnlyNewSubtasks() {
        Epic testEpic = new Epic();
        taskManager.addEpic(testEpic);
        SubTask sb1 = new SubTask();
        sb1.setEpicId(1);
        SubTask sb2 = new SubTask();
        sb2.setEpicId(1);
        taskManager.addSubTask(sb1);
        taskManager.addSubTask(sb2);
        assertEquals(NEW, taskManager.getEpicById(1).getStatus(), "Статус эпика не соответствует сабтаскам");
    }

    @Test
    void updateEpicStatus_shouldUpdateEpicStatusToDone_ifEpicHaveOnlyDoneSubtasks() {
        Epic testEpic = new Epic();
        taskManager.addEpic(testEpic);
        SubTask sb1 = new SubTask();
        sb1.setEpicId(1);
        sb1.setStatus(Status.DONE);
        SubTask sb2 = new SubTask();
        sb2.setEpicId(1);
        sb2.setStatus(Status.DONE);
        taskManager.addSubTask(sb1);
        taskManager.addSubTask(sb2);
        assertEquals(Status.DONE, taskManager.getEpicById(1).getStatus(), "Статус эпика не соответствует сабтаскам");
    }

    @Test
    void updateEpicStatus_shouldUpdateEpicStatusToInProgress_ifEpicHaveDoneAndNewSubtasks() {
        Epic testEpic = new Epic();
        taskManager.addEpic(testEpic);
        SubTask sb1 = new SubTask();
        sb1.setEpicId(1);
        sb1.setStatus(Status.DONE);
        SubTask sb2 = new SubTask();
        sb2.setEpicId(1);
        taskManager.addSubTask(sb1);
        taskManager.addSubTask(sb2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(1).getStatus(), "Статус эпика не соответствует сабтаскам");
    }

    @Test
    void updateEpicStatus_shouldUpdateEpicStatusToInProgress_ifEpicHaveOnlyInProgressSubtasks() {
        Epic testEpic = new Epic();
        taskManager.addEpic(testEpic);
        SubTask sb1 = new SubTask();
        sb1.setEpicId(1);
        sb1.setStatus(Status.IN_PROGRESS);
        SubTask sb2 = new SubTask();
        sb2.setEpicId(1);
        sb2.setStatus(Status.IN_PROGRESS);
        taskManager.addSubTask(sb1);
        taskManager.addSubTask(sb2);
        assertEquals(Status.IN_PROGRESS, taskManager.getEpicById(1).getStatus(), "Статус эпика не соответствует сабтаскам");
    }

    @Test
    void addTask_shouldAddTask_ifMapEmpty() {
        Task task = new Task();
        task.setTitle("Test Task Title");
        task.setDescription("Test Task Description");
        taskManager.addTask(task);
        Task savedTask = taskManager.getTaskById(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(1, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addTask_shouldAddTask_ifMapNotEmpty() {
        Task task = new Task();
        task.setTitle("Test Task Title");
        task.setDescription("Test Task Description");
        taskManager.addTask(new Task());
        taskManager.addTask(new Task());
        taskManager.addTask(task);
        Task savedTask = taskManager.getTaskById(task.getId());
        assertNotNull(savedTask, "Задача не найдена.");
        assertEquals(task, savedTask, "Задачи не совпадают.");
        final List<Task> tasks = taskManager.getTasks();
        assertNotNull(tasks, "Задачи на возвращаются.");
        assertEquals(3, tasks.size(), "Неверное количество задач.");
        assertEquals(task, tasks.get(2), "Задачи не совпадают.");
    }

    @Test
    void addEpic_shouldAddEpic_ifEpicsMapEmpty() {
        Epic epic = new Epic();
        epic.setTitle("Test Epic Title");
        epic.setDescription("Test Epic Description");
        taskManager.addEpic(epic);
        Epic savedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(1, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(0), "Задачи не совпадают.");
    }

    @Test
    void addEpic_shouldAddEpic_ifEpicsMapNotEmpty() {
        Epic epic = new Epic();
        epic.setTitle("Test Epic Title");
        epic.setDescription("Test Epic Description");
        taskManager.addEpic(new Epic());
        taskManager.addEpic(new Epic());
        taskManager.addEpic(epic);
        Epic savedEpic = taskManager.getEpicById(epic.getId());
        assertNotNull(savedEpic, "Задача не найдена.");
        assertEquals(epic, savedEpic, "Задачи не совпадают.");
        final List<Epic> epics = taskManager.getEpics();
        assertNotNull(epics, "Задачи на возвращаются.");
        assertEquals(3, epics.size(), "Неверное количество задач.");
        assertEquals(epic, epics.get(2), "Задачи не совпадают.");
    }

    @Test
    void addSubTask_shouldAddSubTask_ifSubTaskEmpty() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask();
        subTask.setTitle("Test subTasks Title");
        subTask.setDescription("Test subTasks Description");
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        SubTask savedSubTask = taskManager.getSubTaskById(subTask.getId());
        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask, savedSubTask, "Задачи не совпадают.");
        final List<SubTask> subTasks = taskManager.getSubTasks();
        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(1, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask, subTasks.get(0), "Задачи не совпадают.");
    }

    @Test
    void addSubTask_shouldAddSubTask_ifSubTaskNotEmpty() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        SubTask subTask1 = new SubTask();
        subTask1.setTitle("Test subTasks Title");
        subTask1.setDescription("Test subTasks Description");
        subTask1.setEpicId(epic.getId());
        SubTask subTask2 = new SubTask();
        SubTask subTask3 = new SubTask();
        subTask2.setEpicId(epic.getId());
        subTask3.setEpicId(epic.getId());
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        taskManager.addSubTask(subTask1);
        SubTask savedSubTask = taskManager.getSubTaskById(subTask1.getId());
        assertNotNull(savedSubTask, "Задача не найдена.");
        assertEquals(subTask1, savedSubTask, "Задачи не совпадают.");
        final List<SubTask> subTasks = taskManager.getSubTasks();
        assertNotNull(subTasks, "Задачи на возвращаются.");
        assertEquals(3, subTasks.size(), "Неверное количество задач.");
        assertEquals(subTask1, subTasks.get(2), "Задачи не совпадают.");
    }

    @Test
    void removeAllTasks_shouldRemoveAllTasks_ifTaskMapNotEmpty() {
        Task testTask1 = new Task();
        Task testTask2 = new Task();
        taskManager.addTask(testTask1);
        taskManager.addTask(testTask2);
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getTasks().size(), "Список тасков не очищается");
    }

    @Test
    void removeAllTasks_shouldRemoveAllTasks_ifTaskMapEmpty() {
        taskManager.removeAllTasks();
        assertEquals(0, taskManager.getTasks().size(), "Список тасков не очищается");
    }

    @Test
    void removeAllEpics_shouldRemoveAllEpicsAndTheirSubTasks_ifEpicsMapNotEmpty() {
        Epic testEpic1 = new Epic();
        Epic testEpic2 = new Epic();
        taskManager.addEpic(testEpic1);
        taskManager.addEpic(testEpic2);
        SubTask testSubTask1 = new SubTask();
        testSubTask1.setEpicId(1);
        SubTask testSubTask2 = new SubTask();
        testSubTask2.setEpicId(2);
        taskManager.addSubTask(testSubTask1);
        taskManager.addSubTask(testSubTask2);
        taskManager.removeAllEpics();
        assertEquals(0, taskManager.getEpics().size(), "Список эпиков не очищается");
        assertEquals(0, taskManager.getSubTasks().size(), "Список сабтасков не очищается при очистке эпиков");
    }

    @Test
    void removeAllEpics_shouldRemoveAllEpics_ifEpicsWithoutSubTask() {
        Epic testEpic1 = new Epic();
        Epic testEpic2 = new Epic();
        taskManager.addEpic(testEpic1);
        taskManager.addEpic(testEpic2);
        taskManager.removeAllEpics();
        assertEquals(0, taskManager.getEpics().size(), "Список эпиков не очищается");
        assertEquals(0, taskManager.getSubTasks().size(), "Список сабтасков не очищается при очистке эпиков");
    }

    @Test
    void getTaskById_shouldReturnTask() {
        Task task = new Task();
        task.setTitle("Test task title");
        task.setDescription("Test Task description");
        taskManager.addTask(task);
        assertEquals(task, taskManager.getTaskById(1), "Задачи не совпадают");
        assertNotNull(taskManager.getTaskById(1), "Задача не найдена");
    }

    @Test
    void getTaskById_shouldReturnNull_ifIdIncorrect() {
        Task task = new Task();
        task.setTitle("Test task title");
        task.setDescription("Test Task description");
        taskManager.addTask(task);
        assertNull(taskManager.getTaskById(10), "Найдена несуществующая задача");
    }

    @Test
    void getTaskById_shouldReturnNull_ifTaskMapEmpty() {
        assertNull(taskManager.getTaskById(10), "Найдена несуществующая задача");
    }

    @Test
    void getEpicById_shouldReturnEpic() {
        Epic epic = new Epic();
        epic.setTitle("Test epic title");
        epic.setDescription("Test epic description");
        taskManager.addEpic(epic);
        assertEquals(epic, taskManager.getEpicById(epic.getId()), "Задачи не совпадают");
        assertNotNull(taskManager.getEpicById(epic.getId()), "Задача не найдена");
    }

    @Test
    void getEpicById_shouldReturnNull_ifIncorrectId() {
        Epic epic = new Epic();
        epic.setTitle("Test epic title");
        epic.setDescription("Test epic description");
        taskManager.addEpic(epic);
        assertNull(taskManager.getEpicById(2), "Задачи не совпадают");
    }

    @Test
    void getSubTaskById_shouldReturnSubTask() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask();
        subTask.setTitle("Test subTask title");
        subTask.setDescription("Test subTask description");
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        assertEquals(subTask, taskManager.getSubTaskById(subTask.getId()), "Задачи не совпадают");
        assertNotNull(taskManager.getSubTaskById(subTask.getId()), "Задача не найдена");
        assertNull(taskManager.getSubTaskById(10), "Найдена несуществующая задача");
    }

    @Test
    void removeTaskById_shouldRemoveTask_ifCorrectId() {
        Task testTask = new Task();
        taskManager.addTask(testTask);
        taskManager.removeTaskById(testTask.getId());
        assertNull(taskManager.getTaskById(testTask.getId()), "Найден несуществующий таск");
    }

    @Test
    void removeTaskById_shouldRemoveNothing_ifIncorrectId() {
        Task testTask = new Task();
        taskManager.addTask(testTask);
        taskManager.removeTaskById(10);
        assertEquals(1, taskManager.getTasks().size(), "Найден несуществующий таск");
    }

    @Test
    void removeEpicById_shouldRemoveEpicAndHisSubTask_ifCorrectId() {
        Epic epic = new Epic();
        SubTask subTask = new SubTask();
        subTask.setEpicId(1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.removeEpicById(1);
        assertNull(taskManager.getEpicById(1), "Найденый удаленный эпик");
        assertNull(taskManager.getSubTaskById(2), "Сабтаск не удалился после удаления эпика");
    }

    @Test
    void removeEpicById_shouldRemoveNothing_ifIncorrectId() {
        Epic epic = new Epic();
        SubTask subTask = new SubTask();
        subTask.setEpicId(1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask);
        taskManager.removeEpicById(10);
        assertEquals(1, taskManager.getEpics().size(), "Удалился эпик, который не должен удаляться");
        assertEquals(1, taskManager.getSubTasks().size(), "Удалился сабтаск, который не должен удаляться");
    }


    @Test
    void removeSubTaskById_shouldRemoveSubTaskAndHisIdFromSubTaskListInHisEpic() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask();
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        taskManager.removeSubTaskById(subTask.getId());
        assertNull(taskManager.getSubTaskById(subTask.getId()), "Найден удаленный сабтаск");
        assertFalse(taskManager.getEpicById(epic.getId()).getSubTaskIds().contains(subTask.getId()), "Из списка сабтасков эпика не удаляется id удаленного сабтаска");
    }

    @Test
    void removeSubTaskById_shouldRemoveNothing_ifIncorrectId() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask();
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        taskManager.removeSubTaskById(10);
        assertEquals(1, taskManager.getSubTasks().size(), "Удалился сабтаск, который не должен удаляться");
        assertEquals(1, taskManager.getEpics().size(), "Удалился эпик, который не должен удаляться");
    }

    @Test
    void updateTask_shouldUpdateTask() {
        Task testTask = new Task();
        testTask.setTitle("Test task Title");
        testTask.setDescription("Test task description");
        Task updatedTestTask = new Task();
        updatedTestTask.setTitle("Updatec Test Task Title");
        updatedTestTask.setDescription("Updated Test Task Description");
        updatedTestTask.setId(1);
        taskManager.addTask(testTask);
        taskManager.updateTask(updatedTestTask);
        assertEquals(updatedTestTask, taskManager.getTaskById(1), "Таск не заапдейтился");
    }

    @Test
    void updateEpic_shouldUpdateEpic() {
        Epic epic = new Epic();
        epic.setTitle("Test Epic Title");
        epic.setDescription("Test Epic Description");
        Epic updatedEpic = new Epic();
        updatedEpic.setTitle("Updated Test Epic Title");
        updatedEpic.setDescription("Updated Test Epic Description");
        updatedEpic.setId(1);
        taskManager.addEpic(epic);
        taskManager.updateEpic(updatedEpic);
        assertEquals(updatedEpic, taskManager.getEpicById(1));
    }

    @Test
    void updateSubTask_shouldUpdateSubTask() {
        Epic epic = new Epic();
        taskManager.addEpic(epic);
        SubTask subTask = new SubTask();
        subTask.setTitle("Test SubTask Title");
        subTask.setDescription("Test SubTask Description");
        subTask.setEpicId(epic.getId());
        taskManager.addSubTask(subTask);
        SubTask updatedSubTask = new SubTask();
        updatedSubTask.setTitle("Updated Test SubTask Title");
        updatedSubTask.setDescription("Updated Test SubTask Description");
        updatedSubTask.setEpicId(epic.getId());
        updatedSubTask.setId(subTask.getId());
        taskManager.updateSubTask(updatedSubTask);
        assertEquals(updatedSubTask, taskManager.getSubTaskById(subTask.getId()));
    }

    @Test
    void getSubTasksByEpicId_shouldReturnListOfSubTasks() {
        Epic epic = new Epic();
        SubTask subTask1 = new SubTask();
        subTask1.setEpicId(1);
        SubTask subTask2 = new SubTask();
        subTask2.setEpicId(1);
        SubTask subTask3 = new SubTask();
        subTask3.setEpicId(1);
        taskManager.addEpic(epic);
        taskManager.addSubTask(subTask1);
        taskManager.addSubTask(subTask2);
        taskManager.addSubTask(subTask3);
        List<SubTask> subTasksByEpicId = taskManager.getSubTasksByEpicId(1);
        assertArrayEquals(new SubTask[]{subTask1, subTask2, subTask3}, subTasksByEpicId.toArray());
    }
}