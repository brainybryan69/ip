public class Deadline extends Task {
    private String by;
    
    public Deadline(String description, String by) {
        super(description, TaskType.DEADLINE);
        this.by = by;
    }
    
    @Override
    public String getFullDescription() {
        return description + " (by: " + by + ")";
    }
}