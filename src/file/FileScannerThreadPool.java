package file;

import result.Result;
import result.ResultRetrieverPool;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FileScannerThreadPool {
    private File file;
    private Result result;
    private ResultRetrieverPool resultRetriever = ResultRetrieverPool.getInstance();

    public void scanDirectory(String path){
        file = new File(path);
        result = new Result("file", file.getName());

        for(File f: Objects.requireNonNull(file.listFiles()))
            scanFile(f);

        resultRetriever.saveResult(result);
    }

    private void scanFile(File f){
        try {
            String[] words = null;
            StringBuilder lines = new StringBuilder();
            Scanner sc = new Scanner(new FileInputStream(f.getPath()));
            while(sc.hasNext()){
                lines.append(sc.nextLine());
            }
            sc.close();

            if(lines.length()!=0)
                words = lines.toString().split(" ");

            if(words!=null)
                parseWords(words);

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseWords(String[] words){
        Map<String, Integer> res = result.getCounts();
        for(String w: words){
            w = w.replaceAll("[^a-zA-Z0-9']", "");
            res.put(w, (res.get(w)==null)? 1 : res.get(w)+1);
        }
    }
}
