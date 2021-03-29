package byog.lab5;
import com.sun.source.tree.WhileLoopTree;
import edu.princeton.cs.algs4.FordFulkerson;
import edu.princeton.cs.algs4.Out;
import edu.princeton.cs.algs4.WhiteFilter;
import org.junit.Test;
import static org.junit.Assert.*;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

/**
 * Draws a world consisting of hexagonal regions.
 */
public class HexWorld {
    public void addHexagon(TETile[][] world, int num, position P) {
        int width = countWidth(num);
        int length = countLength(num);
        position upperP = drawSecondHalf(world,num, P, width);
        drawUpperHalf(world, num, upperP, width);

    }
    private int countWidth(int num) {
        int width = 4;
        for (int i = 2; i < num; i++) {
            width += 3;
        }
        return width;
    }
    private int countLength(int num) {
        return num * 2;
    }
    private void drawUpperHalf(TETile[][] world,int num, position P, int width) {
        for(int i = width ; i >= num; i-= 2) {
            drawRow(world, i, P);
            if (i == num) {
                continue;
            }
            P.x = P.x + 1;
            P.y = P.y + 1;
        }
    }
    private position drawSecondHalf(TETile[][] world,int num, position P, int width) {
        for (int i = num; i <= width; i += 2) {
            drawRow(world, i, P);
            if (i == width){
                continue;
            }
            P.x = P.x - 1;
            P.y = P.y + 1;
        }
        P.y = P.y + 1;
        return P;
    }
    private void drawRow(TETile[][] world, int number, position P) {
        for(int xi = 0; xi < number; xi++) {
            int xCoord = P.x + xi;
            int yCoord = P.y;
            world[xCoord][yCoord] = Tileset.FLOWER;
        }
    }

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(50,50);
        TETile[][] world = new TETile[50][50];
        for (int x = 0; x < 50; x++) {
            for (int y = 0; y < 50; y++) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        position P = new position(20,2);
        HexWorld newWorld = new HexWorld();
        newWorld.addHexagon(world, 13,P);

        ter.renderFrame(world);
    }
}
