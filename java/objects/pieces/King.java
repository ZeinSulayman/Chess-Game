package objects.pieces;

import static java.lang.Math.abs;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        if (this.isWhite) {
            return "K";
        } else {
            return "k";
        }
    }

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
            return (abs(nextSpace[1] - currSpace[1]) <= 1) &&
                    (abs(nextSpace[0] - currSpace[0]) <= 1);
    }
}
