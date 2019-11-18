import org.junit.Assert;
import org.junit.Test;

public class Tests {
    @Test
    public void testInsertAndSearch() {
        Trie trie = new Trie();
        trie.insert("a");
        trie.insert("apple");
        Assert.assertEquals(true, trie.search("a"));
        Assert.assertEquals(false, trie.search("b"));
        Assert.assertEquals(true, trie.search("apple"));
        Assert.assertEquals(false, trie.search("appl"));
    }
    @Test
    public void testSuggest(){
        Trie trie = new Trie();
        trie.insert("a");
        //trie.insert("ad");
        //trie.insert("ah");
        Assert.assertEquals("a", trie.suggest("aa")[0]);
        //Assert.assertEquals("ad",trie.suggest("aa")[1]);
        for(String letters:trie.suggest("an"))
            System.out.print(letters);
    }
    @Test
    public void testTreeInsert(){
        Tree tree = new Tree();
        tree.insert("abb");
        tree.insert("apple");
        tree.insert("afgo");
        tree.insert("afgxyz");
    }
}
