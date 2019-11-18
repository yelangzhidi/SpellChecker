/**
 *  Data Structure: Self-Balance Binary Search Tree
 */
public class Tree implements Storage {
    private TreeNode root;
    public Tree(){
        root= null;
    }

    /**
     * Insert word into Trie
     * @param word String in dictionary
     */
    @Override
    public void insert(String word) {
        root = insertHelper(word, root);
    }

    /**
     * Search the word in the Trie or not
     * @param word String want to search
     * @return boolean true if word in Trie, false if not
     */
    @Override
    public boolean search(String word) {
        return searchHelper(word, root);
    }

    /**
     * The possible correct words in the Trie
     * @param word String want to suggest
     * @return String Array up to 3
     */
    @Override
    public String[] suggest(String word) {

        return suggestHelper(word, root);
    }
    
    //Helper Functions:
    /**
     * The recursive insert with self balance
     * @param word String we want to insert
     * @param node current node are using
     * @return node after modified.
     */
    private TreeNode insertHelper(String word, TreeNode node) {
        if (node == null)
            return new TreeNode(word);
        else if (word.compareTo(node.data) < 0) {
            node.left = insertHelper(word, node.left);
            if (height(node.left) - height(node.right) == 2)
                if (word.compareTo(node.left.data) < 0)
                    node = rotateWithLeftChild(node);
                else
                    node = doubleWithLeftChild(node);
        }
        else {
            node.right = insertHelper(word, node.right);
            if (height(node.right) - height(node.left) == 2)
                if (word.compareTo(node.right.data) > 0)
                    node = rotateWithRightChild(node);
                else
                    node = doubleWithRightChild(node);
        }
        node.height = max(height(node.left), height(node.right)) + 1;
        return node;
    }

    /**
     * The recursive search
     * @param word String we want to search
     * @param node current node are using
     * @return boolean true if letter in tree
     */
    private  boolean searchHelper(String word, TreeNode node){
        if (node == null)
            return false;
        if (node.data == word)
            return true;
        if (node.data.compareTo(word) > 0)
            return searchHelper(word, node.left);
        else
            return searchHelper(word, node.right);
    }

    /**
     * The recursive suggest
     * @param word String we want to suggest
     * @param node current node are using
     * @return String array the result
     */
    private String[] suggestHelper(String word, TreeNode node){
        if (node.left == null||node.right == null)
            return new String[]{node.data};
        if (node.data.compareTo(word) > 0)
            return suggestHelper(word, node.left);
        else
            return suggestHelper(word, node.right);
    }

    /**
     * Get height of node
     * @param node target node
     * @return the int height of node
     */
    private int height(TreeNode node){
        return node == null? -1 : node.height;
    }

    /**
     * Max of left/right node
     * @param lhs left value
     * @param rhs right value
     * @return the bigger value
     */
    private int max (int lhs, int rhs){
        return lhs > rhs? lhs : rhs;
    }

    /**
     * Rotate binary tree node with left child
     * @param k2 The original parent node
     * @return k1 The modified left node
     */
    private TreeNode rotateWithLeftChild(TreeNode k2){
        TreeNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }

    /**
     * Rotate binary tree node with right child
     * @param k1 The original parent node
     * @return k2 The modified right node
     */
    private TreeNode rotateWithRightChild(TreeNode k1)
    {
        TreeNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }

    /**
     * Double rotate binary tree node: first left child
     * with its right child; then node k1 with new left child
     * @param k2 The original parent node
     * @return node after modified
     */
    private TreeNode doubleWithLeftChild(TreeNode k2)
    {
        k2.left = rotateWithRightChild( k2.left );
        return rotateWithLeftChild( k2 );
    }

    /**
     * Double rotate binary tree node: first right child
     * with its left child; then node k1 with new right child
     * @param k1 The original parent node
     * @return node after modified
     */
    private TreeNode doubleWithRightChild(TreeNode k1)
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }
}
