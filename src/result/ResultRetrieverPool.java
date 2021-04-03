package result;

import java.io.FileWriter;
import java.util.Map;

public class ResultRetrieverPool {

    private static ResultRetrieverPool instance = null;

    public static ResultRetrieverPool getInstance(){
        if(instance == null){
            instance = new ResultRetrieverPool();
        }
        return instance;
    }

    public void saveResult(Map<String,Integer> result){
        try {
            FileWriter fileWriter = new FileWriter(System.getProperty("user.dir")+"/"+"result.txt");
            for(Map.Entry entry: result.entrySet()){
                fileWriter.write(entry.getKey()+": "+entry.getValue().toString());
            }
            fileWriter.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
