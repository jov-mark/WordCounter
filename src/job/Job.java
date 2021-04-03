package job;

public class Job {
    private JobType type;
    private String path;

    public Job(JobType type, String path) {
        this.type = type;
        this.path = path;
    }

    public String getPath() {
        return path;
    }

    public JobType getType() {
        return type;
    }

    @Override
    public String toString() {
        return type.toString()+": "+path;
    }
}
