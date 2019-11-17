public class TrieNode {
    private final int ALPHABET_SIZE = 27;
    //public TrieNode parent;
    public boolean isWord;
    public TrieNode[] children;

    public TrieNode() {
        children = new TrieNode[ALPHABET_SIZE];
        isWord = false;
    }
}