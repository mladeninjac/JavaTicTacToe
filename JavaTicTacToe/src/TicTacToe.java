import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {

    int boardWidth = 500;
    int boardHeight = 550; //50px for tex panel on top

    JFrame frame = new JFrame("Java-Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    ImageIcon iconX;
    ImageIcon iconO;
    ImageIcon iconWin;
    ImageIcon iconTie;
    
    String currentPlayer = "X";
    boolean gameOver = false;
    int turns = 0;

    public TicTacToe() {
        //load images
        iconX = new ImageIcon("lib/blue.png");
        iconO = new ImageIcon("lib/redcup.png");
        iconWin = new ImageIcon("lib/greencup.png");
        iconTie = new ImageIcon("lib/orangecup.png");


        frame.setVisible(true);
        frame.setSize(boardWidth, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.yellow);
        textLabel.setFont(new Font("Arial", Font.BOLD, 40));
        textLabel.setHorizontalAlignment(JLabel.CENTER); 
        textLabel.setText("Java-Tic-Tac-Toe");
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

                tile.setBackground(Color.lightGray);
                tile.setForeground(Color.WHITE);
                tile.setFont(new Font("Arial", Font.BOLD, 121));
                tile.setFocusable(false);

                tile.addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e){
                        if(gameOver) 
                            return;

                        JButton tile = (JButton) e.getSource();
                        //condition stoped overriding label
                        if (tile.getIcon() == null){
                            tile.setIcon(currentPlayer.equals("X") ? iconX : iconO);
                            turns++;
                            checkWinner();
                            if (!gameOver) {
                                currentPlayer = currentPlayer.equals("X") ? "O" : "X";
                                textLabel.setText(currentPlayer + "'s turn");
                            }

                        }
                    }
                });
            }
        }
    }

        void checkWinner(){
            // horizontal, rows
            for(int r = 0; r<3; r++){
                if(board[r][0].getIcon() == null) 
                    continue;
                if (board[r][0].getIcon().equals(board[r][1].getIcon()) &&
                    board[r][1].getIcon().equals(board[r][2].getIcon())){
                    annouanceWinner(board[r][0].getIcon());
                    return;
                }
            }

            //vertical, columns
            for(int c = 0; c < 3; c++){
                if(board[0][c].getIcon() == null)
                    continue;
                if(board[0][c].getIcon().equals(board[1][c].getIcon()) &&
                   board[1][c].getIcon().equals(board[2][c].getIcon())) {
                   annouanceWinner(board[0][c].getIcon());
                   return;
                }
            }

            //diagonals
            if(board[0][0].getIcon() != null &&
               board[0][0].getIcon().equals(board[1][1].getIcon())&&
               board[1][1].getIcon().equals(board[2][2].getIcon())){
                annouanceWinner(board[0][0].getIcon());
                return;
            }

            //reverse diagonale
            if(board[0][2].getIcon() != null &&
               board[0][2].getIcon().equals(board[1][1].getIcon()) &&
               board[1][1].getIcon().equals(board[2][0].getIcon())){
                annouanceWinner(board[2][0].getIcon());
                return;
            }

            // tie game
            if (turns == 9){
                for(int r=0; r < 3; r++){
                    for(int c=0; c<3; c++){
                        setTie(board[r][c]);
                        board[r][c].setIcon(iconTie);
                    }
                }
                gameOver= true;
            }
        }
            
        private void annouanceWinner(Icon icon) {
            String winner = (icon == iconX)? "Blue Cup" : "Red Cup";
            textLabel.setText("Player " + winner + " wins!");
            textLabel.setForeground(Color.green); //text color
            currentPlayer = winner;
            gameOver = true;
            
            Icon winningIcon = (currentPlayer.equals("Blue Cup"))? iconX : iconO;
            // checking rows
            for (int r = 0; r < 3; r++) {
                if (board[r][0].getIcon() != null &&
                    board[r][0].getIcon().equals(board[r][1].getIcon()) &&
                    board[r][1].getIcon().equals(board[r][2].getIcon()) &&
                    board[r][0].getIcon().equals(winningIcon)){
                    //set winning icons green
                    board[r][0].setIcon(iconWin);
                    board[r][1].setIcon(iconWin);
                    board[r][2].setIcon(iconWin);
                    return;
                }
            }
            //checking columns
            for (int c = 0; c<3; c++){
                if(board[0][c].getIcon()!= null &&
                   board[0][c].getIcon().equals(board[1][c].getIcon()) &&
                   board[1][c].getIcon().equals(board[2][c].getIcon()) &&
                   board[0][c].getIcon().equals(winningIcon)){
                    // set winning icons green
                   board[0][c].setIcon(iconWin);
                   board[1][c].setIcon(iconWin);
                   board[2][c].setIcon(iconWin);
                    return;
                }

            // diagonale
            if(board[0][0].getIcon() != null &&
               board[0][0].getIcon().equals(board[1][1].getIcon())&&
               board[1][1].getIcon().equals(board[2][2].getIcon())&&
               board[0][0].getIcon().equals(winningIcon)){

               board[0][0].setIcon(iconWin);
               board[1][1].setIcon(iconWin);
               board[2][2].setIcon(iconWin);
                return;
            }

            // reverse diagonale
            if(board[0][2].getIcon() != null &&
               board[0][2].getIcon().equals(board[1][1].getIcon()) &&
               board[1][1].getIcon().equals(board[2][0].getIcon()) &&
               board[2][0].getIcon().equals(winningIcon)){

               board[0][2].setIcon(iconWin);
               board[1][1].setIcon(iconWin);
               board[2][0].setIcon(iconWin);
                return;
            }
        }
    }
    



        void setWinner(JButton tile){
            tile.setForeground(Color.green);
            tile.setBackground(Color.gray);
            textLabel.setText(currentPlayer + " is the winner!");
        }

        void setTie(JButton tile) {
            tile.setForeground(Color.orange);
            tile.setBackground(Color.gray);
            textLabel.setText("Tie!");
            tile.setIcon(iconTie);
        }

        
}



