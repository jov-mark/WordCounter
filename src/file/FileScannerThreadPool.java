package file;

import result.Result;
import result.ResultRetrieverPool;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class FileScannerThreadPool {
    private Result result;
    private ResultRetrieverPool resultRetriever = ResultRetrieverPool.getInstance();

    private int sizeLimit;
    private List<String> keywords = new ArrayList<>();

    public void scanDirectory(String path){
        File file = new File(path);
        result = new Result("file", file.getName());

        for(File f: Objects.requireNonNull(file.listFiles()))
            scanFile(f);

        resultRetriever.saveResult(result);
    }

    private void scanFile(File f){
        if(f.length()==0)
            return;
        try {
//            System.out.println("Starting file scan for: "+f.getName()+" :"+f.length());
            StringBuilder lines = new StringBuilder();
            Scanner sc = new Scanner(new FileInputStream(f.getPath()));
            while(sc.hasNext()){
                lines.append(sc.nextLine());
            }
            sc.close();

            parseWords(lines.toString().split(" "));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void parseWords(String[] words){
        Map<String, Integer> res = result.getCounts();
        for(String w: words){
            if(!keywords.contains(w))
                continue;
            w = w.replaceAll("[^a-zA-Z0-9']", "");
            res.put(w, (res.get(w)==null)? 1 : res.get(w)+1);
        }
    }

    public void setSizeLimit(Integer sizeLimit) {
        this.sizeLimit = sizeLimit;
    }

    public void setKeywords(String keywords) {
        this.keywords.addAll(Arrays.asList(keywords.split(",")));
    }
}
