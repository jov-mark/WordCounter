import directory.DirectoryCrawlerThread;
import job.JobQueue;
import main.CLIThread;

public class App {

    public static void main(String[] args) {
        JobQueue jobQueue = new JobQueue();
        DirectoryCrawlerThread crawlerThread = new DirectoryCrawlerThread(jobQueue);
        CLIThread cli = new CLIThread(crawlerThread, jobQueue);
        cli.start();
        crawlerThread.start();


    }

}
