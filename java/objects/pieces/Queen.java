package objects.pieces;

public class Queen extends Piece {

    public Queen(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        if (this.isWhite) {
            return "Q";
        } else {
            return "q";
        }
    }
}
