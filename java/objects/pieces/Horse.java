package objects.pieces;

public class Horse extends Piece {

    public Horse(boolean isWhite) {
        super(isWhite);
    }

    @Override
    public String toString() {
        if (this.isWhite) {
            return "H";
        } else {
            return "h";
        }
    }
}
