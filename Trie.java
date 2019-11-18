import java.util.Arrays;

/**
 * Data Structure Trie
 */
public class Trie implements Storage{
    private TrieNode root;
    private int minLevDist;
    private String[] result;
    private int wordCount;
    public Trie(){
        root = new TrieNode();
    }

    /**
     * Insert word into Trie
     * @param word String in dictionary
     */
    @Override
    public void insert(String word) {
        root = insertHelper(root, word.toCharArray());
    }

    /**
     * Search the word in the Trie or not
     * @param word String want to search
     * @return boolean true if word in Trie, false if not
     */
    @Override
    public boolean search(String word) {
        return searchHelper(root, word.toCharArray());
    }

    /**
     * The possible correct words in the Trie
     * @param word String want to suggest
     * @return String Array up to 3
     */
    @Override
    public String[] suggest(String word) {
        result = new String[3];
        wordCount = 0;
        minLevDist = Integer.MAX_VALUE;
        int wordLength = word.length();
        int[] currentRow = new int[wordLength+1];
        for(int i = 0; i <= wordLength; i++)
            currentRow[i] = i;
        for(int i = 0; i < 26; i++) {
            if(root.children[i]!= null)
                suggestHelper(root.children[i], (char)(i+'a'), "", word, currentRow);
        }
        return result;
    }
    // Helper Functions:
    /**
     * The recursive insert
     * @param node current node are using
     * @param letters Char array the word left
     * @return node after modified.
     */
    private TrieNode insertHelper(TrieNode node, char[] letters){
        if(node == null){
            node = new TrieNode();
        }
        if(letters.length == 0){
            node.isWord = true;
            return node;
        }
        if(letters[0] == '\'')
            node.children[26] = insertHelper(node.children[26], Arrays.copyOfRange(letters, 1, letters.length));
        else
            node.children[letters[0] - 'a'] = insertHelper(node.children[letters[0] - 'a'],Arrays.copyOfRange(letters, 1, letters.length));
        return node;
    }

    /**
     * The recursive search
     * @param node current node are using
     * @param letters Char array the word left
     * @return boolean true if letter in trie
     */
    private boolean searchHelper(TrieNode node, char[] letters){
        if(node == null)
            return false;
        if(letters.length == 0)
            return node.isWord;
        if (letters[0] == '\'')
            return searchHelper(node.children[26], Arrays.copyOfRange(letters, 1, letters.length));
        else
            return searchHelper(node.children[letters[0] - 'a'], Arrays.copyOfRange(letters, 1, letters.length));
    }

    /**
     *  The recursive suggest use find min LevenShtein Distance to find closest 3 String in trie
     * @param node current node are using
     * @param letter current node represent letter
     * @param prefix the previous nodes we have pass
     * @param word the target incorrect word
     * @param previousRow the last row of LevenShetein Distance table
     */
    private void suggestHelper(TrieNode node, char letter, String prefix, String word, int[] previousRow){
        int size = previousRow.length;
        int[] currentRow = new int[size];
        currentRow[0] = previousRow[0] + 1;
        int minimumElement = currentRow[0];
        int insertCost, deleteCost, replaceCost;
        for(int i = 1; i < size; i++){
            insertCost = currentRow[i - 1] + 1;
            deleteCost = previousRow[i] + 1;
            if(word.charAt(i-1) == letter)
                replaceCost = previousRow[i - 1];
            else
                replaceCost = previousRow[i - 1] + 1;
            currentRow[i] = Math.min(Math.min(insertCost,deleteCost),replaceCost);
            if(currentRow[i] < minimumElement)
                minimumElement = currentRow[i];

        }
        if(currentRow[size - 1] == minLevDist && node.isWord) {
            if (wordCount < 3)
                result[wordCount++] = prefix + letter;
        }

        if(currentRow[size - 1] < minLevDist && node.isWord) {
            minLevDist = currentRow[size - 1];
            wordCount = 0;
            result = new String[3];
            result[wordCount++] = prefix + letter;
        }
        if(minimumElement < minLevDist) {
            for(int i = 0; i < node.children.length; i++)
                if(node.children[i] != null)
                    if(i==26)
                        suggestHelper(node.children[i],'\'',prefix+letter,word,currentRow);
                    else
                        suggestHelper(node.children[i],(char)(i+'a'),prefix+letter,word,currentRow);
        }
    }
}

