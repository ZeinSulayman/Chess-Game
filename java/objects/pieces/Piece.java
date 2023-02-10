package objects.pieces;

public class Piece {
    boolean isWhite;

    Piece(boolean isWhite) {
        this.isWhite = isWhite;
    }

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
        return false;
    }

    public boolean isWhite() {
        return isWhite;
    }
}
