import java.util.Scanner;

public class LeBron {
    public static void main(String[] args) {
        String name = "LeBron";
        System.out.println("Hello! I'm " + name);
        System.out.println("What can I do for you?");
        
        Scanner scanner = new Scanner(System.in);
        String input;
        
        while (true) {
            input = scanner.nextLine();
            if (input.equals("bye")) {
                break;
            }
            System.out.println(input);
        }
        
        System.out.println("Bye. Hope to see you again soon!");
        scanner.close();
    }
}
