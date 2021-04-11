package job;

import file.FileScanner;
import java.util.concurrent.BlockingQueue;

public class JobDispatcherThread extends Thread{

    private BlockingQueue<Job> jobQueue;
    private FileScanner fileScanner;

    public JobDispatcherThread(BlockingQueue<Job> jobQueue, FileScanner fileScanner){
        this.jobQueue = jobQueue;
        this.fileScanner = fileScanner;
    }

    @Override
    public void run() {
        try{
            while(true){
                Job job = null;
                try {
                    job = jobQueue.take();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                if(job!=null){
                    if(job.isPoison()){
                        break;
                    }
                    if(job.getType()==JobType.FILE){
                        fileScanner.scanDirectory(job.getPath());
                    }
                }
            }
            System.out.println("Stopping job dispatcher..");
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
