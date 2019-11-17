public class TreeNode {
    String data;
    TreeNode left;
    TreeNode right;
    int height;
    public TreeNode(String data){
        this.data = data;
        left = right = null;
        height = 0;
    }
}