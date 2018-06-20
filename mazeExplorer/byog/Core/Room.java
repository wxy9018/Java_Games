package byog.Core;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class Room {

    private static final int maxDimension = 10; // need to be greater than 3. Min dimension is 3 by default.
    private static final int maxDoors = 6;
    private static final int minDoors = 4; // need to be greater than 2
    private static final int maxTrialRoomMaker = 10;

    Position roomPos; // upper left corner of the Room
    Size roomSize; // the size includes the wall thickness
    Door[] roomDoors; // door positions must be on the walls
    boolean valid = true; // this is an indicator if the room is a valid room that does not conflict with anything else in the map

    public Room(){}

    // this is a constructor to generate a room with given door.
    // Room has random size and position, and generates random number of doors, but makes sure the given door is on the wall.

    public Room(Door givenDoor, Random RANDOM) {
        // generate the square room shape
        int deltaX = 1 + RANDOM.nextInt(maxDimension/2);
        int deltaY = 1 + RANDOM.nextInt(maxDimension/2);
        int roomWidth = deltaX + 2 + RANDOM.nextInt(maxDimension-deltaX);
        int roomHeight = deltaY + 2 + RANDOM.nextInt(maxDimension-deltaY);
        switch(givenDoor.openDirection) {
            case 0: roomPos = new Position(givenDoor.doorPos.xPos, givenDoor.doorPos.yPos - deltaY); roomSize = new Size(roomWidth, roomHeight); break;
            case 3: roomPos = new Position(givenDoor.doorPos.xPos + 1 - roomWidth, givenDoor.doorPos.yPos - deltaY); roomSize = new Size(roomWidth, roomHeight); break;
            case 1: roomPos = new Position(givenDoor.doorPos.xPos - deltaX, givenDoor.doorPos.yPos); roomSize = new Size(roomWidth, roomHeight); break;
            case 2: roomPos = new Position(givenDoor.doorPos.xPos-deltaX, givenDoor.doorPos.yPos + 1 - roomHeight); roomSize = new Size(roomWidth, roomHeight); break;
        }

        // Randomly generate N=4~12 doors on the walls, random locations.
        int numDoors = minDoors + RANDOM.nextInt(maxDoors - minDoors);
        roomDoors = new Door[numDoors];
        roomDoors[0] = givenDoor;
        for (int i = 1; i < numDoors; i++) {
            int wallLabel = RANDOM.nextInt(4); // select which wall to generate a door
            switch (wallLabel) {
                case 0: roomDoors[i] = new Door(new Position(roomPos.xPos, roomPos.yPos + 1 + RANDOM.nextInt(roomSize.ySize - 2)), 0); break; // left wall
                case 3: roomDoors[i] = new Door(new Position(roomPos.xPos + roomSize.xSize - 1, roomPos.yPos + 1 + RANDOM.nextInt(roomSize.ySize - 2)), 3); break; // right wall
                case 1: roomDoors[i] = new Door(new Position(roomPos.xPos + 1 + RANDOM.nextInt(roomSize.xSize - 2), roomPos.yPos), 1); break; // bottom wall
                case 2: roomDoors[i] = new Door(new Position(roomPos.xPos + 1 + RANDOM.nextInt(roomSize.xSize - 2), roomPos.yPos + roomSize.ySize - 1), 2); break; // top wall
            }
        }
    }

    // test if the room intersects with anything on the world map. Also tests if the room boundary exceeds the map.
    // this particular test case does not allow two rooms sharing the same wall. Each room must have its separate walls.
    public boolean valid(TETile[][] map) {
        // test if the room boundary exceeds the map
        if (roomPos.xPos < 0 || roomPos.yPos < 0 || roomPos.xPos + roomSize.xSize > map.length || roomPos.yPos + roomSize.ySize > map[0].length) {
            return false;
        }

        // test if the room intersects with anything
        for (int i = roomPos.xPos; i < roomPos.xPos + roomSize.xSize; i++) {
            for (int j = roomPos.yPos; j < roomPos.yPos + roomSize.ySize; j++) {
                if (map[i][j] != Tileset.NOTHING) {
                    return false;
                }
            }
        }
        return true;
    }

    // post the room to the world map
    public void postRoom(TETile[][] map) {
        // draw the four walls
        for (int i = roomPos.xPos; i < roomPos.xPos + roomSize.xSize; i++) {
            map[i][roomPos.yPos] = Tileset.WALL;
            map[i][roomPos.yPos + roomSize.ySize - 1] = Tileset.WALL;
        }
        for (int j = roomPos.yPos; j < roomPos.yPos + roomSize.ySize; j++) {
            map[roomPos.xPos][j] = Tileset.WALL;
            map[roomPos.xPos + roomSize.xSize - 1][j] = Tileset.WALL;
        }

        // draw the floors
        for (int i = roomPos.xPos + 1; i < roomPos.xPos + roomSize.xSize - 1; i ++) {
            for (int j = roomPos.yPos + 1; j < roomPos.yPos + roomSize.ySize - 1; j++) {
                map[i][j] = Tileset.FLOOR;
            }
        }

        // draw the doors
        for (Door door : roomDoors) {
            map[door.doorPos.xPos][door.doorPos.yPos] = Tileset.FLOOR;
        }
    }

    // makes a new room based on a seed door. makes up to maxTrialRoomMaker times of attempt; if fail, declare the room is not valid.
    static Room makeRoom(TETile[][] map, Door givenDoor, Random RANDOM) {
        Room newRoom = new Room(givenDoor, RANDOM);
        int trial = maxTrialRoomMaker; // max number of trials to generate a door
        while(!newRoom.valid(map) && trial > 0) {
            newRoom = new Room(givenDoor, RANDOM);
            trial--;
        }
        if (trial == 0) {
            newRoom.valid = false;
        }
        return newRoom;
    }
}
