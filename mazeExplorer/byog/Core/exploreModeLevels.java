package byog.Core;

import java.util.*;

public class exploreModeLevels {
    int WIDTH;
    int HEIGHT;
    boolean gameOver;
    int EASY = 20;
    int MEDIUM = 15;
    int HARD = 10;
    Random RANDOM;
    final Set<Character> gameInput = Set.of('A', 'B', 'C', 'Q');
    final LinkedList<String> gameOptions = new LinkedList<>(Arrays.asList("Please choose your level:",
            "A: Easy Mode",
            "B: Medium Mode",
            "C: Hard Mode",
            "Q: Quit"));

    exploreModeLevels(int WIDTH, int HEIGHT, Random RANDOM) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.RANDOM = RANDOM;
        gameOver = false;
    }

    void startGame() {
        // shows the option selection menu, interprets user input, and initiates the corresponding gaming mode
        displayHandler display = new displayHandler(this.WIDTH, this.HEIGHT, this.gameInput, this.gameOptions);
        while (!gameOver) {
            char userInput = display.menuDisplay();
            switch (userInput) {
                case 'A': // start the game in easy mode
                    new exploreMode(WIDTH, HEIGHT, RANDOM, EASY).play();
                    break;
                case 'B': // start the game in medium mode
                    new exploreMode(WIDTH, HEIGHT, RANDOM, MEDIUM).play();
                    break;
                case 'C': // start the game in hard mode
                    new exploreMode(WIDTH,HEIGHT,RANDOM, HARD).play();
                    break;
                case 'Q': // quit game. break the loop
                    gameOver = true;
                    break;
                default: // invalid input. do nothing and continue to display the menu
                    break;
            }
        }
    }
}
