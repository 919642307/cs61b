import static org.junit.Assert.*;
import org.junit.Test;
public class TestArrayDequeGold {
    @Test
    public void task1() {
        int item;
        Integer x;
        Integer y;
        StudentArrayDeque<Integer> wrongOne = new StudentArrayDeque<>();
        ArrayDequeSolution<Integer> trueOne = new ArrayDequeSolution();
        String message = new String();
        while (true) {
            int solution = StdRandom.uniform(4);
            if (solution == 0){
                item = StdRandom.uniform(100);
                wrongOne.addFirst(item);
                trueOne.addFirst(item);
                message += "addFirst("+item+")\n";
            } else if (solution == 1) {
                item =StdRandom.uniform(100);
                wrongOne.addLast(item);
                trueOne.addLast(item);
                message += "addLast("+item+")\n";
            } else if (solution == 2 && !trueOne.isEmpty()){
                x = wrongOne.removeFirst();
                y = trueOne.removeFirst();

                message += "removeFirst()\n";
                assertEquals(message, x, y);
            } else if (solution == 3 && !trueOne.isEmpty()){
                x = wrongOne.removeLast();
                y = trueOne.removeLast();
                message += "removeLast()\n";
                assertEquals(message, x, y);
            }
        }
    }
}