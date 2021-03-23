package synthesizer;
import java.util.Iterator;

//TODO: Make sure to make this class and all of its methods public
//TODO: Make sure to make this class extend AbstractBoundedQueue<t>
public class ArrayRingBuffer<T> extends AbstractBoundedQueue<T> {
    /* Index for the next dequeue or peek. */
    private int first;            // index for the next dequeue or peek
    /* Index for the next enqueue. */
    private int last;
    /* Array for storing the buffer data. */
    private T[] rb;

    /**
     * Create a new ArrayRingBuffer with the given capacity.
     */
    public ArrayRingBuffer(int capacity) {
        // TODO: Create new array with capacity elements.
        //       first, last, and fillCount should all be set to 0.
        //       this.capacity should be set appropriately. Note that the local variable
        //       here shadows the field we inherit from AbstractBoundedQueue, so
        //       you'll need to use this.capacity to set the capacity.
        rb = (T[]) new Object[capacity];
        first = 0;
        last = 0;
        this.capacity = capacity;
        fillCount = 0;
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayRingBufferIterator();
    }
    private class ArrayRingBufferIterator implements Iterator<T> {
        private int pos;
        private int num;
        public ArrayRingBufferIterator() {
            pos = first;
            num = 0;
        }
        @Override
        public boolean hasNext() {
            return num < fillCount;
        }
        @Override
        public T next() {
            T item = rb[pos];
            if (pos == capacity - 1) {
                pos = 0;
            } else{
                pos++;
            }
            num++;
            return item;
        }
    }
    public void enqueue(T x) {
        // TODO: Enqueue the item. Don't forget to increase fillCount and update last.
        if (rb[last] != null) {
            throw new RuntimeException("Ring Buffer Overflow");
        }
        rb[last] = x;
        fillCount++;
        if (last == capacity - 1) {
            last = 0;
        } else {
            last++;
        }
    }

    /**
     * Dequeue oldest item in the ring buffer. If the buffer is empty, then
     * throw new RuntimeException("Ring buffer underflow"). Exceptions
     * covered Monday.
     */
    public T dequeue() {
        // TODO: Dequeue the first item. Don't forget to decrease fillCount and update
        if (rb[first] == null) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        T temp = rb[first];
        rb[first] = null;
        if (first == capacity - 1) {
            first = 0;
        } else {
            first++;
        }
        fillCount--;
        return temp;
    }

    /**
     * Return oldest item, but don't remove it.
     */
    public T peek() {
        // TODO: Return the first item. None of your instance variables should change.
        if (rb[first] == null) {
            throw new RuntimeException("Ring Buffer Underflow");
        }
        return rb[first];

        // TODO: When you get to part 5, implement the needed code to support iteration.
    }
}
