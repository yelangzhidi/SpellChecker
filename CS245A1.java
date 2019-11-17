import java.io.*;
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
                line = reader.readLine();
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void readDict(){

    }
    private static void spellchecker(String input, String output){
        // read input.txt and write output.txt
        try {
            reader = new BufferedReader(new FileReader(input));
            writer = new BufferedWriter(new FileWriter(output));
            String line = reader.readLine();
            while (line != null) {
                if(dictionary.search(line))
                    writer.write(line+"\n");
                else
                    writer.write("need suggest"+"\n");//dictionary.suggest(line);
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
    public static void main(String args[]){
        setProperty();
        readDict1();
        spellchecker(args[0],args[1]);
    }
}
