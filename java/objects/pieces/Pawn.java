package objects.pieces;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        if (this.isWhite) {
            return "P";
        } else {
            return "p";
        }
    }

    public boolean isLegalMove(int[] curr_space, int[] next_space) {
        if (next_space[1] == curr_space[1]) {
            if (this.isWhite) {
                if (curr_space[0] == 6) {
                    return (next_space[0] - curr_space[0]) == -1 || (next_space[0] - curr_space[0]) == -2;
                } else {
                    return (next_space[0] - curr_space[0]) == -1;
                }
            } else {
                if (curr_space[0] == 1) {
                    return (next_space[0] - curr_space[0]) == 1 || (next_space[0] - curr_space[0]) == 2;
                } else {
                    return (next_space[0] - curr_space[0]) == 1;
                }
            }
        }
        return false;
    }
}
