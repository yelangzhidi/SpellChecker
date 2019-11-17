public interface Storage {
    public void insert(String item);
    public boolean search(String item);
    public String[] suggest(String item);
}
