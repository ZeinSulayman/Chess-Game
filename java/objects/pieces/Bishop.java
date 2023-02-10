package objects.pieces;

import static java.lang.Math.abs;

public class Bishop extends Piece {

    public Bishop(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        if (this.isWhite) {
            return "B";
        } else {
            return "b";
        }
    }

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
            return abs(nextSpace[0] - currSpace[0]) == abs(nextSpace[1] - currSpace[1]);
    }
}
