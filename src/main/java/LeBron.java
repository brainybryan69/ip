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
            try {
                if (input.trim().isEmpty()) {
                    throw new LeBronException("");
                }
                if (input.equals("bye")) {
                    break;
                } else if (input.equals("list")) {
                if (taskCount == 0) {
                    System.out.println("No tasks found.");
                } else {
                    System.out.println("Here are the tasks in your list:");
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + "." + tasks[i].getTypeIcon() + tasks[i].getStatusIcon() + " " + tasks[i].getFullDescription());
                    }
                }
            } else if (input.startsWith("mark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(5));
                    int taskIndex = taskNumber - 1;
                    if (taskNumber <= 0 || taskIndex >= taskCount) {
                        throw new LeBronException("Invalid task number.");
                    }
                    Task task = tasks[taskIndex];
                    task.markAsDone();
                    System.out.println("Nice! I've marked this task as done:");
                    System.out.println(task.getStatusIcon() + " " + task.getDescription());
                } catch (NumberFormatException e) {
                    throw new LeBronException("Invalid task number.");
                }
            } else if (input.startsWith("unmark ")) {
                try {
                    int taskNumber = Integer.parseInt(input.substring(7));
                    int taskIndex = taskNumber - 1;
                    if (taskNumber <= 0 || taskIndex >= taskCount) {
                        throw new LeBronException("Invalid task number.");
                    }
                    Task task = tasks[taskIndex];
                    task.markAsNotDone();
                    System.out.println("OK, I've marked this task as not done yet:");
                    System.out.println(task.getStatusIcon() + " " + task.getDescription());
                } catch (NumberFormatException e) {
                    throw new LeBronException("Invalid task number.");
                }
            } else if (input.equals("todo") || input.startsWith("todo ")) {
                String description = input.length() > 4 ? input.substring(5).trim() : "";
                if (description.isEmpty()) {
                    throw new LeBronException("The description of a todo cannot be empty.");
                }
                tasks[taskCount] = new ToDo(description);
                taskCount++;
                System.out.println("Got it. I've added this task:");
                System.out.println(tasks[taskCount-1].getTypeIcon() + tasks[taskCount-1].getStatusIcon() + " " + tasks[taskCount-1].getFullDescription());
                System.out.println("Now you have " + taskCount + " tasks in the list.");
            } else if (input.equals("deadline") || input.startsWith("deadline ")) {
                String remaining = input.length() > 8 ? input.substring(9) : "";
                if (remaining.trim().isEmpty()) {
                    throw new LeBronException("The description of a deadline cannot be empty.");
                }
                int byIndex = remaining.indexOf(" /by ");
                if (byIndex != -1) {
                    String description = remaining.substring(0, byIndex).trim();
                    if (description.isEmpty()) {
                        throw new LeBronException("The description of a deadline cannot be empty.");
                    }
                    String by = remaining.substring(byIndex + 5);
                    tasks[taskCount] = new Deadline(description, by);
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount-1].getTypeIcon() + tasks[taskCount-1].getStatusIcon() + " " + tasks[taskCount-1].getFullDescription());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else {
                    throw new LeBronException("Please specify a deadline with /by");
                }
            } else if (input.equals("event") || input.startsWith("event ")) {
                String remaining = input.length() > 5 ? input.substring(6) : "";
                if (remaining.trim().isEmpty()) {
                    throw new LeBronException("The description of an event cannot be empty.");
                }
                int fromIndex = remaining.indexOf(" /from ");
                int toIndex = remaining.indexOf(" /to ");
                if (fromIndex != -1 && toIndex != -1) {
                    String description = remaining.substring(0, fromIndex).trim();
                    if (description.isEmpty()) {
                        throw new LeBronException("The description of an event cannot be empty.");
                    }
                    String from = remaining.substring(fromIndex + 7, toIndex);
                    String to = remaining.substring(toIndex + 5);
                    tasks[taskCount] = new Event(description, from, to);
                    taskCount++;
                    System.out.println("Got it. I've added this task:");
                    System.out.println(tasks[taskCount-1].getTypeIcon() + tasks[taskCount-1].getStatusIcon() + " " + tasks[taskCount-1].getFullDescription());
                    System.out.println("Now you have " + taskCount + " tasks in the list.");
                } else {
                    throw new LeBronException("Please specify event time with /from and /to");
                }
            } else {
                throw new LeBronException("Sorry! I don't know what that means :(");
            }
            } catch (LeBronException e) {
                if (!e.getMessage().isEmpty()) {
                    System.out.println(e.getMessage());
                }
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}
