package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class adventureMode extends challengeMode {

    private int level;

    adventureMode(int WIDTH, int HEIGHT, Random RANDOM, int level) {
        super(WIDTH, HEIGHT, RANDOM);
        this.level = level;
    }

    @Override
    TETile[][] display(map world) {
        // plots the exit and the player's position on the world map, and returns the matrix
        map location = world.copy();
        location.world[location.player.xPos][location.player.yPos] = Tileset.PLAYER;
        for (robot r : robotSet) {
            location.world[r.Pos.xPos][r.Pos.yPos] = Tileset.ROBOT;
        }
        for (int i = 0; i < world.world.length; i++) {
            for (int j = 0; j < world.world[0].length; j++) {
                if (new Position(i,j).distance(world.player) > level) {
                    location.world[i][j] = Tileset.GRASS;
                }
            }
        }
        return location.world;
    }
}
