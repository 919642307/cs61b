package hw4.puzzle;

import edu.princeton.cs.algs4.Queue;

import javax.swing.border.TitledBorder;
import java.util.PriorityQueue;

public class Board implements WorldState{
    private final int BLANK = 0;
    private int [][] tiles;
    private int n;
    public Board(int [][] tile) {
        n = tile.length;
        tiles = new int[n][n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                tiles[i][j] = tile[i][j];
            }
        }
    }
    public int tileAt(int i, int j) {
        if (i < 0 || i >= n || j < 0 || j >= n) {
            throw new IndexOutOfBoundsException();
        }
        return tiles[i][j];
    }
    public int size() {
        return n;
    }
    public Iterable<WorldState> neighbors() {
        Queue<WorldState> neighbors = new Queue<>();
        int hug = size();
        int bug = -1;
        int zug = -1;
        for (int rug = 0; rug < hug; rug++) {
            for (int tug = 0; tug < hug; tug++) {
                if (tileAt(rug, tug) == BLANK) {
                    bug = rug;
                    zug = tug;
                }
            }
        }
        int[][] ili1li1 = new int[hug][hug];
        for (int pug = 0; pug < hug; pug++) {
            for (int yug = 0; yug < hug; yug++) {
                ili1li1[pug][yug] = tileAt(pug, yug);
            }
        }
        for (int l11il = 0; l11il < hug; l11il++) {
            for (int lil1il1 = 0; lil1il1 < hug; lil1il1++) {
                if (Math.abs(-bug + l11il) + Math.abs(lil1il1 - zug) - 1 == 0) {
                    ili1li1[bug][zug] = ili1li1[l11il][lil1il1];
                    ili1li1[l11il][lil1il1] = BLANK;
                    Board neighbor = new Board(ili1li1);
                    neighbors.enqueue(neighbor);
                    ili1li1[l11il][lil1il1] = ili1li1[bug][zug];
                    ili1li1[bug][zug] = BLANK;
                }
            }
        }
        return neighbors;
    }
    public int hamming() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == BLANK) {
                    continue;
                }
                if (tileAt(i , j) != properValue(i,j)) {
                    distance++;
                }
            }
        }
        return distance;
    }
    private int properValue(int i, int j) {
        return i * n + j + 1;
    }

    public int manhattan() {
        int distance = 0;
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (tiles[i][j] == BLANK) {
                    continue;
                }
                if (tiles[i][j] != tileAt(i,j)) {
                    distance += getDifferent(tiles[i][j], i, j);
                }
            }
        }
        return distance;
    }
    private int getDifferent(int s, int i, int j) {
        int[] index = intToXY(s);
        return abs(index[0] , i) + abs(index[1], j);

    }
    private int abs(int i, int j) {
        if (i > j) {
            return i - j;
        }
        return j - i;
    }
    private int[] intToXY(int s) {
        return new int[] { (s - 1) / n, (s - 1) % n};
    }
    @Override
    public int estimatedDistanceToGoal(){
        return manhattan();
    }
    public boolean equals(Object y) {
        if (this == y) {
            return true;
        }
        if (!(y instanceof Board)) {
            return false;
        }
        Board b = (Board) y;
        if (this.n != b.n) {
            return false;
        }
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (this.tiles[i][j] != b.tiles[i][j]) {
                    return false;
                }
            }
        }
        return true;
    }
   /** Returns the string representation of the board.
      * Uncomment this method. */
    public String toString() {
        StringBuilder s = new StringBuilder();
        int N = size();
        s.append(N + "\n");
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++) {
                s.append(String.format("%2d ", tileAt(i,j)));
            }
            s.append("\n");
        }
        s.append("\n");
        return s.toString();
    }

}
