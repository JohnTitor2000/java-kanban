//package ru.yandex.practicum.tasktracker;
//
//import ru.yandex.practicum.tasktracker.manger.TaskManager;
//import ru.yandex.practicum.tasktracker.model.Epic;
//import ru.yandex.practicum.tasktracker.model.Status;
//import ru.yandex.practicum.tasktracker.model.SubTask;
//import ru.yandex.practicum.tasktracker.model.Task;
//
//public class Main {
//
//    public static void main(String[] args) {
//        TaskManager manager = new TaskManager();
//        manager.addTask(new Task("Приготовить завтрак", "Приготовить завтрак", Status.NEW));
//        System.out.println(manager.getTaskById(1));
//        manager.addTask(new Task("Убраться в комнате", "Убраться в квартире", Status.NEW));
//        System.out.println(manager.getTaskById(2));
//        //manager.addEpicTask(new Epic("Отвести кота к ветиренару", "Подавился"));
//        System.out.println(manager.getEpicById(3));
//        manager.addSubTask(new SubTask(3, "Взять кота", "Бери", Status.NEW));
//        manager.addSubTask(new SubTask(3, "Отвезти к ветеринару", "Пусть лечит", Status.NEW));
//        //manager.addEpicTask(new Epic("Попить чай", "Попей чай"));
//        manager.addSubTask(new SubTask(6, "Заварить чай", "Азерчай", Status.NEW));
//        for (Task task : manager.getTasks()) {
//            System.out.println(task);
//        }
//        for (Epic epic : manager.getEpics()) {
//            System.out.println(epic);
//        }
//        for (SubTask subTask : manager.getSubTasks()) {
//            System.out.println(subTask);
//        }
//        System.out.println();
//        manager.updateSubTask(new SubTask(3, "Узурпировать власть в стране", "Диктуй", Status.IN_PROGRESS), 4);
//        manager.updateSubTask(new SubTask(6, "Заварить чай", "Азерчай", Status.DONE), 7);
//        manager.updateTask(new Task("Приготовить чачу", "Приготовь чачу ежи че не понятно", Status.DONE), 1);
//        for (Task task : manager.getTasks()) {
//            System.out.println(task);
//        }
//        for (Epic epic : manager.getEpics()) {
//            System.out.println(epic);
//        }
//        for (SubTask subTask : manager.getSubTasks()) {
//            System.out.println(subTask);
//        }
//        System.out.println();
//        manager.removeEpicById(3);
//        manager.removeTaskById(1);
//        manager.removeSubTaskById(7);
//        for (Task task : manager.getTasks()) {
//            System.out.println(task);
//        }
//        for (Epic epic : manager.getEpics()) {
//            System.out.println(epic);
//        }
//        for (SubTask subTask : manager.getSubTasks()) {
//            System.out.println(subTask);
//        }
//    }
//}
//