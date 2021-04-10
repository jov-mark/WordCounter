package file;

import result.ResultRetriever;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class FileScanner {
    private ResultRetriever resultRetriever;
    private ForkJoinPool forkJoinPool;

    public FileScanner(ResultRetriever resultRetriever){
        forkJoinPool = new ForkJoinPool();
        this.resultRetriever = resultRetriever;
    }

    public void scanDirectory(String dirPath){
        File file = new File(dirPath);
        File[] files = file.listFiles();

        Future<Map<String,Integer>> result = forkJoinPool.submit(new FileScanTask(file, Arrays.asList(files)));
        resultRetriever.setFileResults(file.getName(),result);
    }

    public void shutdown(){
        forkJoinPool.shutdown();
    }
}
