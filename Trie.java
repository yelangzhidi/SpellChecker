import java.util.Arrays;

public class Trie implements Storage{
    private TrieNode root;
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
    public String[] suggest(String item) {
        return new String[0];// need implement
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
            return search(node.children[0], Arrays.copyOfRange(letters, 1, letters.length));
        else
            return search(node.children[letters[0] - 'a'], Arrays.copyOfRange(letters, 1, letters.length));
    }
}

