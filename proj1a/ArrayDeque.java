public class ArrayDeque <T>{
    private T[] item;
    private int head;
    private int last;
    private int capacity = 8;
    public ArrayDeque(){
        item = (T[]) new Object[capacity];
        head = last = 0;
    }
    private void resize(double rate){
        int c;
        c = capacity;
        capacity = (int)(capacity * rate);
        T[] a = (T[]) new Object[capacity ];
        if (last >= head){
            System.arraycopy(item,head,a,0,last-head);

        }
        else{
            System.arraycopy(item,head,a,0,c-head);
            System.arraycopy(item,0,a,c-head,last);
        }
        head = 0;
        last = c-1;

        item = a;
    }
    private void reduceSize(double rate){
        int c;
        c = capacity;
        capacity = (int)(capacity * rate);
        T[] a = (T[]) new Object[capacity];
        if (last >= head) {
            System.arraycopy(item,head,a,0,last-head);
        }
        else {
            System.arraycopy(item,head,a,0,c-head);
            System.arraycopy(item,0,a,c-head,last);
        }
        last = size();
        head = 0;
        item = a;
    }

    public int size(){
        return (last - head + capacity) %capacity;
    }
    private boolean isFull(){
        return size() == capacity - 1 ;
    }

    public boolean isEmpty(){
        return last == head;
    }
    private boolean isLowUsageRate(){
        return capacity > 16 && (size()/(double)capacity)<0.25;
    }

    public void addLast(T i){
        if (isFull()){
            resize(2);
        }
        item[last] = i;
        if (last == capacity-1) {
            last = 0;
        }
        else{
            last++;
        }
    }
    public void addFirst(T i){
        if (isFull()){
            resize(2);
        }
        if (head == 0) {
            head = capacity-1;
        }
        else {
            head--;
        }
        item[head] = i;
    }
    public T removeFirst(){
        if (isEmpty()){
            return  null;
        }
        T i = item[head];
        if (head != capacity-1) {
            item[head] = null;
            head++;
        }

        else {
            item[head] = null;
            head = 0;
        }
        if (isLowUsageRate()) {
            reduceSize(0.5);
        }
        return i;

    }
    public T removeLast(){
        if (isEmpty()){
            return null;
        }
        T i = item[(last-1+capacity)%capacity];
        if (last != 0) {
            item[last-1] = null;
            last--;
        }
        else {
            item[capacity - 1] = null;
            last = capacity -1;
        }
        if (isLowUsageRate()) {
            reduceSize(0.5);
        }
        return i;
    }
    public T get(int index){
        if (head < last) {
            return item[index+head];
        }
        else {
            if (index+head < capacity){
                return item[index+head];
            }
            else {
                return item[(index+head+capacity)%capacity];
            }
        }

    }
    public void printDeque(){
        if (head == last) {
            return;
        }
        else if (head < last) {
            int i = head;
            for (;i<last;i++){
                if (i == last - 1) {
                    System.out.print(item[i]);
                }
                System.out.printf(item[i] + " ");
            }

        }
        else {
            for (int i = head; i < capacity; i++) {
                System.out.printf(item[i] + " ");
            }

            for (int i = 0; i < last; i++) {
                if (i == head - 1) {
                    System.out.println(item[i]);
                    break;
                }
                System.out.printf(item[i]+" ");
            }
        }
    }




}
