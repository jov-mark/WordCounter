package result;

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
        for(Map.Entry w: counts.entrySet())
            str.append(w.getKey()).append("=").append(w.getValue()).append(", ");
        str.delete(str.length()-2,str.length());
        str.append("}");
        return str.toString();
    }
}
