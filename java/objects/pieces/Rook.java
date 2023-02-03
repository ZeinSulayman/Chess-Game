package objects.pieces;

public class Rook extends Piece {

    public Rook(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        if (this.isWhite) {
            return "R";
        } else {
            return "r";
        }
    }
}
