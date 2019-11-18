import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.*;

public class CS245A1 {
    private static BufferedReader reader;
    private static BufferedWriter writer;
    private static Storage dictionary;

    /**
     *  Set the dictionary to type trie or type tree depend on A1properties.txt, the default is trie.
     * @throws Exception If an I/O error occurs
     */
    private static void setProperty() throws Exception {
        Properties storage = new Properties();
        try {
            final String properties = "A1properties.txt";
            reader = new BufferedReader(new FileReader(properties));
            String[] str = reader.readLine().split("=");
            storage.setProperty(str[0],str[1]);

        } catch (FileNotFoundException e) {
            storage.setProperty("storage","trie");
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            reader.close();
            String dataStruct = storage.getProperty("storage");
            if (dataStruct.equals("trie")){
                dictionary = new Trie();
            } else {
                dictionary = new Tree();
            }
        }
    }

    /**
     *  Read Dictionary file english.0 from the original github URL
     * @throws Exception If an I/O error occurs
     */
    private static void readDictFromUrl() throws Exception {
        String url = "https://raw.githubusercontent.com/magsilva/jazzy/master/resource/dict/english.0";
        try {
            URL myURL = new URL(url);
            URLConnection connection = myURL.openConnection();
            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line = reader.readLine();
            while (line != null) {
                if(!line.equals(""))
                    dictionary.insert(line.toLowerCase());
                line = reader.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            reader.close();
        }
    }

    /**
     *  Read input and check it, if correct write it into output, if not give some suggestion into output file.
     * @param input input file
     * @param output output file
     * @throws IOException If an I/O error occurs
     */
    private static void spellchecker(String input, String output) throws IOException {

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

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        finally {
            reader.close();
            writer.close();
        }
    }
    public static void main(String args[]) throws Exception {
        setProperty();
        readDictFromUrl();
        spellchecker(args[0],args[1]);
    }
}
