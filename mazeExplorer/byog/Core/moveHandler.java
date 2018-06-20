package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.Set;

/**
 * this class handles user-controlled movements in the map
 */
public class moveHandler {

    private int WIDTH;
    private int HEIGHT;
    private displayHandler display;
    private final Set<TETile> validMove = Set.of(Tileset.FLOOR, Tileset.UNLOCKED_DOOR, Tileset.HOME, Tileset.FOOD);
    private final Font largeFont = new Font("Arial Bold", Font.BOLD,40);
    private final Font smallFont = new Font("Arial Bold", Font.ITALIC,30);

    moveHandler(int WIDTH, int HEIGHT, displayHandler display) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.display = display;
    }

    boolean userMove(map world, char direction) {
        // takes in a map and a direction, updates the map by making the move if it's a legal move.
        // Shows congratulation message if reach the exit
        boolean gameOver = false;
        if (legalMove(world, direction)) {
            makeMove(world, direction);
        }
        if (world.world[world.player.xPos][world.player.yPos].equals(Tileset.UNLOCKED_DOOR)) {
            gameOver = true;
            StdDraw.clear(Color.BLACK);
            StdDraw.setFont(largeFont);
            display.drawFrame("Congratulations! You win the game!", gameOver);
            StdDraw.pause(2000);
            while (StdDraw.hasNextKeyTyped()) continue; // clear the user input queue
        }
        return gameOver;
    }

    boolean legalMove(map world, char direction) {
        // determines if the given direction is a legal move.
        // a legal move is moving into a floor tile.
        switch (direction) {
            case 'W': return world.player.yPos + 1 < HEIGHT && validMove.contains(world.world[world.player.xPos][world.player.yPos + 1]);
            case 'A': return world.player.xPos - 1 >= 0 && validMove.contains(world.world[world.player.xPos - 1][world.player.yPos]);
            case 'S': return world.player.yPos - 1 >= 0 && validMove.contains(world.world[world.player.xPos][world.player.yPos - 1]);
            case 'D': return world.player.xPos + 1 < WIDTH && validMove.contains(world.world[world.player.xPos + 1][world.player.yPos]);
            default: return false;
        }
    }

    void makeMove(map world, char direction) {
        // updates the player's position according to the direction
        switch (direction) {
            case 'W': world.player.yPos++; break;
            case 'A': world.player.xPos--; break;
            case 'S': world.player.yPos--; break;
            case 'D': world.player.xPos++; break;
        }
    }

    TETile[][] display(map world) {
        // plots the exit and the player's position on the world map, and returns the matrix
        map location = world.copy();
        location.world[location.player.xPos][location.player.yPos] = Tileset.PLAYER;
        return location.world;
    }

    TETile[][] displayPartial(map world, int radius) {
        // plots the exit and the player's position on the world map within the radius, and returns the matrix
        // TETiles outside the radius is shown as grass.
        map location = world.copy();
        location.world[location.player.xPos][location.player.yPos] = Tileset.PLAYER;
        for (int i = 0; i < world.world.length; i++) {
            for (int j = 0; j < world.world[0].length; j++) {
                if (new Position(i,j).distance(world.player) > radius) {
                    location.world[i][j] = Tileset.GRASS;
                }
            }
        }
        return location.world;
    }
}
