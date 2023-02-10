package objects;

import objects.pieces.*;

public class NewGame extends Game {

    public NewGame() {
        this.gameEnd = false;
        this.whiteTurn = true;
        Space[][] board = new Space[][]{new Space[]{
                                                    new Space(new Rook(false)),
                                                    new Space(new Horse(false)),
                                                    new Space(new Bishop(false)),
                                                    new Space(new Queen(false)),
                                                    new Space(new King(false)),
                                                    new Space(new Bishop(false)),
                                                    new Space(new Horse(false)),
                                                    new Space(new Rook(false))},
                                        new Space[]{
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),
                                                    new Space(new Pawn(false)),},
                                        new Space[]{
                                                    new Space(),
                                                    new Space(),
                                                    new Space(),
                                                    new Space(),
                                                    new Space(),
                                                    new Space(),
                                                    new Space(),
                                                    new Space()},
                                        new Space[]{
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space()},
                                        new Space[]{
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space()},
                                        new Space[]{
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space(),
                                                new Space()},
                                        new Space[]{
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),
                                                new Space(new Pawn(true)),},
                                        new Space[]{
                                                new Space(new Rook(true)),
                                                new Space(new Horse(true)),
                                                new Space(new Bishop(true)),
                                                new Space(new Queen(true)),
                                                new Space(new King(true)),
                                                new Space(new Bishop(true)),
                                                new Space(new Horse(true)),
                                                new Space(new Rook(true))}
                                        };
        this.board = new Board(board);
    }
}
