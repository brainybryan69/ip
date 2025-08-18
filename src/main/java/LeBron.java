import java.util.Scanner;
import java.util.ArrayList;

public class LeBron {
    public static void main(String[] args) {
        final String NAME = "LeBron";
        ArrayList<Task> tasks = new ArrayList<>();

        System.out.println("Hello! I'm " + NAME);
        System.out.println("What can I do for you?");
        
        Scanner scanner = new Scanner(System.in);
        String input;
        boolean isRunning = true;
        
        while (isRunning) {
            input = scanner.nextLine();
            try {
                if (input.trim().isEmpty()) {
                    throw new LeBronException("");
                }
                
                CommandType command = CommandType.parseCommand(input);
                
                switch (command) {
                    case BYE:
                        isRunning = false;
                        break;
                    case LIST:
                        if (tasks.size() == 0) {
                            System.out.println("No tasks found.");
                        } else {
                            System.out.println("Here are the tasks in your list:");
                            for (int i = 0; i < tasks.size(); i++) {
                                System.out.println((i + 1) + "." + tasks.get(i).getTypeIcon() + tasks.get(i).getStatusIcon() + " " + tasks.get(i).getFullDescription());
                            }
                        }
                        break;
                    case MARK:
                        try {
                            int taskNumber = Integer.parseInt(input.substring(5));
                            int taskIndex = taskNumber - 1;
                            if (taskNumber <= 0 || taskIndex >= tasks.size()) {
                                throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
                            }
                            Task task = tasks.get(taskIndex);
                            task.markAsDone();
                            System.out.println("Nice! I've marked this task as done:");
                            System.out.println(task.getStatusIcon() + " " + task.getDescription());
                        } catch (NumberFormatException e) {
                            throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
                        }
                        break;
                    case UNMARK:
                        try {
                            int taskNumber = Integer.parseInt(input.substring(7));
                            int taskIndex = taskNumber - 1;
                            if (taskNumber <= 0 || taskIndex >= tasks.size()) {
                                throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
                            }
                            Task task = tasks.get(taskIndex);
                            task.markAsNotDone();
                            System.out.println("OK, I've marked this task as not done yet:");
                            System.out.println(task.getStatusIcon() + " " + task.getDescription());
                        } catch (NumberFormatException e) {
                            throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
                        }
                        break;
                    case DELETE:
                        try {
                            int taskNumber = Integer.parseInt(input.substring(7));
                            int taskIndex = taskNumber - 1;
                            if (taskNumber <= 0 || taskIndex >= tasks.size()) {
                                throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
                            }
                            Task removedTask = tasks.remove(taskIndex);
                            System.out.println("Noted. I've removed the task:");
                            System.out.println(removedTask.getTypeIcon() + removedTask.getStatusIcon() + " " + removedTask.getFullDescription());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        } catch (NumberFormatException e) {
                            throw new LeBronException(ErrorType.INVALID_TASK_NUMBER.getMessage());
                        }
                        break;
                    case TODO:
                        String todoDescription = input.length() > 4 ? input.substring(5).trim() : "";
                        if (todoDescription.isEmpty()) {
                            throw new LeBronException(ErrorType.EMPTY_TODO.getMessage());
                        }
                        tasks.add(new ToDo(todoDescription));
                        System.out.println("Got it. I've added this task:");
                        System.out.println(tasks.get(tasks.size()-1).getTypeIcon() + tasks.get(tasks.size()-1).getStatusIcon() + " " + tasks.get(tasks.size()-1).getFullDescription());
                        System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        break;
                    case DEADLINE:
                        String deadlineRemaining = input.length() > 8 ? input.substring(9) : "";
                        if (deadlineRemaining.trim().isEmpty()) {
                            throw new LeBronException(ErrorType.EMPTY_DEADLINE.getMessage());
                        }
                        int byIndex = deadlineRemaining.indexOf(" /by ");
                        if (byIndex != -1) {
                            String deadlineDescription = deadlineRemaining.substring(0, byIndex).trim();
                            if (deadlineDescription.isEmpty()) {
                                throw new LeBronException(ErrorType.EMPTY_DEADLINE.getMessage());
                            }
                            String by = deadlineRemaining.substring(byIndex + 5);
                            tasks.add(new Deadline(deadlineDescription, by));
                            System.out.println("Got it. I've added this task:");
                            System.out.println(tasks.get(tasks.size()-1).getTypeIcon() + tasks.get(tasks.size()-1).getStatusIcon() + " " + tasks.get(tasks.size()-1).getFullDescription());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        } else {
                            throw new LeBronException(ErrorType.MISSING_DEADLINE_FORMAT.getMessage());
                        }
                        break;
                    case EVENT:
                        String eventRemaining = input.length() > 5 ? input.substring(6) : "";
                        if (eventRemaining.trim().isEmpty()) {
                            throw new LeBronException(ErrorType.EMPTY_EVENT.getMessage());
                        }
                        int fromIndex = eventRemaining.indexOf(" /from ");
                        int toIndex = eventRemaining.indexOf(" /to ");
                        if (fromIndex != -1 && toIndex != -1) {
                            String eventDescription = eventRemaining.substring(0, fromIndex).trim();
                            if (eventDescription.isEmpty()) {
                                throw new LeBronException(ErrorType.EMPTY_EVENT.getMessage());
                            }
                            String from = eventRemaining.substring(fromIndex + 7, toIndex);
                            String to = eventRemaining.substring(toIndex + 5);
                            tasks.add(new Event(eventDescription, from, to));
                            System.out.println("Got it. I've added this task:");
                            System.out.println(tasks.get(tasks.size()-1).getTypeIcon() + tasks.get(tasks.size()-1).getStatusIcon() + " " + tasks.get(tasks.size()-1).getFullDescription());
                            System.out.println("Now you have " + tasks.size() + " tasks in the list.");
                        } else {
                            throw new LeBronException(ErrorType.MISSING_EVENT_FORMAT.getMessage());
                        }
                        break;
                    case UNKNOWN:
                        throw new LeBronException(ErrorType.UNKNOWN_COMMAND.getMessage());
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
