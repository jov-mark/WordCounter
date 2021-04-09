
import config.ConfigReader;
import file.DirectoryCrawlerThread;
import file.FileScanner;
import job.Job;
import job.JobDispatcherThread;
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

        FileScanner fileScanner = new FileScanner();
        JobDispatcherThread jobDispatcherThread = new JobDispatcherThread(jobQueue, fileScanner);
        jobDispatcherThread.start();

        DirectoryCrawlerThread directoryCrawlerThread = new DirectoryCrawlerThread(jobQueue);
        directoryCrawlerThread.start();

        ResultRetrieverPool resultRetriever = ResultRetrieverPool.getInstance();

        boolean work = true;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (work) {
            String[] cmds = scanner.nextLine().split(" ");
            switch (cmds[0]){
                case "ad":
                    directoryCrawlerThread.addPath(cmds[1]); break;
                case "aw":
                    System.out.println("TODO: web component");  break;
                case "get":
                    if(cmds.length<2 || !resultRetriever.get(cmds[1]))
                        System.out.println("Invalid arguments!");
                    break;
                case "query":
                    System.out.println("TODO: query results");  break;
                case "cfs":
                    resultRetriever.clearSummary("file");   break;
                case "cws":
                    resultRetriever.clearSummary("web");    break;
                case "stop":
                    scanner.close();
                    work=false; break;
                default:
                    System.out.println("Invalid input!");
            }
        }
        System.out.println("Stopping CLI thread..");
    }

}
