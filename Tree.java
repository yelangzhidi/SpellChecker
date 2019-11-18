public class Tree implements Storage {
    private TreeNode root;
    public Tree(){
        root= null;
    }


    @Override
    public void insert(String item) {
        root = insert(item, root);
    }

    private TreeNode insert(String item, TreeNode treeNode) {
        if (treeNode == null)
            return new TreeNode(item);
        else if (item.compareTo(treeNode.data) < 0) {
            treeNode.left = insert(item, treeNode.left);
            if (height(treeNode.left) - height(treeNode.right) == 2)
                if (item.compareTo(treeNode.left.data) < 0)
                    treeNode = rotateWithLeftChild(treeNode);
                else
                    treeNode = doubleWithLeftChild(treeNode);
        }
        else {
                treeNode.right = insert(item, treeNode.right);
                if (height(treeNode.right) - height(treeNode.left) == 2)
                    if (item.compareTo(treeNode.right.data) > 0)
                        treeNode = rotateWithRightChild(treeNode);
                    else
                        treeNode = doubleWithRightChild(treeNode);
            }
        treeNode.height = max(height(treeNode.left), height(treeNode.right)) + 1;
        return treeNode;
    }

    @Override
    public boolean search(String item) {
        return search(item, root);
    }
    private  boolean search(String item, TreeNode treeNode){
        if (treeNode == null)
            return false;
        if (treeNode.data == item)
            return true;
        if (treeNode.data.compareTo(item) > 0)
            return search(item, treeNode.left);
        else
            return search(item, treeNode.right);
    }

    @Override
    public String[] suggest(String item) {

        return suggestHelper(item, root);
    }
    private String[] suggestHelper(String item, TreeNode treeNode){
        if (treeNode.left == null||treeNode.right == null)
            return new String[]{treeNode.data};
        if (treeNode.data.compareTo(item) > 0)
            return suggestHelper(item, treeNode.left);
        else
            return suggestHelper(item, treeNode.right);
    }
    private int height(TreeNode treeNode){
        return treeNode == null? -1 : treeNode.height;
    }
    private int max (int lhs, int rhs){
        return lhs > rhs? lhs : rhs;
    }
    private TreeNode rotateWithLeftChild(TreeNode k2){
        TreeNode k1 = k2.left;
        k2.left = k1.right;
        k1.right = k2;
        k2.height = max( height( k2.left ), height( k2.right ) ) + 1;
        k1.height = max( height( k1.left ), k2.height ) + 1;
        return k1;
    }
    private TreeNode rotateWithRightChild(TreeNode k1)
    {
        TreeNode k2 = k1.right;
        k1.right = k2.left;
        k2.left = k1;
        k1.height = max( height( k1.left ), height( k1.right ) ) + 1;
        k2.height = max( height( k2.right ), k1.height ) + 1;
        return k2;
    }
    private TreeNode doubleWithLeftChild(TreeNode k2)
    {
        k2.left = rotateWithRightChild( k2.left );
        return rotateWithLeftChild( k2 );
    }
    private TreeNode doubleWithRightChild(TreeNode k1)
    {
        k1.right = rotateWithLeftChild( k1.right );
        return rotateWithRightChild( k1 );
    }
}
