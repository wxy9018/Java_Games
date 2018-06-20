package byog.Core;

import byog.TileEngine.TETile;

public class map {
    TETile[][] world;
    Position player;
    //Position exit;

    map(TETile[][] world, Position player) {
        this.world = world;
        this.player = player;
        //this.exit = exit;
    }

    map copy() {
        Position newPlayer = player.copy();
        //Position newExit = exit.copy();
        TETile[][] newWorld = new TETile[world.length][world[0].length];
        for (int i = 0; i < world.length; i++) {
            for (int j = 0; j < world[0].length; j ++) {
                newWorld[i][j] = world[i][j];
            }
        }
        return new map(newWorld, newPlayer);
    }
}
