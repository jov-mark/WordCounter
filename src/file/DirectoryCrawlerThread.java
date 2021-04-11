package file;

import config.Config;
import job.Job;
import job.JobType;

import java.io.File;
import java.util.*;
import java.util.concurrent.BlockingQueue;

public class DirectoryCrawlerThread extends Thread{
    private BlockingQueue<Job> jobQueue;
    private List<String> directories;
    private Map<String,Long> lastModified;

    private boolean working;

    public DirectoryCrawlerThread(BlockingQueue<Job> queue){
        working = true;
        jobQueue = queue;
        directories = new ArrayList<>();
        lastModified = new HashMap<>();
    }

    public void addPath(String path){
        this.directories.add(path);
    }

    @Override
    public void run() {
        try{
            while (working){
                for(String path: directories){
                    File dir = new File(System.getProperty("user.dir")+"/"+path);
                    for(File f: Objects.requireNonNull(dir.listFiles()))
                        crawl(f);
                }
                Thread.sleep(Config.getSleepTime());
            }
            System.out.println("Stopping directory crawler..");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void crawl(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(file.getName().startsWith(Config.getCorpus())){
                long current = file.lastModified();
                String path = file.getPath();
                if(lastModified.get(path)==null || lastModified.get(path)!=current){
                    lastModified.put(path,current);
                    jobQueue.add(new Job(JobType.FILE,path,false));
                }
            }
            for(File f: Objects.requireNonNull(files)){
                crawl(f);
            }
        }
    }

    public void stopThread() {
        this.working = false;
    }
}
