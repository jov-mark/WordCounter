package file;

import result.ResultRetrieverPool;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class FileScanner {
    private ResultRetrieverPool retrieverPool = ResultRetrieverPool.getInstance();

    public FileScanner(String dirPath){
        File file = new File(dirPath);
        File[] files = file.listFiles();

        ForkJoinPool forkJoinPool = new ForkJoinPool();

        Future<Map<String,Integer>> result = forkJoinPool.submit(new FileScanTask(file, Arrays.asList(files)));
        try {
            Map<String,Integer> res = result.get();
            System.out.println(file.getName()+":");
            for(Map.Entry e: res.entrySet()){
                System.out.println(e.getKey()+":"+e.getValue());
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }
}
