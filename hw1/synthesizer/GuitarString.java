package synthesizer;

//Make sure this class is public
public class GuitarString {
    /** Constants. Do not change. In case you're curious, the keyword final means
     * the values cannot be changed at runtime. We'll discuss this and other topics
     * in lecture on Friday. */
    private static final int SR = 44100;      // Sampling Rate
    private static final double DECAY = .996; // energy decay factor

    /* Buffer for storing sound data. */
    private BoundedQueue<Double> buffer;

    /* Create a guitar string of the given frequency.  */
    public GuitarString(double frequency) {
        int capacity = (int)Math.round(SR/frequency);
        buffer = new ArrayRingBuffer<>(capacity);
        while (!buffer.isFull()) {
            buffer.enqueue((double)0);
        }
    }
    /* Pluck the guitar string by replacing the buffer with white noise. */
    public void pluck() {

        //       Make sure that your random numbers are different from each other.
        double temp;
        while (!buffer.isEmpty()) {
            buffer.dequeue();
        }
        while (!buffer.isFull()) {
            temp = Math.random() - 0.5;
            buffer.enqueue(temp);
        }
    }

    /* Advance the simulation one time step by performing one iteration of
     * the Karplus-Strong algorithm. 
     */
    public void tic() {
        double temp = buffer.dequeue();
        double sample = DECAY * 0.5 * (temp + buffer.peek());
        buffer.enqueue(sample);
    }

    /* Return the double at the front of the buffer. */
    public double sample() {
        return buffer.peek();
    }
}
