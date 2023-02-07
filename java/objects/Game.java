package objects;

import objects.pieces.Piece;

import java.util.Arrays;

import java.util.Scanner;

import static java.lang.Math.abs;

public class Game {
    Board board;
    boolean white_turn;
    boolean game_end;

    public Game() {
        board = new Board();
        white_turn = true;
        game_end = false;
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

    public String indicesToMove(int[][] move_indices) {
        return String.valueOf((char) (move_indices[0][1] + 97)) +
                (char) (move_indices[0][0] + 49) +
                (char) (move_indices[1][1] + 97) +
                (char) (move_indices[1][0] + 49);
    }

    public boolean isValidMove(String move, boolean noPrint) {
        final String INVALID_PAWN_ATTACK = "" + move.charAt(0) + move.charAt(1) + " can only move to " + move.charAt(2) +
                move.charAt(3) + " unless it is attacking.";
        int[][] move_indices = moveToIndices(move);
//         Check if move is within bounds of the board.
        for (int j = 0; j < 2; j++) {
            for (int x : move_indices[j]) {
                if ((x < 0 || x > 7) || Arrays.equals(move_indices[0], move_indices[1])) {
                    if (!noPrint) {
                        System.out.println(move + " is not a valid chess move.");
                    }
                    return false;
                }
            }
        }
//         Check that current space is not empty.
        if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].isEmpty()) {
            if (!noPrint) {
                System.out.println("There is no piece at " + move.charAt(0) + move.charAt(1) + ".");
            }
            return false;
        }
//         Check that black player is not moving white piece.
        if (!this.white_turn && this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().isWhite()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " is not a black piece.");
            }
            return false;
        }
//         Check that white player is not moving black piece.
        else if (this.white_turn && !this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().isWhite()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " is not a white piece.");
            }
            return false;
        }
//         Check that player is not moving into their own piece.
        if (!this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
            if ((this.board.spaces[move_indices[1][0]][move_indices[1][1]].getPiece().isWhite() && this.white_turn) ||
                    (!this.board.spaces[move_indices[1][0]][move_indices[1][1]].getPiece().isWhite() && !this.white_turn)) {
                if (!noPrint) {
                    System.out.println("" + move.charAt(2) + move.charAt(3) + " is already occupied by one of your pieces.");
                }
                return false;
            }
        }
//         Check that if player is attacking with a white pawn then it is a legal attack.
        if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().toString().equals("P")) {
            if ((move_indices[1][0] - move_indices[0][0]) == -1 && abs(move_indices[1][1] - move_indices[0][1]) == 1) {
                if (this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
                    if (!noPrint) {
                        System.out.println(INVALID_PAWN_ATTACK);
                    }
                    return false;
                } else {
                    return true;
                }
            }
//         Check that if player is attacking with a black pawn then it is a legal attack.
        } else if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().toString().equals("p")) {
            if ((move_indices[1][0] - move_indices[0][0]) == 1 && abs(move_indices[1][1] - move_indices[0][1]) == 1) {
                if (this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
                    if (!noPrint) {
                        System.out.println(INVALID_PAWN_ATTACK);
                    }
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that player is making a legal move.
        if (!this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().isLegalMove(move_indices[0], move_indices[1])) {
            if (!noPrint) {
                System.out.println(move + " is not a legal move.");
            }
            return false;
        }
//        Check that if player is attacking by moving then the attacking piece is not a pawn.
        if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].toString().equals("P") ||
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].toString().equals("p")) {
            if (!this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
                if (!noPrint) {
                    System.out.println("Pawn at " + move.charAt(0) + move.charAt(1) + " can't attack like that.");
                }
                return false;
            }
        }
//         Check that if the player is moving a piece that isn't a horse then the path is clear.
        if (!(this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().toString().equals("h") ||
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece().toString().equals("H"))) {
            return isClearPath(move, noPrint);
        }


        return true;
    }



    public boolean isClearPath(String move, boolean noPrint) {
        int[][] move_indices = moveToIndices(move);
        int y_distance = move_indices[1][0] - move_indices[0][0];
        int x_distance = move_indices[1][1] - move_indices[0][1];
        if (y_distance == 0) {
            if (x_distance > 0) {
                for (int i = 1; i < x_distance; i++) {
                    if (checkHorizontalNotClearPath(move, move_indices, i, noPrint)) return false;
                }
            } else {
                for (int i = -1; i > x_distance; i--) {
                    if (checkHorizontalNotClearPath(move, move_indices, i, noPrint)) return false;
                }
            }
        } else if (x_distance == 0) {
            if (y_distance > 0) {
                for (int i = 1; i < y_distance; i++) {
                    if (checkVerticalNotClearPath(move, move_indices, i, noPrint)) return false;
                }
            } else {
                for (int i = -1; i > y_distance; i--) {
                    if (checkVerticalNotClearPath(move, move_indices, i, noPrint)) return false;
                }
            }
        } else {
            if (y_distance > 0) {
                if (x_distance > 0) {
                    for (int i = 1; i < y_distance; i++) {
                        if (checkNWDiagonalNotClearPath(move, move_indices, i, noPrint)) return false;
                    }
                } else {
                    for (int i = 1; i < y_distance; i++) {
                        if (checkNEDiagonalNotClearPath(move, move_indices, i, noPrint)) return false;
                    }
                }
            } else {
                if (x_distance > 0) {
                    for (int i = -1; i > y_distance; i--) {
                        if (checkNEDiagonalNotClearPath(move, move_indices, i, noPrint)) return false;
                    }
                } else {
                    for (int i = -1; i > y_distance; i--) {
                        if (checkNWDiagonalNotClearPath(move, move_indices, i, noPrint)) return false;
                    }
                }
            }

        }
        return true;
    }

    private boolean checkVerticalNotClearPath(String move, int[][] move_indices, int i, boolean noPrint) {
        if (!this.board.spaces[move_indices[0][0] + i][move_indices[0][1]].isEmpty()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                        ((char) (move_indices[0][1] + 97)) + ((char) (move_indices[0][0] + i + 49)) + ".");
            }
            return true;
        }
        return false;
    }

    private boolean checkHorizontalNotClearPath(String move, int[][] move_indices, int i, boolean noPrint) {
        if (!this.board.spaces[move_indices[0][0]][move_indices[0][1] + i].isEmpty()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                        ((char) (move_indices[0][1] + i + 97)) + ((char) (move_indices[0][0] + 49)) + ".");
            }
            return true;
        }
        return false;
    }

    private boolean checkNWDiagonalNotClearPath(String move, int[][] move_indices, int i, boolean noPrint) {
        return CheckNotClearPathDiagonal(move, move_indices, i, i, noPrint);
    }

    private boolean checkNEDiagonalNotClearPath(String move, int[][] move_indices, int i, boolean noPrint) {
        return CheckNotClearPathDiagonal(move, move_indices, i, -i, noPrint);
    }

    private boolean CheckNotClearPathDiagonal(String move, int[][] move_indices, int i, int j, boolean noPrint) {
        if (!this.board.spaces[move_indices[0][0] + i][move_indices[0][1] + j].isEmpty()) {
            if (!noPrint) {
                System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                        ((char) (move_indices[0][1] + j + 97)) + ((char) (move_indices[0][0] + i + 49)) + ".");
            }
            return true;
        }
        return false;
    }

    public boolean isWhiteCheck() {
        int[] white_king = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((!this.board.spaces[i][j].isEmpty()) && this.board.spaces[i][j].getPiece().toString().equals("K")) {
                    white_king[0] = i;
                    white_king[1] = j;
                }
            }
        }
        return isCheck(white_king);
    }

    public boolean isBlackCheck() {
        int[] black_king = new int[2];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ((!this.board.spaces[i][j].isEmpty())) {
                    if (this.board.spaces[i][j].getPiece().toString().equals("k")) {
                        black_king[0] = i;
                        black_king[1] = j;
                    }
                }
            }
        }
        return isCheck(black_king);
    }

    private boolean isCheck(int[] king) {
        this.white_turn = !this.white_turn;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                String move = indicesToMove(new int[][]{new int[]{i, j}, king});
                if (isValidMove(move, true)) {
                    this.white_turn = !this.white_turn;
                    return true;
                }
            }
        }
        this.white_turn = !this.white_turn;
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
                                    Piece your_piece = this.board.spaces[i][j].getPiece();
                                    this.board.spaces[i][j].setEmpty();
                                    Piece their_piece = this.board.spaces[k][l].getPiece();
                                    this.board.spaces[k][l].setPiece(your_piece);
                                    if (!isWhiteCheck()) {
                                        this.board.spaces[i][j].setPiece(your_piece);
                                        this.board.spaces[k][l].setPiece(their_piece);
                                        return false;
                                    }
                                    this.board.spaces[i][j].setPiece(your_piece);
                                    this.board.spaces[k][l].setPiece(their_piece);
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
                                    Piece your_piece = this.board.spaces[i][j].getPiece();
                                    this.board.spaces[i][j].setEmpty();
                                    Piece their_piece = this.board.spaces[k][l].getPiece();
                                    this.board.spaces[k][l].setPiece(your_piece);
                                    if (!isBlackCheck()) {
                                        System.out.println("" + i + j + k + l);
                                        this.board.spaces[i][j].setPiece(your_piece);
                                        this.board.spaces[k][l].setPiece(their_piece);
                                        return false;
                                    }
                                    this.board.spaces[i][j].setPiece(your_piece);
                                    this.board.spaces[k][l].setPiece(their_piece);
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

    public boolean moveWhitePiece(String move) {
        int[][] move_indices = moveToIndices(move);
        if (isValidMove(move, false)) {
            if (!isWhiteCheck()) {
                Piece your_piece = this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece();
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].setEmpty();
                Piece their_piece = this.board.spaces[move_indices[1][0]][move_indices[1][1]].getPiece();
                this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(your_piece);
                if (isWhiteCheck()) {
                    this.board.spaces[move_indices[0][0]][move_indices[0][1]].setPiece(your_piece);
                    this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(their_piece);
                    System.out.println(move + " was prevented because it results in your king getting Checked.");
                    return false;
                }
            } else {
                Piece your_piece = this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece();
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].setEmpty();
                Piece their_piece = this.board.spaces[move_indices[1][0]][move_indices[1][1]].getPiece();
                this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(your_piece);
                if (isWhiteCheck()) {
                    this.board.spaces[move_indices[0][0]][move_indices[0][1]].setPiece(your_piece);
                    this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(their_piece);
                    System.out.println(move + " was prevented because your king is Checked!.");
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public boolean moveBlackPiece(String move) {
        int[][] move_indices = moveToIndices(move);
        if (isValidMove(move, false)) {
            if (!isBlackCheck()) {
                Piece your_piece = this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece();
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].setEmpty();
                Piece their_piece = this.board.spaces[move_indices[1][0]][move_indices[1][1]].getPiece();
                this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(your_piece);
                if (isBlackCheck()) {
                    this.board.spaces[move_indices[0][0]][move_indices[0][1]].setPiece(your_piece);
                    this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(their_piece);
                    System.out.println(move + " was prevented because it results in your king getting Checked.");
                    return false;
                }
            } else {
                Piece your_piece = this.board.spaces[move_indices[0][0]][move_indices[0][1]].getPiece();
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].setEmpty();
                Piece their_piece = this.board.spaces[move_indices[1][0]][move_indices[1][1]].getPiece();
                this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(your_piece);
                if (isBlackCheck()) {
                    this.board.spaces[move_indices[0][0]][move_indices[0][1]].setPiece(your_piece);
                    this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(their_piece);
                    System.out.println(move + " was prevented because your king is Checked.");
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    public void playGame() {
        Scanner scanner = new Scanner(System.in);
        while (!this.game_end) {
            if (white_turn) {
                printBoard();
                boolean success = false;
                while(!success) {
                    if (isWhiteCheckmate()) {
                        System.out.println("White Checkmate. Black Wins!");
                        this.game_end = true;
                        break;
                    }
                    if (isWhiteCheck()) {
                        System.out.println("White Check!");
                    }
                    System.out.println("White move:");
                    String move = scanner.nextLine();
                    success = moveWhitePiece(move);
                }
            } else {
                printBoard();
                boolean success = false;
                while(!success) {
                    if (isBlackCheckmate()) {
                        System.out.println("Black Checkmate. White Wins!");
                        this.game_end = true;
                        break;
                    }
                    if (isBlackCheck()) {
                        System.out.println("Black Check!");
                    }
                    System.out.println("Black move:");
                    String move = scanner.nextLine();
                    success = moveBlackPiece(move);
                }
            }
            this.white_turn = !this.white_turn;
        }
    }

    public static void main(String[] args) {

    }
}
