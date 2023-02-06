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
                if (this.board.spaces[i][j].isEmpty) {
                    row.append(" ");
                } else {
                    String piece = this.board.spaces[i][j].piece.toString();
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

    public boolean isValidMove(String move) {
        final String INVALID_PAWN_ATTACK = "" + move.charAt(0) + move.charAt(1) + " can only move to " + move.charAt(2) +
                move.charAt(3) + " unless it is attacking.";
        int[][] move_indices = moveToIndices(move);
//         Check if move is within bounds of the board.
//        System.out.println("Check move within bounds");
        for (int j = 0; j < 2; j++) {
            for (int x : move_indices[j]) {
                if ((x < 0 || x > 7) || Arrays.equals(move_indices[0], move_indices[1])) {
                    System.out.println(move + " is not a valid chess move.");
                    return false;
                }
            }
        }
//         Check that current space is not empty.
//        System.out.println("Check curr space occupied");
        if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].isEmpty()) {
            System.out.println("There is no piece at " + move.charAt(0) + move.charAt(1) + ".");
            return false;
        }
//         Check that black player is not moving white piece.
//        System.out.println("Check not moving opponents piece");
        if (!this.white_turn && this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.isWhite()) {
            System.out.println(move.charAt(0) + move.charAt(1) + " is not a black piece.");
            return false;
        }
//         Check that white player is not moving black piece.
        else if (this.white_turn && !this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.isWhite()) {
            System.out.println("" + move.charAt(0) + move.charAt(1) + " is not a white piece.");
            return false;
        }
//         Check that player is not moving into their own piece.
//        System.out.println("Check not friendly-fire");
        if (!this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
            if ((this.board.spaces[move_indices[1][0]][move_indices[1][1]].piece.isWhite() && this.white_turn) ||
                    (!this.board.spaces[move_indices[1][0]][move_indices[1][1]].piece.isWhite() && !this.white_turn)) {
                System.out.println("" + move.charAt(2) + move.charAt(3) + " is already occupied by one of your pieces.");
                return false;
            }
        }
//         Check that if player is attacking with a white pawn then it is a legal attack.
//        System.out.println("Check Legal Pawn Attack");
        if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.toString().equals("P")) {
            if ((move_indices[1][0] - move_indices[0][0]) == -1 && abs(move_indices[1][1] - move_indices[0][1]) == 1) {
                if (this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
                    System.out.println(INVALID_PAWN_ATTACK);
                    return false;
                } else {
                    return true;
                }
            }
//         Check that if player is attacking with a black pawn then it is a legal attack.
        } else if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.toString().equals("p")) {
            if ((move_indices[1][0] - move_indices[0][0]) == 1 && abs(move_indices[1][1] - move_indices[0][1]) == 1) {
                if (this.board.spaces[move_indices[1][0]][move_indices[1][1]].isEmpty()) {
                    System.out.println(INVALID_PAWN_ATTACK);
                    return false;
                } else {
                    return true;
                }
            }
        }
//         Check that player is making a legal move.
//        System.out.println("Check Legal Move");
        if (!this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.isLegalMove(move_indices[0], move_indices[1])) {
            System.out.println(move + " is not a legal move.");
            return false;
        }
//         Check that if the player is moving a piece that isn't a horse then the path is clear.
//        System.out.println("Check Clear Path");
        if (!(this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.toString().equals("h") ||
                this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.toString().equals("H"))) {
            if (!isClearPath(move)) {
                return false;
            }
        }


        return true;
    }

    public boolean isClearPath(String move) {
        int[][] move_indices = moveToIndices(move);
        int y_distance = move_indices[1][0] - move_indices[0][0];
        int x_distance = move_indices[1][1] - move_indices[0][1];
        if (y_distance == 0) {
            if (x_distance > 0) {
                for (int i = 1; i < x_distance; i++) {
                    if (checkHorizontalNotClearPath(move, move_indices, i)) return false;
                }
            } else {
                for (int i = -1; i > x_distance; i--) {
                    if (checkHorizontalNotClearPath(move, move_indices, i)) return false;
                }
            }
        } else if (x_distance == 0) {
            if (y_distance > 0) {
                for (int i = 1; i < y_distance; i++) {
                    if (checkVerticalNotClearPath(move, move_indices, i)) return false;
                }
            } else {
                for (int i = -1; i > y_distance; i--) {
                    if (checkVerticalNotClearPath(move, move_indices, i)) return false;
                }
            }
        } else {
            if (y_distance > 0) {
                if (x_distance > 0) {
                    for (int i = 1; i < y_distance; i++) {
                        if (checkEDiagonalNotClearPath(move, move_indices, x_distance, i)) return false;
                    }
                } else {
                    for (int i = 1; i < y_distance; i++) {
                        if (checkWDiagonalNotClearPath(move, move_indices, x_distance, i)) return false;
                    }
                }
            } else {
                if (x_distance > 0) {
                    for (int i = -1; i > y_distance; i--) {
                        if (checkEDiagonalNotClearPath(move, move_indices, x_distance, i)) return false;
                    }
                } else {
                    for (int i = -1; i > y_distance; i--) {
                        if (checkWDiagonalNotClearPath(move, move_indices, x_distance, i)) return false;
                    }
                }
            }

        }
        return true;
    }

    private boolean checkVerticalNotClearPath(String move, int[][] move_indices, int i) {
        if (!this.board.spaces[move_indices[0][0] + i][move_indices[0][1]].isEmpty()) {
            System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                    ((char) (move_indices[0][1] + 97)) + ((char) (move_indices[0][0] + i + 49)) + ".");
            return true;
        }
        return false;
    }

    private boolean checkHorizontalNotClearPath(String move, int[][] move_indices, int i) {
        if (!this.board.spaces[move_indices[0][0]][move_indices[0][1] + i].isEmpty()) {
            System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                    ((char) (move_indices[0][1] + i + 97)) + ((char) (move_indices[0][0] + 49)) + ".");
            return true;
        }
        return false;
    }

    private boolean checkWDiagonalNotClearPath(String move, int[][] move_indices, int x_distance, int i) {
        for (int j = -1; j > x_distance; j--) {
            if (CheckNotClearPathDiagonal(move, move_indices, i, j)) return true;
        }
        return false;
    }

    private boolean checkEDiagonalNotClearPath(String move, int[][] move_indices, int x_distance, int i) {
        for (int j = i; j < x_distance; j++) {
            if (CheckNotClearPathDiagonal(move, move_indices, i, j)) return true;
        }
        return false;
    }

    private boolean CheckNotClearPathDiagonal(String move, int[][] move_indices, int i, int j) {
        if (!this.board.spaces[move_indices[0][0] + i][move_indices[0][1] + j].isEmpty()) {
            System.out.println("" + move.charAt(0) + move.charAt(1) + " can't jump over piece at " +
                    ((char) (move_indices[0][1] + j + 97)) + ((char) (move_indices[0][0] + i + 49)) + ".");
            return true;
        }
        return false;
    }

//    public boolean isWhiteCheck() {}
//
//    public boolean isBlackCheck() {}

    public boolean movePiece(String move) {
        int[][] move_indices = moveToIndices(move);
        if (isValidMove(move)) {
            Piece piece = this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece;
            this.board.spaces[move_indices[0][0]][move_indices[0][1]].setEmpty();
            this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(piece);
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
                    System.out.println("White move:");
                    String move = scanner.nextLine();
                    success = movePiece(move);
                }
            } else {
                printBoard();
                boolean success = false;
                while(!success) {
                    System.out.println("Black move:");
                    String move = scanner.nextLine();
                    success = movePiece(move);
                }
            }
            this.white_turn = !this.white_turn;
        }
    }

    public static void main(String[] args) {
        Game game = new Game();
        int[][] move_indices = game.moveToIndices("a3b4");
        System.out.println(Arrays.toString(move_indices));
    }
}
