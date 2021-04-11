package result;

import job.JobType;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ResultRetriever {
    private ForkJoinPool forkJoinPool;
    private Map<String, Future<Map<String,Integer>>> webResults;
    private Map<String, Future<Map<String,Integer>>> fileResults;

    public ResultRetriever(){
        forkJoinPool = new ForkJoinPool();
        webResults = new HashMap<>();
        fileResults = new HashMap<>();
    }

    public void setFileResults(String path, Future<Map<String,Integer>> result){
        fileResults.put(path,result);
    }

    private void getPool(){

    }

    private void summaryPool(){

    }

    public boolean get(String cmd){
        String[] args = cmd.split("\\|");
        if(args.length<2 || !(args[0].equals("file") || args[0].equals("web")))
            return false;

        if(args[0].equals("file") && args[1].equals("summary")){
            for(String file: fileResults.keySet()){
                try {
                    Map<String,Integer> res = fileResults.get(file).get();
                    System.out.println(file+":");
                    System.out.print("{");
                    for(Map.Entry e: res.entrySet()){
                        System.out.print(e.getKey()+":"+e.getValue()+",");
                    }
                    System.out.println("}");

                } catch (InterruptedException | ExecutionException e) {
                    e.printStackTrace();
                }
            }

        }else if(args[0].equals("file")){
            if(fileResults.get(args[1]) == null)
                return false;

            try {
                Map<String,Integer> res = fileResults.get(args[1]).get();
                System.out.print("{");
                for(Map.Entry e: res.entrySet()){
                    System.out.print(e.getKey()+":"+e.getValue()+",");
                }
                System.out.println("}");
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }else{
            return false;
        }
        return true;
    }

    public void clearSummary(JobType type){
        if(type.equals(JobType.FILE))
            fileResults.clear();
        else
            webResults.clear();
    }



//    TODO: add corpus results
    public void addCorpusResults(String corpusName, Future<Map<String,Integer>> corpusResult){

    }

    public void shutdown(){
        forkJoinPool.shutdown();
    }
}
