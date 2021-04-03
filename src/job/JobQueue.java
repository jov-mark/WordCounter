package job;

import java.util.ArrayList;
import java.util.List;

public class JobQueue{
    private static JobQueue instance = null;
    private List<Job> jobs;

    public static JobQueue getInstance(){
        if(instance == null){
            instance = new JobQueue();
        }
        return instance;
    }
    public JobQueue(){
        jobs = new ArrayList<>();
    }

    public void appendJob(Job job){
        jobs.add(job);
    }

    public Job getJob(){
        Job job = jobs.get(0);
        jobs.remove(0);
        return job;
    }

    public boolean isEmpty(){
        return jobs.isEmpty();
    }
}
