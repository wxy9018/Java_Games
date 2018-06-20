package byog.Core;

import byog.TileEngine.TERenderer;
import byog.TileEngine.TETile;
import byog.TileEngine.Tileset;

import java.util.Random;

public class exploreMode extends classicMode {

    private int level;

    exploreMode(int WIDTH, int HEIGHT, Random RANDOM, int level) {
        super(WIDTH, HEIGHT, RANDOM);
        this.level = level;
    }

    @Override
    void play() {

        // shows the option selection menu, interprets user input, and initiates the corresponding gaming mode
        moveHandler move = new moveHandler(WIDTH, HEIGHT, display);
        map world = new mapGenerator(RANDOM).matrix(WIDTH, HEIGHT);
        TERenderer ter = new TERenderer();
        ter.initialize(WIDTH, HEIGHT + 3);
        ter.renderFrame(move.displayPartial(world, level)); // show the world map
        display.drawFrame("", gameOver); // display the information bar

        while (!gameOver) {
            char userInput = display.userInput();
            switch (userInput) {
                case 'Q': // exit
                    gameOver = true;
                    break;
                case 'W':
                case 'S':
                case 'D':
                case 'A':
                    gameOver = move.userMove(world, userInput); // refresh the map to reflect the user move
                    ter.renderFrame(move.displayPartial(world, level)); // show the updated map
                    display.drawFrame("", gameOver); // display the information bar
                    break;
                default:
                    display.userInput();
                    break; // invalid input. do nothing and continue to display the menu
            }
        }
    }
}
