package byog.Core;

import byog.TileEngine.TETile;

/** This is the main entry point for the program. This class simply parses
 *  the command line inputs, and lets the byog.Core.Game class take over
 *  in either keyboard or input string mode.
 */
public class Main {

    // this sets the width and height of the game window. pixel count is the following number times 16. (each tile is 16*16 pixels)
    public static final int WIDTH = 110;
    public static final int HEIGHT = 55; // actual height will be 3 tiles more than this number for displaying the information panel.

    // Method used for playing a fresh game. The game should start from the main menu.
    public static void main(String[] args) {
        displayHandler display = new displayHandler(WIDTH, HEIGHT, null, null);
        display.canvasPrepare();
        new newGame(WIDTH,HEIGHT).startGame();
    }
}
