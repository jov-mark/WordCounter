package result;

import job.JobType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;

public class ResultRetriever {
    private Map<String, Future<Map<String,Integer>>> webResults;
    private Map<String, Future<Map<String,Integer>>> fileResults;

    public ResultRetriever(){
        webResults = new HashMap<>();
        fileResults = new HashMap<>();
    }

    public void setFileResults(String path, Future<Map<String,Integer>> result){
        fileResults.put(path,result);
    }

    public Result get(String arg){
        Result r = null;
        if(fileResults.get(arg) != null){
            try {
                Map<String,Integer> res = fileResults.get(arg).get();
                r = new Result(arg,res);
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return r;
    }

    public List<Result> getSummary(){
        List<Result> result = new ArrayList<>();
        for(String file: fileResults.keySet()){
            try {
                Map<String,Integer> res = fileResults.get(file).get();
                result.add(new Result(file,res));
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    public void clearSummary(JobType type){
        if(type.equals(JobType.FILE))
            fileResults.clear();
        else
            webResults.clear();
    }

//    TODO: add corpus results when corpus added/updated
    public void addCorpusResults(String corpusName, Future<Map<String,Integer>> corpusResult){

    }
}
