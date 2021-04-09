package config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Config {

    private static Map<String,Object> props = new HashMap<>();

    public static void setConfig(Map<String,Object> config){
        props = config;
    }

    public static List<String> getKeywords(){
        return Arrays.asList(props.get("keywords").toString().split(","));
    }

    public static String getCorpus(){
        return props.get("file_corpus_prefix").toString();
    }

    public static int getSleepTime(){
        return (Integer) props.get("dir_crawler_sleep_time");
    }

    public static int getFileSizeLimit(){
        return (Integer) props.get("file_scanning_size_limit");
    }
}
