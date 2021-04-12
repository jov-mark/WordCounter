package result;

import config.Config;

import java.util.HashMap;
import java.util.Map;

public class Result {

    private String type;
    private String name;
    private Map<String,Integer> counts;

    public Result(String type, String name){
        this.type = type;
        this.name = name;
        this.counts = new HashMap<>();
    }

    public Result(String type, String name, Map<String,Integer> counts){
        this.type = type;
        this.name = name;
        this.counts = counts;
    }

    public Result(String name, Map<String,Integer> res){
        this.type = "file";
        this.name = name;
        this.counts = res;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public Map<String, Integer> getCounts() {
        return counts;
    }

    public void setCounts(Map<String, Integer> counts) {
        this.counts = counts;
    }

    @Override
    public String toString() {
        StringBuilder str = new StringBuilder();
        str.append(name).append("\n").append("{");

        for(String keyword: Config.getKeywords()){
            str.append(keyword).append("=");
            str.append(counts.getOrDefault(keyword,0).toString());
            str.append(",");
        }
        str.setCharAt(str.length()-1,'}');
        return str.toString();
    }
}
