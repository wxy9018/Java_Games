package byog.Core;

import byog.TileEngine.Tileset;

import java.util.Random;
import java.util.Set;

public class robotController {
    int WIDTH;
    int HEIGHT;
    int numRobot;
    int numFood;
    Random RANDOM;

    robotController(int WIDTH, int HEIGHT, int numRobot, int numFood, Random RANDOM) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.numRobot = numRobot;
        this.numFood = numFood;
        this.RANDOM = RANDOM;
    }

    void robotMove(robot r, map world) {
        // makes a movement of one robot.
        // the legal directions of movement is: moving into FLOOR and not the position robot came from
        Position newPos;
        while (true) {
            int direction = RANDOM.nextInt(6);
            switch (direction) {
                case 0: newPos = new Position(r.Pos.xPos - 1, r.Pos.yPos); break;
                case 1: newPos = new Position(r.Pos.xPos + 1, r.Pos.yPos); break;
                case 2: newPos = new Position(r.Pos.xPos, r.Pos.yPos - 1); break;
                case 3: newPos = new Position(r.Pos.xPos, r.Pos.yPos + 1); break;
                case 4:
                case 5: newPos = new Position(2 * r.Pos.xPos - r.LastPos.xPos, 2 * r.Pos.yPos - r.LastPos.yPos); break;
                default: newPos = r.Pos.copy();
            }
            if (legalRobotMove(r, newPos, world)) {
                r.LastPos = r.Pos;
                r.Pos = newPos;
                break;
            }
        }
    }

    boolean legalRobotMove(robot r, Position newPos, map world) {
        if (newPos.xPos < 0 || newPos.xPos >= WIDTH || newPos.yPos < 0 || newPos.yPos >= HEIGHT) {
            return false;
        }
        int up = world.world[r.Pos.xPos][r.Pos.yPos + 1].equals(Tileset.FLOOR)? 1 : 0;
        int down = world.world[r.Pos.xPos][r.Pos.yPos - 1].equals(Tileset.FLOOR)? 1 : 0;
        int left = world.world[r.Pos.xPos - 1][r.Pos.yPos].equals(Tileset.FLOOR)? 1 : 0;
        int right = world.world[r.Pos.xPos + 1][r.Pos.yPos].equals(Tileset.FLOOR)? 1 : 0;
        int total = up + down + left + right;
        if (world.world[newPos.xPos][newPos.yPos] == Tileset.FLOOR) {
            if (total <= 1) { // only one valid position, which must be the lastPos
                return true;
            } else return !newPos.equal(r.LastPos);
        }
        return false;
    }

    boolean caughtDetection(robot r, Position P) {
        return r.Pos.distance(P) <= 1.5;
    }

    boolean foodDetection(map world) {
        return world.world[world.player.xPos][world.player.yPos].equals(Tileset.FOOD);
    }

    void robotGenerator(map world, Set<robot> robotSet) {
        // creates N = numRobot robots in the map
        for (int i = 0; i < numRobot; i++) {
            robotAdder(world, robotSet);
        }
    }

    void foodGenerator(map world) {
        // creates N = numRobot robots in the map
        for (int i = 0; i < numFood; i++) {
            foodAdder(world);
        }
    }

    void robotAdder(map world, Set<robot> robotSet) {
        // generate a robot at a random position (on floor), add it into the Set, and draw it on the map
        // new robot should be some distance away from the player position
        while (true) {
            for (int i = 1; i < world.world.length - 1; i++) {
                for (int j = 1; j < world.world[0].length - 1; j++) {
                    Position trialPos = new Position(i,j);
                    if (world.world[i][j] == Tileset.FLOOR && RANDOM.nextInt(2000) < 1 && trialPos.distance(world.player) > 5) {
                        robot newRobot = new robot(trialPos, trialPos);
                        robotSet.add(newRobot);
                        return;
                    }
                }
            }
        }
    }

    void foodAdder(map world) {
        // generate a robot at a random position (on floor), add it into the Set, and draw it on the map
        // new robot should be some distance away from the player position
        while (true) {
            for (int i = 1; i < world.world.length - 1; i++) {
                for (int j = 1; j < world.world[0].length - 1; j++) {
                    Position trialPos = new Position(i,j);
                    if (world.world[i][j] == Tileset.FLOOR && RANDOM.nextInt(2000) < 1 && trialPos.distance(world.player) > 5) {
                        world.world[i][j] = Tileset.FOOD;
                        return;
                    }
                }
            }
        }
    }
}
