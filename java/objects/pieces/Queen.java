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

    public boolean isLegalMove(int[] curr_space, int[] next_space) {
            return curr_space[0] == next_space[0] || curr_space[1] == next_space[1] ||
                    abs(next_space[0] - curr_space[0]) == abs(next_space[1] - curr_space[1]);
    }
}
