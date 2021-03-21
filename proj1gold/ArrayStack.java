public class ArrayStack<Item> implements Stack {
    private Item[] items;
    private int lenth;
    public ArrayStack() {
        items = (Item[]) new Object[8];
        lenth = 0;
    }
    private void resize(int capacity) {
        Item[] a = (Item[]) new Object[capacity];
        System.arraycopy(items,0,a,0,lenth);
        items = a;
    }
    public void push(Object x){
        if (usageRatio() == 1) {
            resize(lenth * 2);
        }
        items[lenth] = (Item) x;
        lenth++;
    }
    public Item pop() {
        if (lenth == 0) {
            return null;
        }
        if (usageRatio() < 0.25 && items.length < 8) {
            resize(items.length/4);
        }
        Item a = items[lenth - 1];
        items[lenth - 1] = null;
        lenth--;
        return a;

    }
    public int size(){
        return lenth;
    }
    private double usageRatio() {
        return((double)size()/items.length);
    }

    public static void main(String[] args) {
        ArrayStack<Integer> a = new ArrayStack<>();
        a.push(1);
        a.push(2);
        a.push(3);
        a.push(2);
        a.push(2);
        a.push(2);
        a.purge(2);
    }
}
