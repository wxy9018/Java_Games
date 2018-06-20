package byog.Core;

import byog.TileEngine.*;
import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.*;

/**
 * this class plays the classicMode of the game. the whole world map is displayed; the user will find a way to the exit.
 * the class does the following:
 *  calls the mapGenerator and generates a map
 *  accepts user input to determine the moving direction
 *  process the moving direction to get a new map showing the user movement
 *  determines if user has successfully reached the door
 *  save/quit game if user types in the right option
 */
public class classicMode {

    int WIDTH;
    int HEIGHT;
    boolean gameOver;
    Random RANDOM;
    displayHandler display;
    final Font largeFont = new Font("Arial Bold", Font.BOLD,50);
    final Set<Character> gameInput = Set.of('A', 'S', 'W', 'D', 'Q');

    classicMode(int WIDTH, int HEIGHT, Random RANDOM) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.RANDOM = RANDOM;
        gameOver = false;
        display = new displayHandler(this.WIDTH, this.HEIGHT, this.gameInput, null);
    }

    void play() {

        // shows the option selection menu, interprets user input, and initiates the corresponding gaming mode
        moveHandler move = new moveHandler(WIDTH, HEIGHT, display);
        map world = new mapGenerator(RANDOM).matrix(WIDTH, HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + 3);
        ter.renderFrame(move.display(world)); // show the world map
        display.drawFrame("", gameOver); // display the information bar

        while (!gameOver) {
            char userInput = display.userInput();
            switch (userInput) {
                case 'Q': // save the current progress and exit
                    gameOver = true;
                    break;
                case 'W':
                case 'S':
                case 'D':
                case 'A':
                    gameOver = move.userMove(world, userInput); // refresh the map to reflect the user move
                    ter.renderFrame(move.display(world)); // show the updated map
                    display.drawFrame("", gameOver); // display the information bar
                    break;
                default:
                    break; // invalid input. do nothing and continue to display the menu
            }
        }
    }
}