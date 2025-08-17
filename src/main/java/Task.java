public abstract class Task {
    protected String description;
    protected boolean isDone;
    
    public Task(String description) {
        this.description = description;
        this.isDone = false;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isDone() {
        return isDone;
    }
    
    public void markAsDone() {
        isDone = true;
    }
    
    public void markAsNotDone() {
        isDone = false;
    }
    
    public String getStatusIcon() {
        return isDone ? "[X]" : "[ ]";
    }
    
    public abstract String getTypeIcon();
    
    public abstract String getFullDescription();
}