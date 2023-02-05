package objects.pieces;

public class Piece {
    boolean isWhite;

    Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isLegalMove(int[] curr_space, int[] next_space) {
        return false;
    }

}
