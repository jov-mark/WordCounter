import java.util.HashMap;
import java.util.Map;

public class main {

    public static void main(String[] args) {
        Map<String,Integer> map = new HashMap<>();
        map.put("one",5);
        map.put("two",3);

        Map<String,Integer> m2p = new HashMap<>();
        m2p.put("one",2);

        merge(map,m2p);

        for(Map.Entry e: map.entrySet()){
            System.out.println(e.getKey()+" "+e.getValue());
        }
    }

    private static void merge(Map<String,Integer> map, Map<String,Integer> m2p){
        m2p.forEach((key, value) -> {
            map.merge(key, value, Integer::sum);
        });
    }
}
