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

    public boolean isLegalMove(int[] curr_space, int[] next_space) {
            return abs(next_space[0] - curr_space[0]) == abs(next_space[1] - curr_space[1]);
    }
}
