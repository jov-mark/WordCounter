package file;

import result.ResultRetrieverPool;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Scanner;

public class FileScannerThreadPool {
    private File file;
    private ResultRetrieverPool resultRetriever = ResultRetrieverPool.getInstance();

    public void scanDirectory(String path){
        this.file = new File(path);
        for(File f: Objects.requireNonNull(this.file.listFiles()))
            scanFile(f);
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
        Map<String, Integer> result = new HashMap<>();
        for(String w: words){
            w = w.replaceAll("[?.,]", "");
            result.put(w, (result.get(w)==null)? 1 : result.get(w)+1);
        }
        resultRetriever.saveResult(result);
    }
}
