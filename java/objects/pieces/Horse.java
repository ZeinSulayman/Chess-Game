package objects.pieces;

import static java.lang.Math.abs;

public class Horse extends Piece {

    public Horse(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        if (this.isWhite) {
            return "H";
        } else {
            return "h";
        }
    }

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
        return (abs(nextSpace[1] - currSpace[1]) == 2 && abs(nextSpace[0] - currSpace[0]) == 1) ||
                (abs(nextSpace[1] - currSpace[1]) == 1 && abs(nextSpace[0] - currSpace[0]) == 2);
    }
}
