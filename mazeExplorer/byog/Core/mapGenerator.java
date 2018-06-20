package byog.Core;

import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import java.util.*;

/**
 * this class builds a 2-D TETile array which is going to become the world map.
 * the map is generated with pseudo-random generator.
 * it generates random number of rooms between max/min with given room/hallway ratio
 * a hallway is a room with at least one dimension length to be 3 (defined in another class)
 * the way how to generate the world map:
 * firstly, a door is generated randomly within a given region
 * then, the door is used as a seed to generate a room.
 * then, the doors of the first room is used as seeds to generate following rooms.
 * if a room does not conflict with the existing things on the map, it is a valid room and will be posted on the map
 * keeps repeating the process until the target number of rooms are generated.
*/
public class mapGenerator {
    private static Random RANDOM;
    private static final int minNumRoom = 150;
    private static final int maxNumRoom = 250;
    private static int Room_to_Hallway_Ratio = 2;

    public mapGenerator(Random RANDOM) {
        mapGenerator.RANDOM = RANDOM;
    }

    public static map matrix(int WIDTH, int HEIGHT) {

        // generate the empty world map
        TETile[][] world = emptyWorld(WIDTH, HEIGHT);

        // decides how many things to generate from the pseudo random number
        int numRooms = minNumRoom + RANDOM.nextInt(maxNumRoom-minNumRoom);

        // setup the queues of openDoors and allDoors
        Queue<Door> openDoors = new LinkedList<>();
        Queue<Door> allDoors = new LinkedList<>();

        // generate the first door. This is the position where the map generation starts, and also the point where the player will be
        Position firstDoorPos = new Position(WIDTH/3 + RANDOM.nextInt(WIDTH/3), HEIGHT/3 + RANDOM.nextInt(HEIGHT/3));
        int firstDoorDir = RANDOM.nextInt(4);
        Door firstDoor = new Door(firstDoorPos,firstDoorDir);
        openDoors.add(firstDoor);
        allDoors.add(firstDoor);

        // generate the first room; post it to the map
        Room firstRoom = Room.makeRoom(world, firstDoor, RANDOM);
        doorQueueMaintainer(world, firstRoom, firstDoor, allDoors, openDoors);

        // generate the rest of the rooms, till numRooms is reached or openDoors queue is empty (means no open doors can be used as seed.
        // door is closed (moved out of openDoors queue) when either it has already used as a seed, or N=maxTrial times of trial has been made and did not successfully generate a valid room
        int i = 0;
        while (i < numRooms && !openDoors.isEmpty()) {
            int roomFlag = RANDOM.nextInt(Room_to_Hallway_Ratio + 1);
            // flag >= 1: make an regular random-sized room; flag < 1: make a hallway. The bound of RAMDOM can be changed to tune the rate of room:hallway
            Door queueDoor = openDoors.poll();
            Door seedDoor = Door.mirrorDoor(queueDoor);
            if (roomFlag >= 1) { // make a regular room
                Room newRoom = Room.makeRoom(world, seedDoor, RANDOM);
                if (newRoom.valid) {
                    doorQueueMaintainer(world, newRoom, seedDoor, allDoors, openDoors);
                    i++; // update the number of the generated rooms
                } else {
                    world[queueDoor.doorPos.xPos][queueDoor.doorPos.yPos] = Tileset.WALL;
                }
            } else { // make a hallway
                Hallway newHallway = Hallway.makeHallway(world, seedDoor, RANDOM);
                if (newHallway.valid) {
                    doorQueueMaintainer(world, newHallway, seedDoor, allDoors, openDoors);
                    i++; // update the number of the generated rooms
                } else {
                    world[queueDoor.doorPos.xPos][queueDoor.doorPos.yPos] = Tileset.WALL;
                }
            }
        }

        closeDoor(world, openDoors); // close all remaining doors, if number of trials are exhausted
        openDoor(world,allDoors); // open a door on the wall if the wall of another room happens to block a door
        world[firstDoorPos.xPos][firstDoorPos.yPos] = Tileset.HOME; // set the home position
        Door.makeDoor(world, RANDOM); // generates an exit

        // return the world map
        return new map(world, firstDoorPos);
    }

    // generate the empty world map
    private static TETile[][] emptyWorld(int WIDTH, int HEIGHT) {
        TETile[][] world = new TETile[WIDTH][HEIGHT];
        for (int x = 0; x < WIDTH; x += 1) {
            for (int y = 0; y < HEIGHT; y += 1) {
                world[x][y] = Tileset.NOTHING;
            }
        }
        return world;
    }

    // push the doors of a room into the queues, and post the room on the world map
    private static void doorQueueMaintainer(TETile[][] world, Room newRoom, Door seedDoor, Queue<Door> allDoors, Queue<Door> openDoors) {
        newRoom.postRoom(world);
        for (Door door : newRoom.roomDoors) { // update the door list
            allDoors.add(door);
            if (!door.equal(seedDoor)) {
                openDoors.add(door);
            }
        }
    }

    // close all remaining doors, if number of trials are exhausted
    private static void closeDoor(TETile[][] map, Queue<Door> openDoors) {
        Door close = openDoors.poll();
        while(close != null) {
            map[close.doorPos.xPos][close.doorPos.yPos] = Tileset.WALL;
            close = openDoors.poll();
        }
    }

    // // open a door on the wall if the wall of another room happens to block a door
    private static void openDoor(TETile[][] map, Queue<Door> allDoors) {
        Door thisDoor = allDoors.poll();
        while (thisDoor != null) {
            if (!map[thisDoor.doorPos.xPos][thisDoor.doorPos.yPos].equals(Tileset.WALL)) {
                // if this door is still an open door, i.e. was not closed during the closeDoor operation, then open its contrast door
                Door mirrorDoor = Door.mirrorDoor(thisDoor);
                if (map[mirrorDoor.doorPos.xPos][mirrorDoor.doorPos.yPos].equals(Tileset.WALL)) {
                    map[mirrorDoor.doorPos.xPos][mirrorDoor.doorPos.yPos] = Tileset.FLOOR;
                }
            }
            thisDoor = allDoors.poll();
        }
    }
}
