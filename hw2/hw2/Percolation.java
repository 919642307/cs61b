package hw2;

import com.sun.source.tree.TryTree;
import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import org.hamcrest.core.Is;
import java.lang.reflect.Array;

public class Percolation {
    private int numberOfOpenSites = 0;
    private int totalSites;
    private int N;
    private int top;
    private int Buttom;
    private boolean[] isOpen;
    private WeightedQuickUnionUF grid;
    private WeightedQuickUnionUF grid2;
    public  Percolation(int N) {
        if (N <= 0) {
            throw new java.lang.IllegalArgumentException();
        }
        this.N = N;
        totalSites = N * N + 2;
        top = totalSites - 2;
        Buttom = totalSites - 1;
        grid = new WeightedQuickUnionUF(totalSites);
        grid2 = new WeightedQuickUnionUF(totalSites-1);
        isOpen = new boolean[totalSites];
    }
    public void open(int row, int col) {
        isVaild(row,col);
        if (isOpen(row,col)) {
            return;
        }
        int index = xyTo1D(row, col);
        isOpen[index] = true;
        int[] neighbour = new int[4];
        int length = returnOpenedNeighbour(row, col).length;
        System.arraycopy(returnOpenedNeighbour(row, col), 0, neighbour, 0,length);
        int i = 0;
        while (i < length) {
            if (isOpen(neighbour[i])) {
                grid.union(index, neighbour[i]);
                grid2.union(index,neighbour[i]);
                i++;
            }
        }
        if (row == 0) {
            grid.union(index,top);
            grid2.union(index,top);
        }
        if (row == N-1) {
            grid.union(index,Buttom);
        }
        numberOfOpenSites++;
    }
    public boolean isOpen(int row, int col) {
        isVaild(row,col);
        return isOpen[xyTo1D(row, col)];
    }
    private boolean isOpen(int x) {
        return isOpen[x];
    }

    public boolean isFull(int row, int col) {
        isVaild(row, col);
        return grid2.connected(xyTo1D(row,col), top);
    }
    public int numberOfOpenSites() {
        return numberOfOpenSites;
    }
    public boolean percolates() {
        return grid.connected(top,Buttom);
    }
    private int[] returnOpenedNeighbour(int row, int col) {
        int[] array = new int[4];
        int i = 0;
        if (row+1 < N) {
            if (isOpen(row + 1, col)) {
                array[i] = xyTo1D(row + 1, col);
                i++;
            }
        }
        if (col-1 >= 0) {
            if (isOpen(row, col - 1)) {
                array[i] = xyTo1D(row, col - 1);
                i++;
            }
        }
        if (row-1 >= 0) {
            if (isOpen(row - 1, col)) {
                array[i] = xyTo1D(row - 1, col);
                i++;
            }
        }
        if (col+1 < N) {
            if (isOpen(row, col + 1)) {
                array[i] = xyTo1D(row, col + 1);
                i++;
            }
        }
        int[] arr = new int[i];
        System.arraycopy(array,0,arr,0,i);
        return arr;
    }
    private void isVaild(int row, int col) {
        if (row < 0 || row >= N || col < 0 || col >= N) {
            throw new java.lang.IndexOutOfBoundsException();
        }
    }

    private int xyTo1D(int x, int y) {
       return (x*N+y);
    }
}
