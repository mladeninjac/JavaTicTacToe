import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe 
{

    int boardWidth = 600;
    int boardHeight = 650; //50px for tex panel on top

    JFrame frame = new JFrame("Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    String playerX = "X";
    String playerO = "O";
    String currentPlayer = playerX;

    TicTacToe()
    {
        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.yellow);
        textLabel.setFont(new Font("Console", Font.BOLD, 50));
        textLabel.setHorizontalAlignment(JLabel.CENTER); 
        textLabel.setText("Tic-Tac-Toe");
        textLabel.setOpaque(true);

        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.gray);
        frame.add(boardPanel);

        for (int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                JButton tile = new JButton();
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.GRAY);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 121));
                tile.setFocusable(false);
                //tile.setText(currentPlayer);

                tile.addActionListener(new ActionListener()
                {
                    public void actionPerformed(ActionEvent e)
                    {
                        JButton tile = (JButton) e.getSource();
                        if (tile.getText()  == "")
                        {
                        tile.setText(currentPlayer);

                        currentPlayer = currentPlayer == playerX ? playerO : playerX;
                        textLabel.setText(currentPlayer + "'s turn");
                        }

                    }
                });
            }
        }



    }


}
