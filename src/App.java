import file.DirectoryCrawlerThread;
import file.FileScannerThreadPool;
import job.JobDispatcherThread;
import job.JobQueue;
import main.CLIThread;
import result.ResultRetrieverPool;

public class App {

    public static void main(String[] args) {
        JobQueue jobQueue = new JobQueue();
        ResultRetrieverPool resultRetriever = new ResultRetrieverPool();
        FileScannerThreadPool fileScanner = new FileScannerThreadPool();

        JobDispatcherThread jobDispatcherThread = new JobDispatcherThread(fileScanner);
        DirectoryCrawlerThread crawlerThread = new DirectoryCrawlerThread();
        CLIThread cli = new CLIThread(crawlerThread);

        jobDispatcherThread.start();
        crawlerThread.start();
        cli.start();


    }

}
