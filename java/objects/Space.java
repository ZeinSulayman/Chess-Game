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

    public void setEmpty() {
        isEmpty = true;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
        this.isEmpty = false;
    }

    public String toString() {
        if (this.isEmpty) {
            return "Empty";
        } else {
            return this.piece.toString();
        }
    }

    public boolean isEmpty() {
        return isEmpty;
    }
}
