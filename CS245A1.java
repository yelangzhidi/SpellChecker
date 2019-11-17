import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CS245A1 {
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static Storage dictionary;
    private static void setProperty(){
        Properties storage = new Properties();
        // Set property
        try {
            final String properties = "A1properties.txt";
            reader = new BufferedReader(new FileReader(properties));
            String[] str = reader.readLine().split("=");
            storage.setProperty(str[0],str[1]);
            reader.close();
        } catch (FileNotFoundException e) {
            storage.setProperty("storage","trie");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            if (storage.get("storage") == "tree"){
                dictionary = new Tree();
            } else {
                dictionary = new Trie();
            }
        }
    }
    private static void readDict1(){
        // read english.0
        try {
            reader = new BufferedReader(new FileReader("english.0"));
            String line = reader.readLine();
            while (line != null) {
                dictionary.insert(line.toLowerCase());
                //line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readDictFromUrl() throws Exception {
        String url = "https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0";
        try {
            URL myURL = new URL(url);
            URLConnection connection = myURL.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                dictionary.insert(line.toLowerCase());
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }
    private static void spellchecker(String input, String output){
        // read input.txt and write output.txt

        try {
            File out = new File(output);
            out.createNewFile();
            reader = new BufferedReader(new FileReader(input));
            writer = new BufferedWriter(new FileWriter(output));
            String line = reader.readLine();
            while (line != null) {
                if(dictionary.search(line))
                    writer.write(line+"\n");
                else{
                    for (String suggestion: dictionary.suggest(line))
                        if(suggestion!= null)
                            writer.write(suggestion+" ");
                    writer.write("\n");
                }
                line = reader.readLine();
            }
            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void main(String args[]) throws Exception {
        setProperty();
        readDictFromUrl();
        spellchecker(args[0],args[1]);
    }
}
