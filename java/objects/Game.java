package objects;

import objects.pieces.Piece;

import java.util.Arrays;

import java.util.Scanner;

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
        System.out.println("--------------------------------------------------------");
        System.out.println("-------|  a  |  b  |  c  |  d  |  e  |  f  |  g  |  h  |");
        System.out.println("--------------------------------------------------------");
        for (int i = 0; i < 8; i++) {
            StringBuilder row = new StringBuilder();
            System.out.println("--------------------------------------------------------");
            row.append("|  ").append(i + 1).append("  |");
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
            System.out.println(row + "|");
        }
        System.out.println("--------------------------------------------------------");
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
        int[][] move_indices = moveToIndices(move);
        for (int j = 0; j < 2; j++) {
            for (int x : move_indices[j]) {
                if (x < 0 || x > 7) {
                    System.out.println(move + " is not a valid chess move.");
                    return false;
                }
            }
        }
        if (this.board.spaces[move_indices[0][0]][move_indices[0][1]].isEmpty()) {
            System.out.println("There is no piece at " + move.charAt(0) + move.charAt(1) + ".");
            return false;
        }
        if (!this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece.isLegalMove(move_indices[0], move_indices[1])) {
            System.out.println(move + " is not a legal move.");
            return false;
        }
        return true;
    }

    public boolean movePieceWhite(String move) {
        int[][] move_indices = moveToIndices(move);
        if (isValidMove(move)) {
            Piece piece = this.board.spaces[move_indices[0][0]][move_indices[0][1]].piece;
            System.out.println(this.board.spaces[move_indices[0][0]][move_indices[0][1]].toString());
            this.board.spaces[move_indices[0][0]][move_indices[0][1]].setEmpty();
            System.out.println(this.board.spaces[move_indices[0][0]][move_indices[0][1]].toString());
            System.out.println(this.board.spaces[move_indices[1][0]][move_indices[1][1]].toString());
            this.board.spaces[move_indices[1][0]][move_indices[1][1]].setPiece(piece);
            System.out.println(this.board.spaces[move_indices[1][0]][move_indices[1][1]].toString());
            return true;
        }
        return false;
    }

    public boolean movePieceBlack(String move) {
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
                    success = movePieceWhite(move);
                }
            } else {
                printBoard();
                boolean success = false;
                while(!success) {
                    System.out.println("Black move:");
                    String move = scanner.nextLine();
                    success = movePieceBlack(move);
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
