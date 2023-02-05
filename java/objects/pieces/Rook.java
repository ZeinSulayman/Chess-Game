package objects.pieces;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }


    public String toString() {
        if (this.isWhite) {
            return "R";
        } else {
            return "r";
        }
    }

    public boolean isLegalMove(int[] curr_space, int[] next_space) {
            return curr_space[0] == next_space[0] || curr_space[1] == next_space[1];
    }
}
