/**
 * The node of Trie
 */
public class TrieNode {
    private final int ALPHABET_SIZE = 27;
    public boolean isWord;
    public TrieNode[] children;

    /**
     * Constructor
     */
    public TrieNode() {
        children = new TrieNode[ALPHABET_SIZE];
        isWord = false;
    }
}