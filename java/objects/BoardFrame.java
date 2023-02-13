package objects;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class BoardFrame extends JFrame {
    Game game;

    public BoardFrame() {
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(1000, 1000);
        this.setVisible(true);

        ImageIcon logo = new ImageIcon("logo.png");
        this.setIconImage(logo.getImage());
        this.getContentPane().setBackground(new Color(0xc9ccaf));
        JPanel board = new JPanel();
        board.setBackground(new Color(0x80450e));
        board.setBounds(100, 100, 800, 800);
        JPanel innerBoard = new JPanel();
//        ImageIcon grid = new ImageIcon("chess_board.png");
//        JLabel gridLabel = new JLabel();
//        gridLabel.setIcon(grid);
//        innerBoard.add(gridLabel);
        innerBoard.setBounds(95, 95, 610, 610);

        Panel[][] panels = new Panel[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {

            }
        }
        board.add(innerBoard);
        this.add(board);
    }

    public void updateBoard(String move) {
        if (this.game.isWhiteTurn())
            this.setTitle("White Turn");
        else
            this.setTitle("Black Turn");
    }

    public String getMove() {
        return "";
    }
}
