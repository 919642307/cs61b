public class LinkedListDeque<Item> implements Deque<Item> {
    private class node{
        public Item item;
        public node next;
        public node prev;
        public node(Item i, node p,node n) {
            this.item =i;
            this.prev =p;
            this.next =n;
        }
    }
    private node sentinel;
    private int size;
    public LinkedListDeque(){
        sentinel = new node(null,null,null);
        size = 0;
        sentinel.prev = sentinel;
        sentinel.next = sentinel;
    }

    public void addFirst(Item item){
        node p = new node(item,sentinel,sentinel.next);
        sentinel.next.prev = p;
        sentinel.next = p;

        size++;
    }


    public void addLast(Item item){
        node p = new node(item,sentinel.prev,sentinel);
        sentinel.prev.next = p;
        sentinel.prev = p;
        size++;
    }
    public boolean isEmpty(){
        if (sentinel.next == sentinel){
            return true;
        }
        return false;
    }
    public int size(){
        return size;
    }
    public void printDeque(){
        node p = sentinel.next ;
        while(p != sentinel){
            System.out.print(p.item);
            if (p.next != sentinel) {
                System.out.print(' ');
            }
            p = p.next;
        }
        System.out.println();
    }
    public Item removeFirst(){
        if (sentinel.next == sentinel) {
            return null;
        }
        Item temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;
    }
    public Item removeLast(){
        if (sentinel.next == sentinel) {
            return null;
        }
        Item temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;
    }
    public Item get(int index){
        if (sentinel.next == sentinel) {
            return null;
        }
        int i = 0;
        node p = sentinel.next;
        while (i != index) {

            p = p.next;
            i++;
            if (p == sentinel){
                return null;
            }
        }
        return p.item;
    }
    public Item getRecursive(int index) {
        if (size < index) {
            return null;
        }
        return getRecursive(sentinel.next, index);
    }
    private Item getRecursive(node Node, int i) {
        if (i == 0) {
            return Node.item;
        }
        return getRecursive(Node.next, i - 1);
    }



}