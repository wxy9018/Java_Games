package byog.Core;

import java.util.Random;

public class adventureModeLevels extends exploreModeLevels{

    adventureModeLevels(int WIDTH, int HEIGHT, Random RANDOM) {
        super(WIDTH, HEIGHT, RANDOM);
        gameOver = false;
    }

    @Override
    void startGame() {
        // shows the option selection menu, interprets user input, and initiates the corresponding gaming mode
        displayHandler display = new displayHandler(this.WIDTH, this.HEIGHT, this.gameInput, this.gameOptions);
        while (!gameOver) {
            char userInput = display.menuDisplay();
            switch (userInput) {
                case 'A': // start the game in easy mode
                    new adventureMode(WIDTH, HEIGHT, RANDOM, EASY).play();
                    break;
                case 'B': // start the game in medium mode
                    new adventureMode(WIDTH, HEIGHT, RANDOM, MEDIUM).play();
                    break;
                case 'C': // start the game in hard mode
                    new adventureMode(WIDTH,HEIGHT,RANDOM, HARD).play();
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
