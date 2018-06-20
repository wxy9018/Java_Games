package byog.Core;

import byog.TileEngine.TETile;

import java.util.Random;

public class Hallway extends Room {

    private static final int maxDimension = 8; // need to be greater than 3. Min dimension is 3 by default.
    private static final int maxDoors = 6;
    private static final int minDoors = 3; // need to be greater than 2
    private static final int maxTrialRoomMaker = 10;

    // this is the constructor that makes a Hallway
    // a hallway is a room with either width or height to be 3
    public Hallway(Door givenDoor, Random RANDOM) {
        int deltaX = 1 + RANDOM.nextInt(maxDimension/2);
        int deltaY = 1 + RANDOM.nextInt(maxDimension/2);
        int roomWidth = deltaX + 2 + RANDOM.nextInt(maxDimension-deltaX);
        int roomHeight = deltaY + 2 + RANDOM.nextInt(maxDimension-deltaY);
        Door contrastDoor;

        switch(givenDoor.openDirection) {
            case 0: {
                roomPos = new Position(givenDoor.doorPos.xPos, givenDoor.doorPos.yPos - 1);
                roomSize = new Size(roomWidth, 3);
                contrastDoor = new Door(new Position(roomPos.xPos + roomWidth - 1, givenDoor.doorPos.yPos), 3);
                break;
            }
            case 3: {
                roomPos = new Position(givenDoor.doorPos.xPos + 1 - roomWidth, givenDoor.doorPos.yPos - 1);
                roomSize = new Size(roomWidth, 3);
                contrastDoor = new Door(new Position(roomPos.xPos, givenDoor.doorPos.yPos), 0);
                break;
            }
            case 1: {
                roomPos = new Position(givenDoor.doorPos.xPos - 1, givenDoor.doorPos.yPos);
                roomSize = new Size(3, roomHeight);
                contrastDoor = new Door(new Position(givenDoor.doorPos.xPos, roomPos.yPos + roomHeight - 1), 2);
                break;
            }
            case 2: {
                roomPos = new Position(givenDoor.doorPos.xPos - 1, givenDoor.doorPos.yPos + 1 - roomHeight);
                roomSize = new Size(3, roomHeight);
                contrastDoor = new Door(new Position(givenDoor.doorPos.xPos, roomPos.yPos), 3);
                break;
            }
            default: {
                roomPos = new Position(0,0);
                roomSize = new Size(1,1);
                contrastDoor = givenDoor;
            }
        }

        // Randomly generate N=4~12 doors on the walls, random locations.
        int numDoors = minDoors + RANDOM.nextInt(maxDoors - minDoors);
        roomDoors = new Door[numDoors];
        roomDoors[0] = givenDoor;
        roomDoors[1] = contrastDoor;
        for (int i = 2; i < numDoors; i++) {
            int wallLabel = RANDOM.nextInt(4); // select which wall to generate a door
            switch (wallLabel) {
                case 0: roomDoors[i] = new Door(new Position(roomPos.xPos, roomPos.yPos + 1 + RANDOM.nextInt(roomSize.ySize - 2)), 0); break; // left wall
                case 3: roomDoors[i] = new Door(new Position(roomPos.xPos + roomSize.xSize - 1, roomPos.yPos + 1 + RANDOM.nextInt(roomSize.ySize - 2)), 3); break; // right wall
                case 1: roomDoors[i] = new Door(new Position(roomPos.xPos + 1 + RANDOM.nextInt(roomSize.xSize - 2), roomPos.yPos), 1); break; // bottom wall
                case 2: roomDoors[i] = new Door(new Position(roomPos.xPos + 1 + RANDOM.nextInt(roomSize.xSize - 2), roomPos.yPos + roomSize.ySize - 1), 2); break; // top wall
            }
        }
    }

    // makes a new room based on a seed door. makes up to maxTrialRoomMaker times of attempt; if fail, declare the room is not valid.
    static Hallway makeHallway(TETile[][] map, Door givenDoor, Random RANDOM) {
        Hallway newHallway = new Hallway(givenDoor, RANDOM);
        int trial = maxTrialRoomMaker; // max number of trials to generate a door
        while(!newHallway.valid(map) && trial > 0) {
            newHallway = new Hallway(givenDoor, RANDOM);
            trial--;
        }
        if (trial == 0) {
            newHallway.valid = false;
        }
        return newHallway;
    }
}
