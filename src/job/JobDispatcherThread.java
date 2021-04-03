package job;

import file.FileScannerThreadPool;

public class JobDispatcherThread extends Thread{

    private JobQueue jobQueue = JobQueue.getInstance();
    private FileScannerThreadPool fileScanner = new FileScannerThreadPool();

    public JobDispatcherThread(FileScannerThreadPool fileScanner){
        this.fileScanner = fileScanner;
    }

    @Override
    public void run() {
        try{
            while(true){
                if(jobQueue.isEmpty()) {
                    System.out.println("Waiting for jobs..");
                    Thread.sleep(5000);
                }
                else{
                    Job job = jobQueue.getJob();
                    if(job.getType()==JobType.FILE){
                        fileScanner.scanDirectory(job.getPath());
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
