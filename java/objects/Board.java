package objects;

public class Board {
    Space[][] spaces;

    public Board(Space[][] spaces) {
        this.spaces = spaces;
    }

    public Board() {
        this.spaces = new Space[8][8];
        int i;
        for (i = 0; i < 8; i++) {
            this.spaces[i] = new Space[]{new Space(), new Space(), new Space(), new Space(),
                                         new Space(), new Space(), new Space(), new Space()};
        }
    }
}
