package objects.pieces;

public class Pawn extends Piece {

    public Pawn(boolean isWhite) {
        super(isWhite);
    }

    public String toString() {
        if (this.isWhite) {
            return "P";
        } else {
            return "p";
        }
    }

    public boolean isLegalMove(int[] currSpace, int[] nextSpace) {
        if (nextSpace[1] == currSpace[1]) {
            if (this.isWhite) {
                if (currSpace[0] == 6) {
                    return (nextSpace[0] - currSpace[0]) == -1 || (nextSpace[0] - currSpace[0]) == -2;
                } else {
                    return (nextSpace[0] - currSpace[0]) == -1;
                }
            } else {
                if (currSpace[0] == 1) {
                    return (nextSpace[0] - currSpace[0]) == 1 || (nextSpace[0] - currSpace[0]) == 2;
                } else {
                    return (nextSpace[0] - currSpace[0]) == 1;
                }
            }
        }
        return false;
    }
}
