package byog.Core;

import byog.TileEngine.Tileset;

public class robot {
    Position Pos;
    Position LastPos;

    robot(Position Pos, Position LastPos) {
        this.Pos = Pos;
        this.LastPos = LastPos;
    }

    boolean equal(robot R1) {
        // as long as the positions of robots are the same, we claim them to be equal
        return this.Pos.equal(R1.Pos);
    }
}
