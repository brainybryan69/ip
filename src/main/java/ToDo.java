public class ToDo extends Task {
    public ToDo(String description) {
        super(description);
    }
    
    @Override
    public String getTypeIcon() {
        return "[T]";
    }
    
    @Override
    public String getFullDescription() {
        return description;
    }
}