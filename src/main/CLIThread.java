package main;

import file.DirectoryCrawlerThread;
import file.FileScannerThreadPool;
import job.JobQueue;
import result.ResultRetrieverPool;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Map;
import java.util.Scanner;

public class CLIThread extends Thread{
    private ResultRetrieverPool resultRetriever = ResultRetrieverPool.getInstance();
    private FileScannerThreadPool fileScanner;
    private Map<String, Object> props;

    public CLIThread(FileScannerThreadPool fileScanner){
        this.fileScanner = fileScanner;
    }

    @Override
    public void run() {
        if(readConfig()) {
            Scanner scanner = new Scanner(new InputStreamReader(System.in));
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
                DirectoryCrawlerThread dirCrawler = new DirectoryCrawlerThread(
                        cmds[1],
                        props.get("file_corpus_prefix").toString(),
                        (Integer)props.get("dir_crawler_sleep_time"));
                dirCrawler.start(); break;
            case "aw":
                System.out.println("TODO: web component"); break;
            case "get":
                if(cmds.length<2 || !resultRetriever.get(cmds[1]))
                    System.out.println("Invalid arguments!");
                break;
            case "query":
                System.out.println("TODO: query results"); break;
            case "cfs":
                resultRetriever.clearSummary("file"); break;
            case "cws":
                resultRetriever.clearSummary("web"); break;
            default:
                System.out.println("Invalid input!");
        }
    }

    private boolean readConfig(){
        ConfigReader configReader = new ConfigReader();
        try {
            props = configReader.getConfig();
            fileScanner.setKeywords(props.get("keywords").toString());
            fileScanner.setSizeLimit((Integer)props.get("file_scanning_size_limit"));
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
