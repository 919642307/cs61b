public interface Stack<Item> {
    void push(Item x);
    Item pop();
    int size();
    public default void purge(Item x) {
        ArrayStack<Item> a = new ArrayStack<>();
        Item i;
        while (size() != 0) {
            i = pop();
            if (i != x) {
                a.push(i);
            }
        }
        while (a.size() != 0) {
            i = a.pop();
            this.push(i);
        }
    }
}
