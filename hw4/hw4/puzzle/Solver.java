package hw4.puzzle;

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;

import java.util.*;

public class Solver {
    private final Map<WorldState, Integer> eDistance = new HashMap<>();
    private final List<WorldState> solution = new ArrayList<>();

    private class SearchNode {
        private final WorldState state;
        private int moves = 0;
        private final SearchNode prev;

        public SearchNode(WorldState state, int moves, SearchNode prev) {
            this.state = state;
            this.moves = moves;
            this.prev = prev;
        }
    }

    private class myComparator implements Comparator<SearchNode> {
        @Override
        public int compare(SearchNode o1, SearchNode o2) {
            int step1 = estimateDistance(o1);
            int step2 = estimateDistance(o2);
            int total1 = o1.moves + step1;
            int total2 = o2.moves + step2;
            return total1 - total2;
        }

        private int estimateDistance(SearchNode o) {
            if (!eDistance.containsKey(o.state)) {
                eDistance.put(o.state, o.state.estimatedDistanceToGoal());
            }
            return eDistance.get(o.state);
        }
    }

    public Solver(WorldState initial) {
        MinPQ<SearchNode> minPQ = new MinPQ<>(new myComparator());
        SearchNode currentNode = new SearchNode(initial, 0, null);
        minPQ.insert(currentNode);
        while (!minPQ.isEmpty()) {
            currentNode = minPQ.delMin();
            if (currentNode.state.isGoal()) {
                break;
            }
            for (WorldState neighbour : currentNode.state.neighbors()) {
                if (currentNode.prev != null && neighbour.equals(currentNode.prev.state)) {
                    continue;
                }
                minPQ.insert(new SearchNode(neighbour, currentNode.moves + 1, currentNode));
            }
        }
        Stack<WorldState> reversePath = new Stack<>();
        for (; currentNode != null; currentNode = currentNode.prev) {
            reversePath.push(currentNode.state);
        }
        while (!reversePath.isEmpty()) {
            solution.add(reversePath.pop());
        }

    }

    public int moves() {
        return solution.size() - 1;
    }

    public Iterable<WorldState> solution() {
        return solution;
    }
}
