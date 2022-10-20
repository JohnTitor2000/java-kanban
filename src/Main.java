public class Main {

    public static void main(String[] args) {
        Manager manager = new Manager();
        manager.addTask(new Task("Приготовить чачу", "Приготовь чачу ежи че не понятно", Status.NEW));
        System.out.println(manager.getTaskById(1));
        manager.addTask(new Task("Убраться в комнате", "Бардак блин", Status.NEW));
        System.out.println(manager.getTaskById(2));
        manager.addEpicTask(new Epic("Звоевать мир", "ДА ДАВААЙ ДАВАЙ ДААААА"));
        System.out.println(manager.getEpicById(3));
        manager.addSubTask(new SubTask(3, "Узурпировать власть в стране", "Диктуй", Status.NEW));
        manager.addSubTask(new SubTask(3, "Вторгнуться в украину", "Воюй", Status.NEW));
        manager.addEpicTask(new Epic("Попить чай", "Попей чай"));
        manager.addSubTask(new SubTask(6, "Заварить чай", "Азерчай", Status.NEW));
        for (Task task : manager.getTaskList()) {
            System.out.println(task);
        }
        for (Epic epic : manager.getEpicList()) {
            System.out.println(epic);
        }
        for (SubTask subTask : manager.getSubTaskList()) {
            System.out.println(subTask);
        }
        System.out.println();
        manager.updateSubTask(new SubTask(3, "Узурпировать власть в стране", "Диктуй", Status.IN_PROGRESS), 4);
        manager.updateSubTask(new SubTask(6, "Заварить чай", "Азерчай", Status.DONE), 7);
        manager.updateTask(new Task("Приготовить чачу", "Приготовь чачу ежи че не понятно", Status.DONE), 1);
        for (Task task : manager.getTaskList()) {
            System.out.println(task);
        }
        for (Epic epic : manager.getEpicList()) {
            System.out.println(epic);
        }
        for (SubTask subTask : manager.getSubTaskList()) {
            System.out.println(subTask);
        }
        System.out.println();
        manager.removeEpicById(3);
        manager.removeTaskById(1);
        manager.removeSubTuskById(7);
        for (Task task : manager.getTaskList()) {
            System.out.println(task);
        }
        for (Epic epic : manager.getEpicList()) {
            System.out.println(epic);
        }
        for (SubTask subTask : manager.getSubTaskList()) {
            System.out.println(subTask);
        }
    }
}
