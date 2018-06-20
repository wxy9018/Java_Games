package byog.Core;

import edu.princeton.cs.introcs.StdDraw;

import java.awt.*;
import java.util.*;


/**
 * Utility class for displaying purposes. Includes methods for displaying something on the canvas
 */
public class displayHandler {

    private int WIDTH;
    private int HEIGHT;
    private Set<Character> validInput; // valid user inputs
    private LinkedList<String> options; // valid options to display on the menu
    private final Font largeFont = new Font("Arial Bold", Font.BOLD,40);
    private final Font smallFont = new Font("Arial Bold", Font.ITALIC,30);
    private final Font tinyFont = new Font("Arial Bold", Font.ITALIC,15);

    // constructor
    displayHandler(int WIDTH, int HEIGHT, Set<Character> validInput, LinkedList<String> options) {
        this.WIDTH = WIDTH;
        this.HEIGHT = HEIGHT;
        this.validInput = validInput;
        this.options = options;
    }

    // prepare the canvas
    void canvasPrepare() {
        StdDraw.setCanvasSize(WIDTH * 16, (HEIGHT + 3) * 16);
        StdDraw.setXscale(0, WIDTH);
        StdDraw.setYscale(0, HEIGHT);
        StdDraw.enableDoubleBuffering();
    }

    // displays the menu on the canvas and takes in user input
    char menuDisplay() { // displays the main menu and waits for user input
        char userInput = '*';
        // takes in user input
        int count = 0; // the count variable is to make the cursor flash.
        while (userInput == '*') {
            if (!StdDraw.hasNextKeyTyped()) {
                if (count < 5) {
                    menu('^');
                } else {
                    menu('&');
                }
                StdDraw.pause(100); // did not find user input, pause 100mS and check again
                count++;
                if (count == 10) {
                    count = 0;
                }
                continue;
            }
            userInput = Character.toUpperCase(StdDraw.nextKeyTyped());
        }
        menu(userInput);
        StdDraw.pause(500);
        if (!validInput.contains(userInput)) { // input not valid. show warning message.
            menu('%');
            StdDraw.pause(1000);
        }
        return userInput;
    }

    // Display the menu; show the prompt that waits for user input
    void menu(char dash) {
        StdDraw.clear(Color.BLACK);
        StdDraw.setFont(largeFont);
        StdDraw.setPenColor(Color.WHITE);

        // display menu title
        StdDraw.text(WIDTH/2, HEIGHT/2 + 20, options.get(0));
        StdDraw.setFont(smallFont);

        // display the menu options
        int i = 1;
        int startPos = options.size() * 2;
        while(i < options.size()) {
            StdDraw.textLeft(WIDTH / 2 - 18, HEIGHT / 2 + startPos, options.get(i));
            startPos -= 4;
            i++;
        }

        // display user input prompt
        StdDraw.textLeft(WIDTH/2 - 18, HEIGHT/2 - 12, "Please enter your selections:");
        if (dash == '^') {
            StdDraw.textLeft(WIDTH / 2 + 9, HEIGHT / 2 - 12, "_");
        } else if (dash == '%') {
            StdDraw.textLeft(WIDTH / 2 + 9, HEIGHT / 2 - 12, "Invalid option!");
        } else if (dash != '&') {
            StdDraw.textLeft(WIDTH / 2 + 9, HEIGHT / 2 - 12, String.valueOf(dash));
        }
        StdDraw.show();
    }

    void drawFrame(String s, boolean gameOver) {
        //Take the string and display it in the center of the screen
        //If game is not over, display relevant game information at the top of the screen

        if (!gameOver) { // Draw the UI
            StdDraw.setPenColor(Color.WHITE);
            StdDraw.setFont(tinyFont);
            StdDraw.textLeft(1, HEIGHT + 2, " Moving Actions: 'W'- Up; 'A'- Left; 'D'- Right; 'S'- Down");
            StdDraw.textRight(WIDTH - 1, HEIGHT + 2, "Press 'S' to save progress; Press 'Q' to quit game");
            StdDraw.text(WIDTH/2, HEIGHT + 2, s);
            StdDraw.line(0, HEIGHT + 1, WIDTH, HEIGHT + 1);
        }

        // draw the game text
        StdDraw.setPenColor(Color.WHITE);
        StdDraw.setFont(largeFont);
        if (gameOver) StdDraw.text(WIDTH/2, HEIGHT/2 + 3, s);
        StdDraw.show();
    }

    char userInput() {
        // takes user input without showing anything on the canvas. used in game mode
        char userInput = '*';
        while (userInput == '*') {
            if (!StdDraw.hasNextKeyTyped()) {
                StdDraw.pause(100); // did not find user input, pause 100mS and check again
                continue;
            }
            userInput = Character.toUpperCase(StdDraw.nextKeyTyped());
        }
        return userInput;
    }
}
