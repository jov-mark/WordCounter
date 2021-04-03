package directory;

import job.JobQueue;

import java.io.File;
import java.util.Objects;

public class DirectoryCrawlerThread extends Thread{
    private String corpus="";
    private String path="";

    private JobQueue jobQueue;

    public DirectoryCrawlerThread(JobQueue jobQueue){
        this.jobQueue = jobQueue;
    }

    @Override
    public void run() {
        try{
            while(corpus.equals("")){
                Thread.sleep(100);
            }
            while(path.equals("")){
                Thread.sleep(4000);
            }
            File arg = new File(System.getProperty("user.dir")+"/"+path);
            for(File f: Objects.requireNonNull(arg.listFiles()))
                crawl(f);

            System.out.println("Stopping directory crawler..");
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void crawl(File file){
        if(file.isDirectory()){
            File[] files = file.listFiles();
            if(file.getName().startsWith(corpus)) {
                for (File f : Objects.requireNonNull(files)) {
                    jobQueue.appendJob(f.getPath());
                }
            }
            for(File f: Objects.requireNonNull(files)){
                crawl(f);
            }
        }
    }

    public void setPath(String path){
        if(path.equals("")) return;
        this.path = path;
    }

    public void setCorpus(String corpus){
        this.corpus = corpus;
    }
}
