package vn.edu.hcmus.student.sv19127568.slangdict;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/23/2021 - 2:04 AM
 * Description: Load file into slang dictionary
 */
public class SlangDict {
    public static HashMap<String, String> slangDict;

    /**
     * init slang dict
     */
    public static void init() {
        slangDict = hashFromTextFile();
    }

    /**
     * add a slang word to dictionary
     * @param slang String
     * @param meaning String
     */
    public static void add(String slang, String meaning) {
        slangDict.put(slang, meaning);
    }

    /**
     * edit an existing slang word
     * @param slang String
     * @param meaning String
     */
    public static void edit(String slang, String meaning) {
        for(Map.Entry<String, String> entry : slangDict.entrySet()) {
            String key = entry.getKey();
            if (key.equals(slang)) {
                entry.setValue(meaning);
            }
        }
    }

    /**
     * delete an existing slang word
     * @param slang String
     */
    public static void delete(String slang) {
        for(Map.Entry<String, String> entry : slangDict.entrySet()) {
            String key = entry.getKey();
           if (key.equals(slang)) {
                slangDict.remove(key);
           }
        }
    }

    /**
     * search by slang word
     * @param slang String
     * @return meaning String
     */
    public static String searchBySlang(String slang) {
        String meaning = null;
        for(Map.Entry<String, String> entry : slangDict.entrySet()) {
            if (entry.getKey().contains(slang)) {
                meaning = entry.getValue();
            }
        }
        return meaning;
    }

    /**
     * search by definition
     * @param def String
     * @return slangs list of slang words
     */
    public static ArrayList<String> searchByDefinition(String def) {
        ArrayList<String> slangs = null;
        for(Map.Entry<String, String> entry : slangDict.entrySet()) {
            if (entry.getValue().contains(def)) {
                slangs.add(entry.getValue());
            }
        }
        return slangs;
    }

    /**
     * load slang dict from text file into a hash map
     * @return HashMap slang dict
     */
    public static HashMap<String, String> hashFromTextFile() {
        HashMap<String, String> map = new HashMap<String, String>();
        BufferedReader br = null;
        try {
            File file = new File("slang.txt");
            br = new BufferedReader(new FileReader(file));
            String line = null;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split("`");
                String slang = tokens[0].trim();
                String meaning = tokens[1].trim();
                if (slang.length() != 0 && meaning.length() != 0) {
                    map.put(slang, meaning);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (Exception ignored) {
                }
                ;
            }
        }
        return map;
    }
}
