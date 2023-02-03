package objects;

import objects.pieces.Piece;

public class Space {
    boolean isEmpty;
    Piece piece;

    public Space(Piece piece) {
        this.isEmpty = false;
        this.piece = piece;
    }

    public Space() {
        this.isEmpty = true;
        this.piece = null;
    }
}
