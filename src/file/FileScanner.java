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
    private ForkJoinPool forkJoinPool;

    public FileScanner(){
        forkJoinPool = new ForkJoinPool();
    }

    public void scanDirectory(String dirPath){
        File file = new File(dirPath);
        File[] files = file.listFiles();

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
