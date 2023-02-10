package objects.pieces;

import static java.lang.Math.abs;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        if (this.isWhite) {
            return "Q";
        } else {
            return "q";
        }
    }

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
            return currSpace[0] == nextSpace[0] || currSpace[1] == nextSpace[1] ||
                    abs(nextSpace[0] - currSpace[0]) == abs(nextSpace[1] - currSpace[1]);
    }
}
