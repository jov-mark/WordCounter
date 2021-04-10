package file;

import config.Config;
import job.Job;
import job.JobType;

import java.io.File;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DirectoryCrawlerThread extends Thread{
    private String corpus;
    private int sleepTime;
    private BlockingQueue<Job> jobQueue;
    private List<String> directories;
    private Map<String,Long> lastModified;

    public DirectoryCrawlerThread(BlockingQueue<Job> jobQueue){
        this.directories = new ArrayList<>();
        this.corpus = Config.getCorpus();
        this.sleepTime = Config.getSleepTime()*5;
        this.jobQueue = jobQueue;
        this.lastModified = new HashMap<>();
    }

    public void addPath(String path){
        this.directories.add(path);
    }

    @Override
    public void run() {
        try{
            while (!Thread.interrupted()){
                for(String path: directories){
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
            if(file.getName().startsWith(corpus)){
                long current = file.lastModified();
                String path = file.getPath();
                if(lastModified.get(path)==null || lastModified.get(path)!=current){
                    lastModified.put(path,current);
                    jobQueue.add(new Job(JobType.FILE,path));
                }
            }
            for(File f: Objects.requireNonNull(files)){
                crawl(f);
            }
        }
    }
}
