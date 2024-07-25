import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class TicTacToe {

    int boardWidth = 600;
    int boardHeight = 650; //50px for tex panel on top

    JFrame frame = new JFrame("Java-Tic-Tac-Toe");
    JLabel textLabel = new JLabel();
    JPanel textPanel = new JPanel();
    JPanel boardPanel = new JPanel();
    JPanel playersPanel = new JPanel();

    JButton[][] board = new JButton[3][3];
    ImageIcon iconX;
    ImageIcon iconO;
    ImageIcon iconWin;
    ImageIcon iconTie;
    String currentPlayer = "X";
    boolean gameOver = false;
    int turns = 0;

    // fields for player name
    JTextField player1Field;
    JTextField player2Field;
    String player1Name="";
    String player2Name="";
    
    public TicTacToe() {
        //load images
        iconX = new ImageIcon("lib/bluecup.png");
        iconO = new ImageIcon("lib/redcup.png");
        iconWin = new ImageIcon("lib/greencup.png");
        iconTie = new ImageIcon("lib/orangecup.png");


        frame.setVisible(true);
        frame.setSize(boardWidth + 150, boardHeight);
        frame.setLocationRelativeTo(null);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        //set up textLabel
        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.yellow);
        textLabel.setFont(new Font("Arial", Font.BOLD, 24 ));
        textLabel.setHorizontalAlignment(JLabel.CENTER); 
        textLabel.setText("Java-Tic-Tac-Toe");
        textLabel.setOpaque(true);

            // set up textPanel
        textPanel.setLayout(new BorderLayout());
        textPanel.add(textLabel);
        frame.add(textPanel, BorderLayout.NORTH);

        // new panel for player name
        playersPanel.setLayout(new FlowLayout());
        playersPanel.setBackground(Color.CYAN);
        
        JLabel player1Label = new JLabel("Player 1");
        player1Field = new JTextField("Enter name",15);


        JLabel player2Label = new JLabel("Player 2");
        player2Field = new JTextField("Enter 2. name",15);

        playersPanel.add(player1Label);
        playersPanel.add(this.player1Field);

        playersPanel.add(player2Label);
        playersPanel.add(this.player2Field);

        frame.add(playersPanel, BorderLayout.EAST);

        boardPanel.setLayout(new GridLayout(3,3));
        boardPanel.setBackground(Color.gray);
        frame.add(boardPanel, BorderLayout.CENTER);

        // initializing buttons       
        Font tileFont = new Font("Aria", Font.BOLD, 80);

        for (int r=0; r<3; r++){
            for(int c=0; c<3; c++){
                JButton tile = new JButton();
                tile.setPreferredSize(new Dimension(200,200));
                board[r][c] = tile;
                boardPanel.add(tile);

                tile.setBackground(Color.lightGray);
                tile.setForeground(Color.WHITE);
                tile.setFont(tileFont);
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
                                //textLabel.setText(currentPlayer + "'s turn");
                                updateTurnLabel();
                            }

                        }
                    }
                });
            }
        }
        
        this.player1Field.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e) {
                startGameIfReady();
            }
        });

        this.player2Field.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                startGameIfReady();
            }
        });
        frame.pack();
        frame.setVisible(true);
    }

        void updateTurnLabel(){
            if(currentPlayer.equals("X")){
                textLabel.setText(player1Name + "'s turn");
            }else{
                textLabel.setText(player2Name + "'s turn");
            }
        }

        void startGameIfReady() {
            player1Name = player1Field.getText().trim();
            player2Name = player2Field.getText().trim();
            if (!player1Name.isEmpty() && !player2Name.isEmpty()) {
                currentPlayer = "X"; // Start with player 1
                updateTurnLabel();
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
            
        void annouanceWinner(Icon winningIcon) {
            String winner;
            if (winningIcon == iconX){
                winner = player1Name;
            }else{
                winner = player2Name;
            }

            textLabel.setText("Player " + winner + " wins!");
            textLabel.setForeground(Color.green); //text color
            currentPlayer = winner;
            gameOver = true;
            
            //Icon winningIcon = (currentPlayer.equals("Blue Cup"))? iconX : iconO;
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
            textLabel.setText("Tie game!");
            tile.setIcon(iconTie);
        }

        
}



