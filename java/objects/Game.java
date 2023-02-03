package objects;

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
}
