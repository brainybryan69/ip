import java.util.Scanner;

public class LeBron {
    public static void main(String[] args) {
        final String NAME = "LeBron";
        Task[] tasks = new Task[100];
        int taskCount = 0;

        System.out.println("Hello! I'm " + NAME);
        System.out.println("What can I do for you?");
        
        Scanner scanner = new Scanner(System.in);
        String input;
        
        while (true) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            } else if (input.equals("list")) {
                if (taskCount == 0) {
                    System.out.println("No tasks found.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i].getStatusIcon() + " " + tasks[i].getDescription());
                    }
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int taskIndex = Integer.parseInt(input.substring(5)) - 1;
                    if (taskIndex >= 0 && taskIndex < taskCount) {
                        Task task = tasks[taskIndex];
                        task.markAsDone();
                        System.out.println("Nice! I've marked this task as done:");
                        System.out.println(task.getStatusIcon() + " " + task.getDescription());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int taskIndex = Integer.parseInt(input.substring(7)) - 1;
                    if (taskIndex >= 0 && taskIndex < taskCount) {
                        Task task = tasks[taskIndex];
                        task.markAsNotDone();
                        System.out.println("OK, I've marked this task as not done yet:");
                        System.out.println(task.getStatusIcon() + " " + tasks[taskIndex].getDescription());
                    }
                } catch (NumberFormatException e) {
                    System.out.println("Invalid task number.");
                }
            } else {
                tasks[taskCount] = new Task(input);
                taskCount++;
                System.out.println("added: " + input);
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}
