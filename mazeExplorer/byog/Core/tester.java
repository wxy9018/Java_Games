package byog.Core;
import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

public class tester {
    private static int WIDTH = 120;
    private static int HEIGHT = 65;
    //private static final long SEED = 20;

    public static void main(String[] args) {
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT);

        //TETile[][] world = new Game().playWithInputString("A99583Q");


        //ter.renderFrame(world);

        /*
        // generate an empty world map
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        */



        /*
        // generate a room on the world map
        Room first = new Room(new Position(50, 20), new Size(5,8), 3, SEED);
        System.out.println(first.interSect(world));
        first.postRoom(world);
        ter.renderFrame(world);
        */
    }
}
