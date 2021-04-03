package job;

import java.util.ArrayList;
import java.util.List;

public class JobQueue{
    private List<String> jobs;

    public JobQueue(){
        jobs = new ArrayList<>();
    }

    public void appendJob(String job){
        jobs.add(job);
    }
}
