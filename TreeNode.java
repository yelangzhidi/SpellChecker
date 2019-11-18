/**
 * The node of Tree
 */
public class TreeNode {
    String data;
    TreeNode left;
    TreeNode right;
    int height;

    /**
     * Constructor
     * @param data the String want to store
     */
    public TreeNode(String data){
        this.data = data;
        left = right = null;
        height = 0;
    }
}