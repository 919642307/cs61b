package byog.Core;
import byog.SaveDemo.World;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import javax.lang.model.util.Elements;
import java.awt.font.TextLayout;
import java.nio.channels.ConnectionPendingException;
import java.nio.channels.Pipe;
import java.util.Random;

public class PlayGame {
    Random r;
    TETile [][] world;
    TERenderer ter;
    int width;
    int height;
    int RoomNumber;
    int cornerNum;
    Position[] RoomPosition;
    Position[] CornerPosition;
    Position[] RoomNextToWall;
    int RoomNextToWallIndex;

    private PlayGame(long seed) {
        width =101;
        height =61;
        r = new Random(seed);
        RoomPosition = new Position[20];
        for (int i = 0; i < 20; i++) {
            RoomPosition[i] = new Position();
        }
        CornerPosition = new Position[40];
        for (int j = 0; j < 40; j++) {
            CornerPosition[j] = new Position();
        }
        RoomNextToWall = new Position[1700];
        for (int k =0; k <1700; k++) {
            RoomNextToWall[k] = new Position();
        }
        RoomNextToWallIndex = 0;
    }
    private int RandomNum(int origin, int bound) {
        int temp = r.nextInt(bound);
        while (temp < origin) {
            temp = r.nextInt(bound);
        }
        return temp;
    }
    private void CreateWorld() {
        world = new TETile[width][height];
        ter = new TERenderer();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
               world[x][y] = Tileset.NOTHING;
            }
        }
    }
    private void CreateRoom() {
        RoomNumber = RandomNum(15,19);
        int i;
        for (i = 0; i < RoomNumber; i++) {
            RoomPosition[i] = CreateARoom();

        }
    }
    private Position CreateARoom() {
        int x;
        int y;
        int indexX;
        int indexY;
        int RoomWidth;
        int RoomHeight;
        int YOrigin;
        int XOrigin;
        Position temp = new Position();
        x = RandomNum(4,width - 10);
        y = RandomNum(4, height - 10);
        temp.add(x,y);
        XOrigin = x;
        YOrigin = y;
        RoomWidth = RandomNum(2, 9);
        RoomHeight = RandomNum(2, 9);
        for (indexX = 0; indexX < RoomWidth; indexX++) {
            for (indexY = 0; indexY < RoomHeight; indexY++) {
                world[x][y] = Tileset.FLOOR;
                if (x == XOrigin || x == XOrigin + RoomWidth-1 || y == YOrigin || y == YOrigin + RoomHeight - 1) {
                    RoomNextToWall[RoomNextToWallIndex].add(x,y);
                    RoomNextToWallIndex++;

                }
                y++;
            }
            y = YOrigin;
            x++;
        }
        return temp;
    }
    private void CreateCorner() {
        cornerNum = RandomNum(15,25);
        int x;
        int y;
        int i = 0;
        for (i = 0; i < cornerNum; i++) {
            x = RandomNum(4,width - 10);
            y =RandomNum(4,height - 10);
            CornerPosition[i].add(x,y);
            world[x][y] = Tileset.FLOWER;
        }
    }
    private Position shortestPosition(Position p) {
        Position target = CornerPosition[0];
        int minDistance = abs((p.x - CornerPosition[0].x)) + abs((p.y - CornerPosition[0].y));
        for (int i = 0; i < cornerNum; i++) {
            if ( abs((p.x - CornerPosition[i].x)) + abs((p.y - CornerPosition[i].y)) < minDistance){
                target = CornerPosition[i];
            }
        }
        return target;
    }
    private void connectRoomCorner() {
        for (int i =0; i < RoomNumber; i++) {
            ARoomLinkCorner(RoomPosition[i]);
        }
    }
    private void ARoomLinkCorner(Position room) {
        Position target = shortestPosition(room);
        int diffX = target.x - room.x;
        int diffY = target.y - room.y;
        int x = room.x;
        int y = room.y;
        if (abs(diffX) != 0) {
            for (int i = 0; i < abs(diffX); i++) {
                x += (diffX / abs(diffX));
                world[x][y] = Tileset.FLOOR;
                RoomNextToWall[RoomNextToWallIndex].add(x,y);
                RoomNextToWallIndex++;
            }
        }
        if (abs(diffY) != 0) {
            for (int j = 0; j  < abs(diffY); j++) {
                y += (diffY / abs(diffY));
                world[x][y] = Tileset.FLOOR;
                RoomNextToWall[RoomNextToWallIndex].add(x,y);
                RoomNextToWallIndex++;
            }
        }

    }
    private int abs(int a) {
        if (a < 0) {
            return -a;
        }
        return a;
    }

    private void deleteUnusedCorner() {
        for (int i = 0; i < cornerNum; i++) {
            if (world[CornerPosition[i].x][CornerPosition[i].y] == Tileset.FLOWER){
                world[CornerPosition[i].x][CornerPosition[i].y] = Tileset.NOTHING;
            }
        }
    }
    private void CornerToCorner() {
        Position target = new Position();
        for (int i = 0; i < cornerNum; i++) {
            target = NextCorner(CornerPosition[i]);
            ACornerToCorner(CornerPosition[i], target);
        }
    }
    private void ACornerToCorner(Position origin, Position target) {
        int diffX = target.x - origin.x;
        int diffY = target.y - origin.y;
        int x = origin.x;
        int y = origin.y;
        if (abs(diffX) != 0) {
            for (int i = 0; i < abs(diffX); i++) {
                x += (diffX/abs(diffX));
                world[x][y] = Tileset.FLOOR;
                RoomNextToWall[RoomNextToWallIndex].add(x,y);
                RoomNextToWallIndex++;
            }
        }
        if (abs(diffY) != 0) {
            for (int j =0;j < abs(diffY); j++) {
                y += (diffY/abs(diffY));
                world[x][y] = Tileset.FLOOR;
                RoomNextToWall[RoomNextToWallIndex].add(x,y);
                RoomNextToWallIndex++;

            }
        }
    }
    private Position NextCorner(Position corner) {
        Position target = new Position();
        target = corner;
        int MinDiffX = 200;
        for (int i = 0; i < cornerNum; i++) {
            if (CornerPosition[i].x - corner.x > 0 && CornerPosition[i].x - corner.x < MinDiffX) {
                MinDiffX = CornerPosition[i].x - corner.x;
                target = CornerPosition[i];
            }
            if (CornerPosition[i].x - corner.x == MinDiffX){
                if (CornerPosition[i].y - corner.y > target.y - corner.y) {
                    target = CornerPosition[i];
                }
            }
        }
        return target;
    }
    private void AddWall() {
        for (int i = 0; i < RoomNextToWallIndex; i++) {
            addTile(RoomNextToWall[i]);
        }
    }
    private void addTile(Position p) {
        Position up = new Position();
        Position down = new Position();
        Position left = new Position();
        Position right = new Position();
        Position topRight = new Position();
        Position topLeft = new Position();
        Position downRight = new Position();
        Position downLeft = new Position();
        up.add(p.x, p.y+1);
        down.add(p.x,p.y -1);
        left.add(p.x-1, p.y);
        right.add(p.x+1,p.y);
        topRight.add(p.x+1,p.y+1);
        topLeft.add(p.x-1,p.y+1);
        downRight.add(p.x+1,p.y-1);
        downLeft.add(p.x-1,p.y-1);
        if (world[up.x][up.y] == Tileset.NOTHING) {
            world[up.x][up.y] = Tileset.WALL;
        }

        if (world[down.x][down.y] == Tileset.NOTHING) {
            world[down.x][down.y] = Tileset.WALL;
        }
        if (world[left.x][left.y] == Tileset.NOTHING) {
            world[left.x][left.y] = Tileset.WALL;
        }
        if (world[right.x][right.y] == Tileset.NOTHING) {
            world[right.x][right.y] = Tileset.WALL;
        }
        if (world[topRight.x][topRight.y] == Tileset.NOTHING) {
            world[topRight.x][topRight.y] = Tileset.WALL;
        }
        if (world[topLeft.x][topLeft.y] == Tileset.NOTHING) {
            world[topLeft.x][topLeft.y] = Tileset.WALL;
        }
        if (world[downRight.x][downRight.y] == Tileset.NOTHING) {
            world[downRight.x][downRight.y] = Tileset.WALL;
        }
        if (world[downLeft.x][downLeft.y] == Tileset.NOTHING) {
            world[downLeft.x][downLeft.y] = Tileset.WALL;
        }
    }
    private void addGoldenWall() {
        boolean judge = false;
        while (judge == false){
            int i = RandomNum(1,200);
            Position p = RoomNextToWall[i];
            Position up = new Position();
            Position down = new Position();
            Position left = new Position();
            Position right = new Position();
            up.add(p.x, p.y+1);
            down.add(p.x,p.y -1);
            left.add(p.x-1, p.y);
            right.add(p.x+1,p.y);
            if (world[up.x][up.y] == Tileset.WALL) {
                world[up.x][up.y] = Tileset.SAND;
                judge = true;
                break;
            }

            if (world[down.x][down.y] == Tileset.WALL) {
                world[down.x][down.y] = Tileset.SAND;
                judge = true;
                break;
            }
            if (world[left.x][left.y] == Tileset.WALL) {
                world[left.x][left.y] = Tileset.SAND;
                judge = true;
                break;

            }
            if (world[right.x][right.y] == Tileset.WALL) {
                world[right.x][right.y] = Tileset.SAND;
                judge = true;
                break;
            }
        }
    }
    private void addPlayer() {
        int i  = RandomNum(1,200);
        Position p = RoomNextToWall[i];
        world[p.x][p.y] = Tileset.PLAYER;
    }
    public static TETile[][] play(long seed) {
        PlayGame newGame = new PlayGame(seed);

        newGame.CreateWorld();
        newGame.CreateRoom();
        newGame.CreateCorner();
        newGame.connectRoomCorner();
        newGame.CornerToCorner();
        newGame.deleteUnusedCorner();
        newGame.AddWall();
        newGame.addGoldenWall();
        newGame.addPlayer();
        return newGame.world;
    }

}
