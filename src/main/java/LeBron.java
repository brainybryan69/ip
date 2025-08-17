import java.util.Scanner;

public class LeBron {
    public static void main(String[] args) {
        final String NAME = "LeBron";
        String[] TASKS = new String[100];
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
                    for (int i = 0; i < taskCount; i++) {
                        System.out.println((i + 1) + ". " + TASKS[i]);
                    }
                }
            } else {
                TASKS[taskCount] = input;
                taskCount++;
                System.out.println("added: " + input);
            }
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}
