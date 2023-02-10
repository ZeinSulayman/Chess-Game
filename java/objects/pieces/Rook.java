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

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
            return currSpace[0] == nextSpace[0] || currSpace[1] == nextSpace[1];
    }
}
