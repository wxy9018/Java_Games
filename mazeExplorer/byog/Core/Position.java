package byog.Core;

public class Position {
    int xPos;
    int yPos;

    Position(int x, int y) {
        xPos = x;
        yPos = y;
    }

    boolean equal(Position compare) {
        return this.xPos == compare.xPos && this.yPos == compare.yPos;
    }

    Position copy() {
        return new Position(xPos, yPos);
    }

    double distance(Position target) {
        return Math.sqrt((target.xPos-this.xPos) * (target.xPos-this.xPos) + (target.yPos-this.yPos) * (target.yPos-this.yPos));
    }
}
