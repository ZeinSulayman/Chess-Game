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

    public boolean isLegalMove(int[] curr_space, int[] next_space) {
        return (abs(next_space[1] - curr_space[1]) == 2 && abs(next_space[0] - curr_space[0]) == 1) ||
                (abs(next_space[1] - curr_space[1]) == 1 && abs(next_space[0] - curr_space[0]) == 2);
    }
}
