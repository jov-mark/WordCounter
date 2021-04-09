package config;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class ConfigReader {

    public static void getConfig() throws IOException {
        Map<String, Object> props = new HashMap<>();
        Properties prop = new Properties();
        String propFileName = "app.properties";
        InputStream inputStream = ConfigReader.class.getClassLoader().getResourceAsStream(propFileName);
        try{
            if(inputStream != null){
                prop.load(inputStream);
            }else{
                throw new FileNotFoundException("property file '"+propFileName+"' not found!");
            }

            String keywords = prop.getProperty("keywords");
            String file_corpus_prefix = prop.getProperty("file_corpus_prefix");
            String dir_crawler_sleep_time = prop.getProperty("dir_crawler_sleep_time");
            String file_scanning_size_limit = prop.getProperty("file_scanning_size_limit");
            String hop_count = prop.getProperty("hop_count");
            String url_refresh_time = prop.getProperty("url_refresh_time");

            props.put("keywords", keywords);
            props.put("file_corpus_prefix", file_corpus_prefix);
            props.put("dir_crawler_sleep_time", Integer.parseInt(dir_crawler_sleep_time));
            props.put("file_scanning_size_limit", Integer.parseInt(file_scanning_size_limit));
            props.put("hop_count", Integer.parseInt(hop_count));
            props.put("url_refresh_time", Integer.parseInt(url_refresh_time));

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            inputStream.close();
        }
        Config.setConfig(props);
    }
}
