package file;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;
import java.util.concurrent.RecursiveTask;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FileScanTask extends RecursiveTask<Map<String,Integer>> {
    private File directory;
    private List<File> files;
    private final int file_scanning_size_limit = 10;
    private List<String> keywords = new ArrayList<>();

    public FileScanTask(File directory, List<File> files){
        this.directory = directory;
        this.files = files;
        keywords.add("one");
        keywords.add("two");
        keywords.add("three");
    }

    @Override
    protected Map<String,Integer> compute() {
        Map<String,Integer> result = new HashMap<>();
        int index = 0;
        int sumSize = 0;
        for(; index<files.size(); index++){
            if(sumSize < file_scanning_size_limit)
                sumSize += files.get(index).length();
            else
                break;
        }
        for(int i=0; i<index; i++)
            result.putAll(scanFile(files.get(i)));

        if(index<files.size()){
            int mid = ((files.size() - index) / 2) + index;
            FileScanTask left = new FileScanTask(directory, files.subList(index,mid));
            FileScanTask right = new FileScanTask(directory, files.subList(mid,files.size()));

            left.fork();
            Map<String,Integer> rightResult = right.compute();
            Map<String,Integer> leftResult = left.join();
            merge(result,leftResult);
            merge(result,rightResult);
        }
        return result;
    }

    private void merge(Map<String,Integer> map, Map<String,Integer> m2p){
        m2p.forEach((key, value) -> {
            map.merge(key, value, Integer::sum);
        });
    }

    private Map<String,Integer> scanFile(File f){
        Map<String, Integer> res = new HashMap<>();
        if(f.length()!=0){
            try {
                StringBuilder lines = new StringBuilder();
                Scanner sc = new Scanner(new FileInputStream(f.getPath()));
                while(sc.hasNext()){
                    String[] words = sc.nextLine().split(" ");
                    for(String w: words){
                        if(keywords.contains(w)){
                            res.put(w, (res.get(w)==null)?1:res.get(w)+1);
                        }
                    }
                }
                sc.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }
        return res;
    }
}
