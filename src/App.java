
import config.ConfigReader;
import file.DirectoryCrawlerThread;
import file.FileScanner;
import job.Job;
import job.JobDispatcherThread;
import job.JobType;
import result.ResultRetriever;
import result.ResultRetrieverPool;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class App {

    public static void main(String[] args) {
        try {
            ConfigReader.getConfig();
        } catch (IOException e) {
            e.printStackTrace();
        }

        BlockingQueue<Job> jobQueue = new ArrayBlockingQueue<>(20);

        ResultRetriever resultRetriever = new ResultRetriever();
        FileScanner fileScanner = new FileScanner(resultRetriever);
        JobDispatcherThread jobDispatcherThread = new JobDispatcherThread(jobQueue, fileScanner);
        jobDispatcherThread.start();

        DirectoryCrawlerThread directoryCrawlerThread = new DirectoryCrawlerThread(jobQueue);
        directoryCrawlerThread.start();

        boolean work = true;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (work) {
            String[] cmds = scanner.nextLine().split(" ");
            switch (cmds[0]){
                case "ad":
//                    TODO: handle wrong input or non dir argument
                    directoryCrawlerThread.addPath(cmds[1]); break;

                case "aw":
                    System.out.println("TODO: web component");  break;

                case "get":
                    boolean invalid = cmds.length<2;
                    if(!invalid && !cmds[1].startsWith("web")){
                        invalid = !resultRetriever.get(cmds[1]);
                    }
                    if(invalid)
                        System.out.println("Invalid arguments!");
                    break;

                case "query":
                    System.out.println("TODO: query results");  break;
                case "cfs":
                    resultRetriever.clearSummary(JobType.FILE);   break;
                case "cws":
                    resultRetriever.clearSummary(JobType.WEB);  break;
                case "stop":
                    scanner.close();
                    directoryCrawlerThread.stopThread();
                    jobQueue.add(new Job(null,"",true));
                    fileScanner.shutdown();
                    resultRetriever.shutdown();
                    work=false; break;
                default:
                    System.out.println("Invalid input!");
            }
        }
        System.out.println("Stopping CLI thread..");
    }

}
