package file;

import config.Config;
import job.Job;
import job.JobType;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.BlockingQueue;

public class DirectoryCrawlerThread extends Thread{
    private String corpus;
    private int sleepTime;
    private BlockingQueue<Job> jobQueue;
    private List<String> directories;

    public DirectoryCrawlerThread(BlockingQueue<Job> jobQueue){
        this.directories = new ArrayList<>();
        this.corpus = Config.getCorpus();
        this.sleepTime = Config.getSleepTime()*10;
        this.jobQueue = jobQueue;
    }

    public void addPath(String path){
        this.directories.add(path);
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                for(String path: directories){
                    System.out.println(System.currentTimeMillis()+": crawling in "+path);
                    File dir = new File(System.getProperty("user.dir")+"/"+path);
                    for(File f: Objects.requireNonNull(dir.listFiles()))
                        crawl(f);
                }
                Thread.sleep(sleepTime);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void crawl(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(file.getName().startsWith(corpus))
                jobQueue.add(new Job(JobType.FILE,file.getPath()));
            for(File f: Objects.requireNonNull(files)){
                crawl(f);
            }
        }
    }
}
