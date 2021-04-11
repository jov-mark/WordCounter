package job;

public class Job {
    private JobType type;
    private String path;
    private boolean poison;

    public Job(JobType type, String path, boolean poison) {
        this.type = type;
        this.path = path;
        this.poison = poison;
    }

    public String getPath() {
        return path;
    }

    public JobType getType() {
        return type;
    }

    public boolean isPoison(){
        return poison;
    }

    @Override
    public String toString() {
        return type.toString()+": "+path;
    }
}
