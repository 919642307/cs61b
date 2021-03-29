package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;

import java.io.*;

public class Game implements Serializable {
    TERenderer ter = new TERenderer();
    /* Feel free to change the width and height. */
    public static final int WIDTH = 101;
    public static final int HEIGHT = 61;

    /**
     * Method used for playing a fresh game. The game should start from the main menu.
     */
    public void playWithKeyboard() {
    }

    /**
     * Method used for autograding and testing the game code. The input string will be a series
     * of characters (for example, "n123sswwdasdassadwas", "n123sss:q", "lwww". The game should
     * behave exactly as if the user typed these characters into the game after playing
     * playWithKeyboard. If the string ends in ":q", the same world should be returned as if the
     * string did not end with q. For example "n123sss" and "n123sss:q" should return the same
     * world. However, the behavior is slightly different. After playing with "n123sss:q", the game
     * should save, and thus if we then called playWithInputString with the string "l", we'd expect
     * to get the exact same world back again, since this corresponds to loading the saved game.
     * @param input the input string to feed to your program
     * @return the 2D TETile[][] representing the state of the world
     */
    public TETile[][] playWithInputString(String input) {
        // and return a 2D tile representation of the world that would have been
        // drawn if the same inputs had been given to playWithKeyboard().
        TETile[][] finalWorldFrame;
        finalWorldFrame = handleString(input);
        return finalWorldFrame;
    }
    private TETile[][] handleString(String input) {
        TETile[][] finalWorldFrame = new TETile[100][60];
        if (input.substring(0,1).equals("N")) {
            finalWorldFrame = toBeginNewGame(input);
        } else {
            toLoadGame(input);
        }
        return finalWorldFrame;
    }
    private TETile[][] toBeginNewGame(String input) {
        long seed;
        TETile[][] finalWorldFrame;
        String[] arr1 = input.split("S",2);
        seed = Long.parseLong(arr1[0].substring(1));
        finalWorldFrame = PlayGame.play(seed);
        String[] arr2 =  arr1[1].split(":",2);
        remove(arr2[0]);
        saveGame(finalWorldFrame);
        return finalWorldFrame;
    }
    private TETile[][] toLoadGame(String input) {
        TETile[][] finalWorldFrame = new TETile[100][60];
        finalWorldFrame = getSavedGame();
        return finalWorldFrame;
    }
    private void remove(String manipulate) {

    }
    private void saveGame(TETile[][] finalWorldFrame) {
        try {
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream("shapefile.txt"));
            out.writeObject(finalWorldFrame);
            out.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private TETile[][] getSavedGame() {
        TETile[][] finalWorldFrame = new TETile[101][61];
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream("shapefile.txt"));
            finalWorldFrame = (TETile[][]) in.readObject();
            in.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return finalWorldFrame;
    }
}
