import java.io.File;
import java.io.FileInputStream;
import java.util.*;

public class main {

    public static void main(String[] args) {
        Map<String, Integer> res = new HashMap<>();
        List<String> keywords = new ArrayList<>();
        keywords.add("cosmic");
        keywords.add("stars");
        keywords.add("star");
        keywords.add("billion");

        File f1 = new File(System.getProperty("user.dir")+"\\"+"dat\\corpus_1");
        for(File f: Objects.requireNonNull(f1.listFiles())){

            Map<String, Integer> fres = new HashMap<>();
            try {
                Scanner sc = new Scanner(new FileInputStream(f.getPath()));
                while(sc.hasNext()){
                    String[] words = sc.nextLine().split(" ");
                    for(String w: words){
                        if(keywords.contains(w)){
                            fres.put(w,fres.getOrDefault(w,0)+1);
                        }
                    }
                }
                sc.close();
            }catch (Exception e){
                e.printStackTrace();
            }

            fres.forEach((key, value) -> {
                res.merge(key, value, Integer::sum);
            });

            System.out.println(f.getName());
            for(Map.Entry e: res.entrySet()){
                System.out.println(e.getKey()+"="+e.getValue());
            }
            System.out.println();
        }
//        if(f.length()!=0){
//            try {
//                Scanner sc = new Scanner(new FileInputStream(f.getPath()));
//                while(sc.hasNext()){
//                    String[] words = sc.nextLine().split(" ");
//                    for(String w: words){
//                        if(keywords.contains(w)){
//                            res.put(w, (res.get(w)==null)?1:res.get(w)+1);
//                        }
//                    }
//                }
//                sc.close();
//            }catch (Exception e){
//                e.printStackTrace();
//            }
//        }
    }
}
