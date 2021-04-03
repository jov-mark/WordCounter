package main;

import file.DirectoryCrawlerThread;
import job.JobQueue;

import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class CLIThread extends Thread{
    private Map<String, Object> props;
    private DirectoryCrawlerThread dirCrawler;
    private JobQueue jobQueue = JobQueue.getInstance();

    private Scanner scanner;

    public CLIThread(DirectoryCrawlerThread dirCrawler){
        this.dirCrawler = dirCrawler;
    }

    @Override
    public void run() {
        if(readConfig()) {
            scanner = new Scanner(new InputStreamReader(System.in));
            while (true) {
                String input = scanner.nextLine();
                if (input.equals("stop")) {
                    scanner.close();
                    break;
                } else {
                    parseInput(input);
                }
            }
        }
        System.out.println("Stopping CLI thread..");
    }

    private void parseInput(String input){
        String[] cmds = input.split(" ");
        switch (cmds[0]){
            case "ad":
                dirCrawler.setPath(cmds[1]); break;
            case "aw":
                System.out.println("web component"); break;
            case "get":
                System.out.println("TODO: get results"); break;
            case "query":
                System.out.println("TODO: query results"); break;
            case "cfs":
                System.out.println("TODO: clear file summary"); break;
            case "cws":
                System.out.println("TODO: clear web summary"); break;
            default:
                System.out.println("Invalid input!");
        }
    }

    private boolean readConfig(){
        ConfigReader configReader = new ConfigReader();
        try {
            props = configReader.getConfig();
            dirCrawler.setCorpus(props.get("file_corpus_prefix").toString());
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
