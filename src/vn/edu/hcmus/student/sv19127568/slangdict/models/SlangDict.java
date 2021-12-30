package vn.edu.hcmus.student.sv19127568.slangdict.models;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * vn.edu.hcmus.student.sv19127568.slangdict
 * Created by Thu Nguyen
 * Date 12/23/2021 - 2:04 AM
 * Description: slang dictionary methods and data initialization
 */
public class SlangDict {
    public static HashMap<String, String> slangDict;
    public static Vector<String> history = new Vector<>();

    /**
     * init slang dict and load history
     */
    public static void init() {
        try {
            File fin = new File("slangdict.dat");
            FileInputStream fis = new FileInputStream(fin);
            ObjectInputStream ois = new ObjectInputStream(fis);
            slangDict = (HashMap<String, String>) ois.readObject();
            ois.close();
            fis.close();
            fin = new File("history.dat");
            if (fin.length() != 0) {
                fis = new FileInputStream(fin);
                ois = new ObjectInputStream(fis);
                history = (Vector<String>) ois.readObject();
                ois.close();
                fis.close();
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * reset slang dict
     */
    public static void reset() {
        slangDict.clear();
        history.clear();
        slangDict = hashFromTextFile();
    }

    /**
     * save the slang dictionary
     */
    public static void saveDict() {
        try {
            File fout = new File("slangdict.dat");
            FileOutputStream fos = new FileOutputStream(fout);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(slangDict);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * save slang dictionary's history
     */
    public static void saveHis() {
        try {
            File fout = new File("history.dat");
            FileOutputStream fos = new FileOutputStream(fout);
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeObject(history);
            oos.flush();
            oos.close();
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
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
     * @return list of found slang words and their definition
     */
    public static HashMap<String, String> searchBySlang(String slang) {
        HashMap<String, String> res = new HashMap<String, String>();
        for(Map.Entry<String, String> entry : slangDict.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            if (key.startsWith(slang)) {
                res.put(key,value);
            }
        }
        return res;
    }

    /**
     * search by definition
     * @param def String
     * @return list of found slang words and their definition
     */
    public static HashMap<String, String> searchByDefinition(String def) {
        HashMap<String, String> res = new HashMap<String, String>();
        for(Map.Entry<String, String> entry : slangDict.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            String[] tokens = value.split(" ");
            for (String word : tokens)
                if (word.startsWith(def)) {
                    res.put(key, value);
                }
            }
        return res;
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
