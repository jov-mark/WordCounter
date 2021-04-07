package result;

import java.util.ArrayList;
import java.util.List;

public class ResultRetrieverPool {

    private static ResultRetrieverPool instance = null;
    private List<Result> results = new ArrayList<>();

    public static ResultRetrieverPool getInstance(){
        if(instance == null){
            instance = new ResultRetrieverPool();
        }
        return instance;
    }

    public boolean get(String cmd){
        String[] args = cmd.split("\\|");
        if(args.length<2 || !(args[0].equals("file") || args[0].equals("web")))
            return false;

        if(args[1].equals("summary"))
            this.summary(args[0]);
        else
            this.corpus(args[0], args[1]);
        return true;
    }

    public void clearSummary(String type){
        results.removeIf(r -> r.getType().equals(type));
    }

    public void saveResult(Result result){
        boolean exists = false;
        for(Result r: results){
            if(r.getName().equals(result.getName())) {
                r.setCounts(result.getCounts());
                exists = true;
                break;
            }
        }
        if(!exists)
            results.add(result);
    }

    public void setResults(List<Result> results){
        this.results = results;
    }

    private void corpus(String type, String name){
        for(Result r: results){
            if(r.getType().equals(type) && r.getName().equals(name)){
                System.out.println(r.toString());
                break;
            }
        }
    }

    private void summary(String type){
        for(Result r: results){
            if(r.getType().equals(type))
                System.out.println(r);
        }
    }
}
