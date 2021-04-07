package file;

import job.Job;
import job.JobQueue;
import job.JobType;

import java.io.File;
import java.util.Objects;

public class DirectoryCrawlerThread extends Thread{
    private String corpus;
    private String path;
    private int sleepTime;

    private JobQueue jobQueue = JobQueue.getInstance();

    public DirectoryCrawlerThread(String path, String corpus, int sleepTime){
        this.path = path;
        this.corpus = corpus;
        this.sleepTime = sleepTime;
    }

    @Override
    public void run() {
        try{
            System.out.println(System.currentTimeMillis()+": crawling in "+path);
            File arg = new File(System.getProperty("user.dir")+"/"+path);
            for(File f: Objects.requireNonNull(arg.listFiles()))
                crawl(f);
            Thread.sleep(sleepTime);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void crawl(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(file.getName().startsWith(corpus))
                jobQueue.appendJob(new Job(JobType.FILE,file.getPath()));
            for(File f: Objects.requireNonNull(files)){
                crawl(f);
            }
        }
    }
}
