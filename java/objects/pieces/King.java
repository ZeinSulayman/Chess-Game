package objects.pieces;

public class King extends Piece {

    public King(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        if (this.isWhite) {
            return "K";
        } else {
            return "k";
        }
    }
}
