package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Door{
    Position doorPos;
    int openDirection; // left:0 right:3 top:2 bottom:1. Door always open outward.
    public Door(Position P, int direction) {
        doorPos = P;
        openDirection = direction;
    }
    public boolean equal(Door compare) {
        return this.openDirection == compare.openDirection && this.doorPos.equal(compare.doorPos);
    }

    static Door mirrorDoor(Door queueDoor) { // given a door, get the contrast door
        Position queueDoorPos = queueDoor.doorPos;
        int openDirection = queueDoor.openDirection;
        int newDirection = 3 - openDirection;
        Position newPos;
        switch(openDirection) {
            case 0: newPos = new Position(queueDoorPos.xPos - 1, queueDoorPos.yPos); break;
            case 3: newPos = new Position(queueDoorPos.xPos + 1, queueDoorPos.yPos); break;
            case 1: newPos = new Position(queueDoorPos.xPos, queueDoorPos.yPos - 1); break;
            case 2: newPos = new Position(queueDoorPos.xPos, queueDoorPos.yPos + 1); break;
            default: newPos = queueDoorPos;
        }
        return new Door(newPos, newDirection);
    }

    // makes a door (only a door tile, not a door object) on the map
    static void makeDoor(TETile[][] map, Random RANDOM) {
        while (true) {
            for (int i = 1; i < map.length / 4; i++) {
                for (int j = 1; j < map[0].length - 1; j++) {
                    if (oneDoor(map, i, j, RANDOM)) return;// new Position(i,j);
                }
            }
            for (int i = map.length * 3 / 4; i < map.length - 1; i++) {
                for (int j = 1; j < map[0].length - 1; j++) {
                    if (oneDoor(map, i, j, RANDOM)) return;// new Position(i,j);
                }
            }
            for (int i = 1; i < map.length - 1; i++) {
                for (int j = 1; j < map[0].length / 4; j++) {
                    if (oneDoor(map, i, j, RANDOM)) return;// new Position(i,j);
                }
            }
            for (int i = 1; i < map.length - 1; i++) {
                for (int j = map[0].length * 3 / 4; j < map[0].length - 1; j++) {
                    if (oneDoor(map, i, j, RANDOM)) return;// new Position(i,j);
                }
            }
        }
    }

    private static boolean oneDoor(TETile[][] map, int i, int j, Random RANDOM) {
        // returns true if the given position [i][j] is turned into a door
        // a given position is turned into a door if it can be turned into a door and the probability determines it will be turned into a door
        boolean generate = (RANDOM.nextInt(500) < 1); // this sets the probability if this wall is going to be changed into a door
        TETile self = map[i][j];
        TETile left = map[i - 1][j];
        TETile right = map[i + 1][j];
        TETile top = map[i][j - 1];
        TETile bottom = map[i][j + 1];
        if (self.equals(Tileset.WALL) && generate) {
            if (left.equals(Tileset.WALL) && right.equals(Tileset.WALL)) {
                if ((top.equals(Tileset.NOTHING) && bottom.equals(Tileset.FLOOR)) || (top.equals(Tileset.FLOOR) && bottom.equals(Tileset.NOTHING))) {
                    map[i][j] = Tileset.UNLOCKED_DOOR;
                    return true;
                }
            } else if (top.equals(Tileset.WALL) && bottom.equals(Tileset.WALL)) {
                if ((left.equals(Tileset.FLOOR) && right.equals(Tileset.NOTHING)) || (left.equals(Tileset.NOTHING) && right.equals(Tileset.FLOOR))) {
                    map[i][j] = Tileset.UNLOCKED_DOOR;
                    return true;
                }
            }
        }
        return false;
    }

    /* Unused but fine codes are moved to below
    // open numDoors new doors on the walls on the world map
    private static Door[] generateDoor(TETile[][] map, int numDoors, Random RANDOM) {
        int count = 0;
        Door[] newDoors = new Door[numDoors];
        Door tempDoor;
        while (count < numDoors) {
            for (int i = 1; i < map.length - 1; i++) {
                for (int j = 1; j < map[0].length - 1; j++) {
                    boolean generate = (RANDOM.nextInt(500) < 1); // this sets the probability if this wall is going to be changed into a door
                    tempDoor = makeOneDoor(map, new Position(i, j));
                    if (generate && (tempDoor != null)) {
                        newDoors[count] = tempDoor;
                        System.out.println(tempDoor.doorPos.xPos + " " + tempDoor.doorPos.yPos + " " + tempDoor.openDirection);
                        map[tempDoor.doorPos.xPos][tempDoor.doorPos.yPos] = Tileset.TREE;
                        count++;
                        if (count == numDoors) {
                            return newDoors;
                        }
                    }
                }
            }
        }
        return newDoors;
    }

    // determines if the given position can be turned into a door, and return a door if so. If not, return null
    private static Door makeOneDoor(TETile[][] map, Position Pos) {
        Door oneDoor = null;
        TETile self = map[Pos.xPos][Pos.yPos];
        TETile left = map[Pos.xPos - 1][Pos.yPos];
        TETile right = map[Pos.xPos + 1][Pos.yPos];
        TETile top = map[Pos.xPos][Pos.yPos - 1];
        TETile bottom = map[Pos.xPos][Pos.yPos + 1];
        if (self.equals(Tileset.WALL)) {
            if (left.equals(Tileset.WALL) && right.equals(Tileset.WALL)) {
                if (top.equals(Tileset.NOTHING) && bottom.equals(Tileset.FLOOR)) { // make a top opening door
                    oneDoor = new Door(Pos, 1);
                } else if (top.equals(Tileset.FLOOR) && bottom.equals(Tileset.NOTHING)) { // make a bottom opening door
                    oneDoor = new Door(Pos, 2);
                }
                map[Pos.xPos][Pos.yPos] = Tileset.UNLOCKED_DOOR;
                //System.out.println(Pos.xPos + " " + Pos.yPos);
            } else if (top.equals(Tileset.WALL) && bottom.equals(Tileset.WALL)) {
                if (left.equals(Tileset.NOTHING) && right.equals(Tileset.FLOOR)) { // make a left opening door
                    oneDoor = new Door(Pos, 0);
                } else if (left.equals(Tileset.FLOOR) && right.equals(Tileset.NOTHING)) { // make a right opening door
                    oneDoor = new Door(Pos, 3);
                }
                map[Pos.xPos][Pos.yPos] = Tileset.UNLOCKED_DOOR;
            }
        }
        return oneDoor;
    }
    */
}
