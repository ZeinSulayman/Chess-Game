package objects.pieces;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        if (this.isWhite) {
            return "P";
        } else {
            return "p";
        }
    }
}
