package byog.Core;

import java.util.*;

import static java.lang.System.exit;

/**
 * this class starts a new game.
 * it allows the user to choose from different versions of games
 */
public class newGame {

    private int WIDTH;
    private int HEIGHT;
    private boolean gameOver;
    private final long now = new Date().getTime(); // will be used as the seed for randomness
    private Random RANDOM = new Random(now);
    private final Set<Character> gameInput = Set.of('A', 'B', 'C', 'D', 'Q');
    private final LinkedList<String> gameOptions = new LinkedList<>(Arrays.asList("Start Game Options:",
            "A: Classic mode - full map",
            "B: Explore mode - limited vision",
            "C: Challenge mode - catch and seek",
            "D: Adventure mode - catch and seek, limited vision",
            "Q: Quit"));

    newGame(int WIDTH, int HEIGHT) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        gameOver = false;
    }

    void startGame() {
        // shows the option selection menu, interprets user input, and initiates the corresponding gaming mode
        displayHandler display = new displayHandler(this.WIDTH, this.HEIGHT, this.gameInput, this.gameOptions);
        while (!gameOver) {
            char userInput = display.menuDisplay();
            switch (userInput) {
                case 'A': // start the game in classic mode
                    new classicMode(WIDTH, HEIGHT, RANDOM).play();
                    break;
                case 'B': // start the game in explore mode
                    new exploreModeLevels(WIDTH, HEIGHT, RANDOM).startGame();
                    break;
                case 'C': // start the game in challenge mode
                    new challengeMode(WIDTH,HEIGHT,RANDOM).play();
                    break;
                case 'D': // start the game in adventure mode
                    new adventureModeLevels(WIDTH,HEIGHT, RANDOM).startGame();
                    break;
                case 'Q': // quit game. break the loop
                    gameOver = true;
                    break;
                default: // invalid input. do nothing and continue to display the menu
                    //display.menuDisplay();
                    break;
            }
        }
        exit(0);
    }
}
