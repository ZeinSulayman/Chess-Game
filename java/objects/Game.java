package objects;

import objects.pieces.*;

import java.util.Arrays;

import java.util.Scanner;

import static java.lang.Math.abs;

public class Game {
    Board board;
    boolean whiteTurn;
    boolean gameEnd;
    boolean whiteKingMoved = false;
    boolean blackKingMoved = false;
    boolean whiteLeftRookMoved = false;
    boolean whiteRightRookMoved = false;
    boolean blackLeftRookMoved = false;
    boolean blackRightRookMoved = false;

    public Game() {
        board = new Board();
        whiteTurn = true;
        gameEnd = false;
    }

    public void printBoard() {
        System.out.println("-----------------------------------------------------------------");
        System.out.println("--------|  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |--------");
        System.out.println("-----------------------------------------------------------------");
        for (int i = 0; i < 8; i++) {
            StringBuilder row = new StringBuilder();
            System.out.println("-----------------------------------------------------------------");
            row.append("|  ").append(i + 1).append("  ||");
            for (int j = 0; j < 8; j++) {
                row.append("|  ");
                if (this.board.spaces[i][j].isEmpty()) {
                    row.append(" ");
                } else {
                    String piece = this.board.spaces[i][j].getPiece().toString();
                    row.append(piece);
                }
                row.append("  ");
            }
            System.out.println(row + "|||  " + (i + 1) + "  |");
        }
        System.out.println("-----------------------------------------------------------------");
        System.out.println("-----------------------------------------------------------------");
        System.out.println("--------|  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |--------");
        System.out.println("-----------------------------------------------------------------");
    }

    public int[][] moveToIndices(String move) {
        int[][] indices = new int[2][2];
        indices[0][1] = (int) move.charAt(0) - 97;
        indices[0][0] = (int) move.charAt(1) - 49;
        indices[1][1] = (int) move.charAt(2) - 97;
        indices[1][0] = (int) move.charAt(3) - 49;
        return indices;
    }

    public String indicesToMove(int[][] moveIndices) {
        return String.valueOf((char) (moveIndices[0][1] + 97)) +
                (char) (moveIndices[0][0] + 49) +
                (char) (moveIndices[1][1] + 97) +
                (char) (moveIndices[1][0] + 49);
    }

    public boolean isValidMove(String move, boolean noPrint) {
        final String INVALID_PAWN_ATTACK = "" + move.charAt(0) + move.charAt(1) + " can only move to " + move.charAt(2) +
                move.charAt(3) + " unless it is attacking.";
        int[][] moveIndices = moveToIndices(move);
//         Check if move is within bounds of the board.
        if (Arrays.equals(moveIndices[0], moveIndices[1]) || move.length() != 4 ||
                (((int) move.charAt(1) < 49 || (int) move.charAt(1) > 56) ||
                        ((int) move.charAt(3) < 49 || (int) move.charAt(3) > 56)) ||
                (((int) move.charAt(0) < 97 || (int) move.charAt(0) > 104) ||
                        ((int) move.charAt(2) < 97 || (int) move.charAt(2) > 104))) {
            if (!noPrint) {
                System.out.println(move + " is not a valid chess move.");
                System.out.println("Chess moves are inputted in the format [a-h][1-8][a-h][1-8] \nrepresenting the coordinate of the piece you want to move first then the coordinate of the space you want to move it into.");
            }
            return false;
        }
//         Check that current space is not empty.
        if (this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].isEmpty()) {
            if (!noPrint) {
                System.out.println("There is no piece at " + move.charAt(0) + move.charAt(1) + ".");
            }
            return false;
        }
//         Check that black player is not moving white piece.
        if (!this.whiteTurn && this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().isWhite()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " is not a black piece.");
            }
            return false;
        }
//         Check that white player is not moving black piece.
        else if (this.whiteTurn && !this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().isWhite()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " is not a white piece.");
            }
            return false;
        }
//         Check that player is not moving into their own piece.
        if (!this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].isEmpty()) {
            if ((this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece().isWhite() && this.whiteTurn) ||
                    (!this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece().isWhite() && !this.whiteTurn)) {
                if (!noPrint) {
                    System.out.println("" + move.charAt(2) + move.charAt(3) + " is already occupied by one of your pieces.");
                }
                return false;
            }
        }
//         Check that if player is attacking with a white pawn then it is a legal attack.
        if (this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().toString().equals("P")) {
            if ((moveIndices[1][0] - moveIndices[0][0]) == -1 && abs(moveIndices[1][1] - moveIndices[0][1]) == 1) {
                if (this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].isEmpty()) {
                    if (!noPrint) {
                        System.out.println(INVALID_PAWN_ATTACK);
                    }
                    return false;
                } else {
                    return true;
                }
            }
//         Check that if player is attacking with a black pawn then it is a legal attack.
        } else if (this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().toString().equals("p")) {
            if ((moveIndices[1][0] - moveIndices[0][0]) == 1 && abs(moveIndices[1][1] - moveIndices[0][1]) == 1) {
                if (this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].isEmpty()) {
                    if (!noPrint) {
                        System.out.println(INVALID_PAWN_ATTACK);
                    }
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that if white player tries to queen-side castle then it is a valid castle.
        if (Arrays.equals(moveIndices[0], new int[]{7, 4}) && Arrays.equals(moveIndices[1], new int[]{7, 2})) {
            if (this.whiteKingMoved || this.whiteLeftRookMoved) {
                System.out.println("Castling requires the king and subjected rook to have not previously moved.");
                return false;
            } else {
                if (!this.board.spaces[7][1].isEmpty()) {
                    System.out.println("b8 is required to be empty in order to successfully castle.");
                    return false;
                } else if (!this.board.spaces[7][2].isEmpty()) {
                    System.out.println("c8 is required to be empty in order to successfully castle.");
                    return false;
                } else if (!this.board.spaces[7][3].isEmpty()) {
                    System.out.println("d8 is required to be empty in order to successfully castle.");
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that if white player tries to king-side castle then it is a valid castle.
        if (Arrays.equals(moveIndices[0], new int[]{7, 4}) && Arrays.equals(moveIndices[1], new int[]{7, 6})) {
            if (this.whiteKingMoved || this.whiteRightRookMoved) {
                System.out.println("Castling requires the king and subjected rook to have not previously moved.");
                return false;
            } else {
                if (!this.board.spaces[7][6].isEmpty()) {
                    System.out.println("g8 is required to be empty in order to successfully castle.");
                    return false;
                } else if (!this.board.spaces[7][5].isEmpty()) {
                    System.out.println("f8 is required to be empty in order to successfully castle.");
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that if black player tries to queen-side castle then it is a valid castle.
        if (Arrays.equals(moveIndices[0], new int[]{0, 4}) && Arrays.equals(moveIndices[1], new int[]{0, 2})) {
            if (this.blackKingMoved || this.blackLeftRookMoved) {
                System.out.println("Castling requires the king and subjected rook to have not previously moved.");
                return false;
            } else {
                if (!this.board.spaces[0][1].isEmpty()) {
                    System.out.println("b1 is required to be empty in order to successfully castle.");
                    return false;
                } else if (!this.board.spaces[0][2].isEmpty()) {
                    System.out.println("c1 is required to be empty in order to successfully castle.");
                    return false;
                } else if (!this.board.spaces[0][3].isEmpty()) {
                    System.out.println("d1 is required to be empty in order to successfully castle.");
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that if black player tries to king-side castle then it is a valid castle.
        if (Arrays.equals(moveIndices[0], new int[]{0, 4}) && Arrays.equals(moveIndices[1], new int[]{0, 6})) {
            if (this.blackKingMoved || this.blackRightRookMoved) {
                System.out.println("Castling requires the king and subjected rook to have not previously moved.");
                return false;
            } else {
                if (!this.board.spaces[0][6].isEmpty()) {
                    System.out.println("g1 is required to be empty in order to successfully castle.");
                    return false;
                } else if (!this.board.spaces[0][5].isEmpty()) {
                    System.out.println("f1 is required to be empty in order to successfully castle.");
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that player is making a legal move.
        if (!this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().isLegalMove(moveIndices[0], moveIndices[1])) {
            if (!noPrint) {
                System.out.println(move + " is not a legal move.");
            }
            return false;
        }
//        Check that if player is attacking by moving then the attacking piece is not a pawn.
        if (this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].toString().equals("P") ||
                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].toString().equals("p")) {
            if (!this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].isEmpty()) {
                if (!noPrint) {
                    System.out.println("Pawn at " + move.charAt(0) + move.charAt(1) + " can't attack like that.");
                }
                return false;
            }
        }

//        Check that if the player is moving a piece that isn't a horse then the path is clear.
        if (!(this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().toString().equals("h") ||
                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece().toString().equals("H"))) {
            return isClearPath(move, noPrint);
        }


        return true;
    }



    public boolean isClearPath(String move, boolean noPrint) {
        int[][] moveIndices = moveToIndices(move);
        int yDistance = moveIndices[1][0] - moveIndices[0][0];
        int xDistance = moveIndices[1][1] - moveIndices[0][1];
        if (yDistance == 0) {
            if (xDistance > 0) {
                for (int i = 1; i < xDistance; i++) {
                    if (checkHorizontalNotClearPath(move, moveIndices, i, noPrint)) return false;
                }
            } else {
                for (int i = -1; i > xDistance; i--) {
                    if (checkHorizontalNotClearPath(move, moveIndices, i, noPrint)) return false;
                }
            }
        } else if (xDistance == 0) {
            if (yDistance > 0) {
                for (int i = 1; i < yDistance; i++) {
                    if (checkVerticalNotClearPath(move, moveIndices, i, noPrint)) return false;
                }
            } else {
                for (int i = -1; i > yDistance; i--) {
                    if (checkVerticalNotClearPath(move, moveIndices, i, noPrint)) return false;
                }
            }
        } else {
            if (yDistance > 0) {
                if (xDistance > 0) {
                    for (int i = 1; i < yDistance; i++) {
                        if (checkNWDiagonalNotClearPath(move, moveIndices, i, noPrint)) return false;
                    }
                } else {
                    for (int i = 1; i < yDistance; i++) {
                        if (checkNEDiagonalNotClearPath(move, moveIndices, i, noPrint)) return false;
                    }
                }
            } else {
                if (xDistance > 0) {
                    for (int i = -1; i > yDistance; i--) {
                        if (checkNEDiagonalNotClearPath(move, moveIndices, i, noPrint)) return false;
                    }
                } else {
                    for (int i = -1; i > yDistance; i--) {
                        if (checkNWDiagonalNotClearPath(move, moveIndices, i, noPrint)) return false;
                    }
                }
            }

        }
        return true;
    }

    private boolean checkVerticalNotClearPath(String move, int[][] moveIndices, int i, boolean noPrint) {
        if (!this.board.spaces[moveIndices[0][0] + i][moveIndices[0][1]].isEmpty()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                        ((char) (moveIndices[0][1] + 97)) + ((char) (moveIndices[0][0] + i + 49)) + ".");
            }
            return true;
        }
        return false;
    }

    private boolean checkHorizontalNotClearPath(String move, int[][] moveIndices, int i, boolean noPrint) {
        if (!this.board.spaces[moveIndices[0][0]][moveIndices[0][1] + i].isEmpty()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                        ((char) (moveIndices[0][1] + i + 97)) + ((char) (moveIndices[0][0] + 49)) + ".");
            }
            return true;
        }
        return false;
    }

    private boolean checkNWDiagonalNotClearPath(String move, int[][] moveIndices, int i, boolean noPrint) {
        return CheckNotClearPathDiagonal(move, moveIndices, i, i, noPrint);
    }

    private boolean checkNEDiagonalNotClearPath(String move, int[][] moveIndices, int i, boolean noPrint) {
        return CheckNotClearPathDiagonal(move, moveIndices, i, -i, noPrint);
    }

    private boolean CheckNotClearPathDiagonal(String move, int[][] moveIndices, int i, int j, boolean noPrint) {
        if (!this.board.spaces[moveIndices[0][0] + i][moveIndices[0][1] + j].isEmpty()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                        ((char) (moveIndices[0][1] + j + 97)) + ((char) (moveIndices[0][0] + i + 49)) + ".");
            }
            return true;
        }
        return false;
    }

    public void upgradeWhitePawn(int[] currSpace) {
        System.out.println("Upgrade pawn to Queen, Bishop, Rook or Horse?");
        Scanner scanner = new Scanner(System.in);
        boolean upgraded = false;
        while (!upgraded) {
            String piece = scanner.nextLine();
            if (piece.equals("Queen")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Queen(true));
                upgraded = true;
            }
            if (piece.equals("Bishop")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Bishop(true));
                upgraded = true;
            }
            if (piece.equals("Rook")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Rook(true));
                upgraded = true;
            }
            if (piece.equals("Horse")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Horse(true));
                upgraded = true;
            } else {
                System.out.println("Please enter one of \"Queen\", \"Bishop\", \"Rook\", \"Horse\".");
            }
        }
    }

    public void upgradeBlackPawn(int[] currSpace) {
        System.out.println("Upgrade pawn to Queen, Bishop, Rook or Horse?");
        Scanner scanner = new Scanner(System.in);
        String piece = scanner.nextLine();
        boolean upgraded = false;
        while (!upgraded) {
            if (piece.equals("Queen")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Queen(false));
                upgraded = true;
            }
            if (piece.equals("Bishop")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Bishop(false));
                upgraded = true;
            }
            if (piece.equals("Rook")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Rook(false));
                upgraded = true;
            }
            if (piece.equals("Horse")) {
                this.board.spaces[currSpace[0]][currSpace[1]].setPiece(new Horse(false));
                upgraded = true;
            } else {
                System.out.println("Please enter one of \"Queen\", \"Bishop\", \"Rook\", \"Horse\".");
            }
        }
    }

    public boolean isWhiteCheck() {
        int[] whiteKing = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((!this.board.spaces[i][j].isEmpty()) && this.board.spaces[i][j].getPiece().toString().equals("K")) {
                    whiteKing[0] = i;
                    whiteKing[1] = j;
                }
            }
        }
        return isCheck(whiteKing);
    }

    public boolean isBlackCheck() {
        int[] blackKing = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((!this.board.spaces[i][j].isEmpty())) {
                    if (this.board.spaces[i][j].getPiece().toString().equals("k")) {
                        blackKing[0] = i;
                        blackKing[1] = j;
                    }
                }
            }
        }
        return isCheck(blackKing);
    }

    private boolean isCheck(int[] king) {
        this.whiteTurn = !this.whiteTurn;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String move = indicesToMove(new int[][]{new int[]{i, j}, king});
                if (isValidMove(move, true)) {
                    this.whiteTurn = !this.whiteTurn;
                    return true;
                }
            }
        }
        this.whiteTurn = !this.whiteTurn;
        return false;
    }

    public boolean isWhiteCheckmate() {
        if (isWhiteCheck()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (!this.board.spaces[i][j].isEmpty() && this.board.spaces[i][j].getPiece().isWhite()) {
                                int[][] moveIndices = new int[][]{new int[]{i, j}, new int[]{k, l}};
                                if (isValidMove(indicesToMove(moveIndices), true)) {
                                    Piece yourPiece = this.board.spaces[i][j].getPiece();
                                    this.board.spaces[i][j].setEmpty();
                                    Piece theirPiece = this.board.spaces[k][l].getPiece();
                                    this.board.spaces[k][l].setPiece(yourPiece);
                                    if (!isWhiteCheck()) {
                                        this.board.spaces[i][j].setPiece(yourPiece);
                                        this.board.spaces[k][l].setPiece(theirPiece);
                                        return false;
                                    }
                                    this.board.spaces[i][j].setPiece(yourPiece);
                                    this.board.spaces[k][l].setPiece(theirPiece);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackCheckmate() {
        if (isBlackCheck()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            if (!this.board.spaces[i][j].isEmpty() && !this.board.spaces[i][j].getPiece().isWhite()) {
                                int[][] moveIndices = new int[][]{new int[]{i, j}, new int[]{k, l}};
                                if (isValidMove(indicesToMove(moveIndices), true)) {
                                    Piece yourPiece = this.board.spaces[i][j].getPiece();
                                    this.board.spaces[i][j].setEmpty();
                                    Piece theirPiece = this.board.spaces[k][l].getPiece();
                                    this.board.spaces[k][l].setPiece(yourPiece);
                                    if (!isBlackCheck()) {
                                        this.board.spaces[i][j].setPiece(yourPiece);
                                        this.board.spaces[k][l].setPiece(theirPiece);
                                        return false;
                                    }
                                    this.board.spaces[i][j].setPiece(yourPiece);
                                    this.board.spaces[k][l].setPiece(theirPiece);
                                }
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isWhiteStalemate() {
        if (!isWhiteCheck()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            int[][] moveIndices = new int[][]{new int[]{i, j}, new int[]{k, l}};
                            String move = indicesToMove(moveIndices);
                            if (isValidMove(move, true)) {
                                Piece yourPiece = this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece();
                                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setEmpty();
                                Piece theirPiece = this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece();
                                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(yourPiece);
                                if (!isWhiteCheck()) {
                                    this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                                    this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                                    return false;
                                }
                                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean isBlackStalemate() {
        if (!isBlackCheck()) {
            for (int i = 0; i < 8; i++) {
                for (int j = 0; j < 8; j++) {
                    for (int k = 0; k < 8; k++) {
                        for (int l = 0; l < 8; l++) {
                            int[][] moveIndices = new int[][]{new int[]{i, j}, new int[]{k, l}};
                            String move = indicesToMove(moveIndices);
                            if (isValidMove(move, true)) {
                                Piece yourPiece = this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece();
                                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setEmpty();
                                Piece theirPiece = this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece();
                                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(yourPiece);
                                if (!isBlackCheck()) {
                                    this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                                    this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                                    return false;
                                }
                                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                            }
                        }
                    }
                }
            }
            return true;
        }
        return false;
    }

    public boolean moveWhitePiece(String move) {
        int[][] moveIndices = moveToIndices(move);
        if (isValidMove(move, false)) {
            if (!isWhiteCheck()) {
                Piece yourPiece = this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece();
                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setEmpty();
                Piece theirPiece = this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece();
                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(yourPiece);
                // Check for queen-side castling.
                if (Arrays.equals(moveIndices[0], new int[]{7, 4}) && Arrays.equals(moveIndices[1], new int[]{7, 2})) {
                    Piece Rook = this.board.spaces[7][0].getPiece();
                    this.board.spaces[7][0].setEmpty();
                    this.board.spaces[7][3].setPiece(Rook);
                }
                // Check for king-side castling.
                if (Arrays.equals(moveIndices[0], new int[]{7, 4}) && Arrays.equals(moveIndices[1], new int[]{7, 6})) {
                    Piece Rook = this.board.spaces[7][7].getPiece();
                    this.board.spaces[7][7].setEmpty();
                    this.board.spaces[7][5].setPiece(Rook);
                }
                if (isWhiteCheck()) {
                    this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                    this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                    // Check for reverting rook move in queen-side castling.
                    if (Arrays.equals(moveIndices[0], new int[]{7, 4}) && Arrays.equals(moveIndices[1], new int[]{7, 2})) {
                        Piece Rook = this.board.spaces[7][3].getPiece();
                        this.board.spaces[7][3].setEmpty();
                        this.board.spaces[7][0].setPiece(Rook);
                    }
                    // Check for reverting rook move in king-side castling.
                    if (Arrays.equals(moveIndices[0], new int[]{7, 4}) && Arrays.equals(moveIndices[1], new int[]{7, 6})) {
                        Piece Rook = this.board.spaces[7][5].getPiece();
                        this.board.spaces[7][5].setEmpty();
                        this.board.spaces[7][7].setPiece(Rook);
                    }
                    System.out.println(move + " was prevented because it results in your king getting Checked.");
                    return false;
                }
            } else {
                // Prevent castling if king is checked.
                if ((Arrays.equals(moveIndices[0], new int[]{7, 4}) &&
                        Arrays.equals(moveIndices[1], new int[]{7, 6})) ||
                        (Arrays.equals(moveIndices[0], new int[]{7, 4}) &&
                                Arrays.equals(moveIndices[1], new int[]{7, 2}))) {
                    System.out.println("Castling is not allowed when your king is checked.");
                    return false;
                }
                Piece yourPiece = this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece();
                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setEmpty();
                Piece theirPiece = this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece();
                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(yourPiece);
                if (isWhiteCheck()) {
                    this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                    this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                    System.out.println(move + " was prevented because your king is Checked!.");
                    return false;
                }
            }
            // Check for upgrading pawn.
            if (this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece().toString().equals("P")) {
                if (moveIndices[1][0] == 0) {
                    upgradeWhitePawn(moveIndices[1]);
                }
            }
            // Check if moving king or rook (update instance variable if so)
            if (Arrays.equals(moveIndices[0], new int[]{7, 4})) {
                this.whiteKingMoved = true;
            }
            if (Arrays.equals(moveIndices[0], new int[]{7, 0})) {
                this.whiteLeftRookMoved = true;
            }
            if (Arrays.equals(moveIndices[0], new int[]{7, 7})) {
                this.whiteRightRookMoved = true;
            }
            return true;
        }
        return false;
    }

    public boolean moveBlackPiece(String move) {
        int[][] moveIndices = moveToIndices(move);
        if (isValidMove(move, false)) {
            if (!isBlackCheck()) {
                Piece yourPiece = this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece();
                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setEmpty();
                Piece theirPiece = this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece();
                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(yourPiece);
                // Check for queen-side castling.
                if (Arrays.equals(moveIndices[0], new int[]{0, 4}) && Arrays.equals(moveIndices[1], new int[]{0, 2})) {
                    Piece Rook = this.board.spaces[0][0].getPiece();
                    this.board.spaces[0][0].setEmpty();
                    this.board.spaces[0][3].setPiece(Rook);
                }
                // Check for king-side castling.
                if (Arrays.equals(moveIndices[0], new int[]{0, 4}) && Arrays.equals(moveIndices[1], new int[]{0, 6})) {
                    Piece Rook = this.board.spaces[0][7].getPiece();
                    this.board.spaces[0][7].setEmpty();
                    this.board.spaces[0][5].setPiece(Rook);
                }
                if (isBlackCheck()) {
                    this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                    this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                    // Check for reverting rook move in queen-side castling.
                    if (Arrays.equals(moveIndices[0], new int[]{0, 4}) && Arrays.equals(moveIndices[1], new int[]{0, 2})) {
                        Piece Rook = this.board.spaces[0][3].getPiece();
                        this.board.spaces[0][3].setEmpty();
                        this.board.spaces[0][0].setPiece(Rook);
                    }
                    // Check for reverting rook move in king-side castling.
                    if (Arrays.equals(moveIndices[0], new int[]{0, 4}) && Arrays.equals(moveIndices[1], new int[]{0, 6})) {
                        Piece Rook = this.board.spaces[0][5].getPiece();
                        this.board.spaces[0][5].setEmpty();
                        this.board.spaces[0][7].setPiece(Rook);
                    }
                    System.out.println(move + " was prevented because it results in your king getting Checked.");
                    return false;
                }
            } else {
                // Prevent castling if king is checked.
                if ((Arrays.equals(moveIndices[0], new int[]{0, 4}) &&
                        Arrays.equals(moveIndices[1], new int[]{0, 6})) ||
                        (Arrays.equals(moveIndices[0], new int[]{0, 4}) &&
                                Arrays.equals(moveIndices[1], new int[]{0, 2}))) {
                    System.out.println("Castling is not allowed when your king is checked.");
                    return false;
                }
                Piece yourPiece = this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].getPiece();
                this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setEmpty();
                Piece theirPiece = this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece();
                this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(yourPiece);
                if (isBlackCheck()) {
                    this.board.spaces[moveIndices[0][0]][moveIndices[0][1]].setPiece(yourPiece);
                    this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].setPiece(theirPiece);
                    System.out.println(move + " was prevented because your king is Checked.");
                    return false;
                }
            }
            // Check for upgrading pawn.
            if (this.board.spaces[moveIndices[1][0]][moveIndices[1][1]].getPiece().toString().equals("p")) {
                if (moveIndices[1][0] == 7) {
                    upgradeBlackPawn(moveIndices[1]);
                }
            }
            // Check if moving king or rook (update instance variable if so)
            if (Arrays.equals(moveIndices[0], new int[]{0, 0})) {
                this.blackKingMoved = true;
            }
            if (Arrays.equals(moveIndices[0], new int[]{0, 4})) {
                this.blackLeftRookMoved = true;
            }
            if (Arrays.equals(moveIndices[0], new int[]{0, 7})) {
                this.blackRightRookMoved = true;
            }
            return true;
        }
        return false;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (!this.gameEnd) {
            if (whiteTurn) {
                printBoard();
                boolean success = false;
                labelWhite:
                while(!success) {
                    if (isWhiteCheckmate()) {
                        System.out.println("White Checkmate. Black Wins!");
                        this.gameEnd = true;
                        break;
                    }
                    if (isWhiteCheck()) {
                        System.out.println("White Check!");
                    }
                    if (isWhiteStalemate()) {
                        System.out.println("Stalemate. Game ends in a Draw!");
                        this.gameEnd = true;
                        break;
                    }
                    System.out.println("White move:");
                    String move = scanner.nextLine();
                    switch (move) {
                        case "reset":
                            Game newGame = new NewGame();
                            newGame.playGame();
                            this.gameEnd = true;
                            break labelWhite;
                        case "skip":
                            break labelWhite;
                        case "end":
                            this.gameEnd = true;
                            break labelWhite;
                        default:
                            success = moveWhitePiece(move);
                    }
                }
            } else {
                printBoard();
                boolean success = false;
                labelBlack:
                while(!success) {
                    if (isBlackCheckmate()) {
                        System.out.println("Black Checkmate. White Wins!");
                        this.gameEnd = true;
                        break;
                    }
                    if (isBlackCheck()) {
                        System.out.println("Black Check!");
                    }
                    if (isBlackStalemate()) {
                        System.out.println("Stalemate. Game ends in a Draw!");
                        this.gameEnd = true;
                        break;
                    }
                    System.out.println("Black move:");
                    String move = scanner.nextLine();
                    switch (move) {
                        case "reset":
                            Game newGame = new NewGame();
                            newGame.playGame();
                            this.gameEnd = true;
                            break labelBlack;
                        case "skip":
                            break labelBlack;
                        case "end":
                            this.gameEnd = true;
                            break labelBlack;
                        default:
                            success = moveBlackPiece(move);
                    }
                }
            }
            this.whiteTurn = !this.whiteTurn;
        }
    }

    public static void main(String[] args) {

    }
}
