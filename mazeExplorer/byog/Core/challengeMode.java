package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.*;

public class challengeMode extends classicMode {

    int numFood;
    int numRobot;
    int robotSpeed; // movement speed of robot
    Set<robot> robotSet = new HashSet<>();
    robotController robots;
    displayHandler display;
    double health = 100;
    final Set<Character> gameInput = Set.of('A', 'S', 'W', 'D', 'Q');
    TERenderer ter;

    challengeMode(int WIDTH, int HEIGHT, Random RANDOM) {
        super(WIDTH, HEIGHT, RANDOM);
        numFood = 20;
        numRobot = 40;
        robotSpeed = 5; // the smaller the number is, the faster the robot moves
        this.RANDOM = RANDOM;
        robots = new robotController(WIDTH, HEIGHT, numRobot, numFood, RANDOM);
        display = new displayHandler(this.WIDTH, this.HEIGHT, this.gameInput, null);
    }

    @Override
    void play() {

        moveHandler move = new moveHandler(WIDTH, HEIGHT, display);

        // prepares the world map
        map world = new mapGenerator(RANDOM).matrix(WIDTH, HEIGHT);
        ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + 3);

        // prepares the robot map and the food map
        robots.robotGenerator(world, robotSet);
        robots.foodGenerator(world);
        worldDisplay(world, "Health: " + health + "%");

        char userInput = '*';
        int count = 0;
        while (!gameOver && userInput == '*') {
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(100);
                count++;
                if (count >= robotSpeed) {
                    // update robot positions and refresh the drawing
                    robotRefresh(world);
                    //worldDisplay(world, "Health: " + health + "%");

                    // update health condition
                    health = health - 0.5;
                    if (health <= 0) deathMessage(world); // game over; show the game over message on screen for 2 seconds
                    count = 0;
                }
                continue;
            }

            userInput = Character.toUpperCase(StdDraw.nextKeyTyped());
            switch (userInput) {
                case 'Q': // save the current progress and exit
                    gameOver = true;
                    break;
                case 'W':
                case 'S':
                case 'D':
                case 'A':
                    move.userMove(world, userInput); // refresh the map
                    if (robots.foodDetection(world)) foodMessage(world); // if player gets some food, update health status and display info, and refresh the food
                    robot temp = null; // handles the case when player moves and gets caught by a robot
                    for (robot r : robotSet) {
                        if (robots.caughtDetection(r, world.player)) {
                            temp = caughtMessage(world, r);
                        }
                    }
                    if (temp != null) { // remove the robot that caught the user, and generate another robot
                        robotSet.remove(temp);
                        robots.robotAdder(world, robotSet);
                    }
                    worldDisplay(world, "Health: " + health + "%");
                    if (health <= 0) deathMessage(world);
                    userInput = '*';
                    break;
                default:
                    userInput = '*';
                    break; // invalid input. do nothing and continue to display the menu
            }
        }
    }

    void worldDisplay(map world, String s) {
        ter.renderFrame(display(world));
        display.drawFrame(s, gameOver);
    }

    void robotRefresh(map world) { // refreshes the robots positions
        robot temp = null; // this is to store the robot that caught the user
        for (robot r : robotSet) {
            robots.robotMove(r, world);
            temp = caughtMessage(world, r);
        }
        if (temp != null) {
            robotSet.remove(temp);
            robots.robotAdder(world, robotSet);
        }
        worldDisplay(world, "Health: " + health + "%");
    }

    robot caughtMessage(map world, robot r) {
        robot temp = null;
        if (robots.caughtDetection(r, world.player)) {
            health = health - 25;
            if (health < 0) health = 0;
            temp = r;
            worldDisplay(world,"Health: " + health + "%");
            display.drawFrame("BOOM! You've been caught by a robot, loose 25% health", true);
            StdDraw.pause(1000);
            while (StdDraw.hasNextKeyTyped()) StdDraw.nextKeyTyped(); // clear the input queue
        }
        return temp;
    }

    void foodMessage(map world) {
        health = health + 10;
        if (health > 100) health = 100;
        world.world[world.player.xPos][world.player.yPos] = Tileset.FLOOR; // remove the caught food
        robots.foodAdder(world); //generate another food
        worldDisplay(world,"Health: " + health + "%"); // refresh the drawing
        display.drawFrame("Yep! You got some snacks to eat, increase 10% health", true); // display some encouragement
        StdDraw.pause(1000);
        while (StdDraw.hasNextKeyTyped()) StdDraw.nextKeyTyped(); // clear the input queue
    }

    void deathMessage(map world) {
        gameOver = true;
        StdDraw.clear(Color.black);
        display.drawFrame("Dead! Try again!", gameOver);
        StdDraw.pause(2000);
        while (StdDraw.hasNextKeyTyped()) StdDraw.nextKeyTyped(); // clear the input queue
    }

    TETile[][] display(map world) { // plots the exit and the player's position on the world map, and returns the matrix
        map location = world.copy();
        location.world[location.player.xPos][location.player.yPos] = Tileset.PLAYER;
        for (robot r : robotSet) {
            location.world[r.Pos.xPos][r.Pos.yPos] = Tileset.ROBOT;
        }
        return location.world;
    }
}
