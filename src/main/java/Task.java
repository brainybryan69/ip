public abstract class Task {
    protected String description;
    protected TaskStatus status;
    protected TaskType type;
    
    public Task(String description, TaskType type) {
        this.description = description;
        this.status = TaskStatus.NOT_DONE;
        this.type = type;
    }
    
    public String getDescription() {
        return description;
    }
    
    public boolean isDone() {
        return status == TaskStatus.DONE;
    }
    
    public void markAsDone() {
        status = TaskStatus.DONE;
    }
    
    public void markAsNotDone() {
        status = TaskStatus.NOT_DONE;
    }
    
    public String getStatusIcon() {
        return status.getIcon();
    }
    
    public String getTypeIcon() {
        return type.getIcon();
    }
    
    public abstract String getFullDescription();
}