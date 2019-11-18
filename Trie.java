import java.util.Arrays;

public class Trie implements Storage{
    private TrieNode root;
    private int minLevDist;
    private String[] result;
    private int wordCount;
    public Trie(){
        root = new TrieNode();
    }

    @Override
    public void insert(String item) {
        root = insert(root, item.toCharArray());
    }

    @Override
    public boolean search(String item) {
        return search(root, item.toCharArray());
    }

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
                suggest(root.children[i], (char)(i+'a'), "", word, currentRow);
        }
        return result;
    }

    private TrieNode insert(TrieNode node, char[] letters){
        if(node == null){
            node = new TrieNode();
        }
        if(letters.length == 0){
            node.isWord = true;
            return node;
        }
        if(letters[0] == '\'')
            node.children[26] = insert(node.children[26], Arrays.copyOfRange(letters, 1, letters.length));
        else
            node.children[letters[0] - 'a'] = insert(node.children[letters[0] - 'a'],Arrays.copyOfRange(letters, 1, letters.length));
        return node;
    }

    private boolean search(TrieNode node, char[] letters){
        if(node == null)
            return false;
        if(letters.length == 0)
            return node.isWord;
        if (letters[0] == '\'')
            return search(node.children[26], Arrays.copyOfRange(letters, 1, letters.length));
        else
            return search(node.children[letters[0] - 'a'], Arrays.copyOfRange(letters, 1, letters.length));
    }
    private void suggest(TrieNode node, char letter, String prefix, String word, int[] previousRow){
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
                        suggest(node.children[i],'\'',prefix+letter,word,currentRow);
                    else
                        suggest(node.children[i],(char)(i+'a'),prefix+letter,word,currentRow);
        }
    }
}

