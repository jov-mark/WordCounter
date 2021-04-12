
import config.ConfigReader;
import file.DirectoryCrawlerThread;
import file.FileScanner;
import job.Job;
import job.JobDispatcherThread;
import job.JobType;
import result.Result;
import result.ResultRetriever;

import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
        boolean invalid = false;
        Scanner scanner = new Scanner(new InputStreamReader(System.in));
        while (work) {
            String[] cmds = scanner.nextLine().split(" ");
            switch (cmds[0]){
                case "ad":
                    if(cmds.length<2)
                        System.out.println("Invalid input!");
                    else {
                        String path = System.getProperty("user.dir") + "\\" + cmds[1];
                        if (!Files.exists(Paths.get(path)))
                            System.out.println("No directory for given path");
                        else
                            directoryCrawlerThread.addPath(path);
                    }
                    break;

                case "aw":
                    System.out.println("TODO: web component");  break;

                case "get":
                    if(cmds.length<2) {
                        System.out.println("Invalid input!");
                        break;
                    }
                    String[] arg = cmds[1].split("\\|");
                    if(arg.length<2 || !(arg[0].equals("file") || arg[0].equals("web"))) {
                        System.out.println("Invalid arguments!");
                        break;
                    }
                    if(!cmds[1].startsWith("web")) {
                        if(arg[1].equals("summary")){
                            List<Result> result = resultRetriever.getSummary();
                            if(!result.isEmpty()){
                                for(Result r: result)
                                    System.out.println(r);
                            }
                        }else{
                            Result r = resultRetriever.get(args[1]);
                            if(r==null)
                                System.out.println("No corpus with that name!");
                            else
                                System.out.println(r);
                        }
                    }
                    break;

                case "query":
                    System.out.println("TODO: query results");  break;

                case "cfs":
                    if(cmds.length>1)
                        System.out.println("Invalid input!");
                    else
                        resultRetriever.clearSummary(JobType.FILE);
                    break;

                case "cws":
                    if(cmds.length>1)
                        System.out.println("Invalid input!");
                    else
                        resultRetriever.clearSummary(JobType.WEB);
                    break;

                case "stop":
                    if(cmds.length>1)
                        System.out.println("Invalid input!");
                    else {
                        scanner.close();
                        directoryCrawlerThread.stopThread();
                        jobQueue.add(new Job(null, "", true));
                        fileScanner.shutdown();
                        work = false;
                    }
                    break;
                default:
                    System.out.println("Invalid input!");
            }
        }
        System.out.println("Stopping CLI thread..");
    }

}
