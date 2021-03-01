public class LinkedListDeque<T> {
    private class node{
        public T item;
        public node next;
        public node prev;
        public node(T i, node p,node n) {
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
    public LinkedListDeque(T i){
        sentinel = new node(null,null,null);
        node p = new node(i,sentinel,sentinel);
        sentinel.prev = p;
        sentinel.next = p;
        size = 1;
    }
    public void addFirst(T item){
        node p = new node(item,sentinel,sentinel.next);
        sentinel.next.prev = p;
        sentinel.next = p;

        size++;
    }
    public void addLast(T item){
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
    public T removeFirst(){
        if (sentinel.next == sentinel) {
            return null;
        }
        T temp = sentinel.next.item;
        sentinel.next.next.prev = sentinel;
        sentinel.next = sentinel.next.next;
        size--;
        return temp;
    }
    public T removeLast(){
        if (sentinel.next == sentinel) {
            return null;
        }
        T temp = sentinel.prev.item;
        sentinel.prev.prev.next = sentinel;
        sentinel.prev = sentinel.prev.prev;
        size--;
        return temp;
    }
    public T get(int index){
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



}