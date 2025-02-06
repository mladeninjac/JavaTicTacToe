import javax.swing.*;
import java.awt.*;


public class GameFrame extends JFrame implements GameUI {

    private static final int WIN_SCORE = 5;
    private JLabel textLabel;
    private JTextField player1Field;
    private JTextField player2Field;
    private JButton[][] board;
    private JLabel player1ScoreLabel;
    private JLabel player2ScoreLabel;
    private JButton restartButton;

    private String player1Name = "Player1";
    private String player2Name = "Player2";
    private String currentPlayer = "X";
    private JLabel turnLabel;

    private int player1Score = 0;
    private int player2Score = 0;
    private final Icon wIcon = new ImageIcon("JavaTicTacToe/lib/greencup.png");

    public GameFrame() {
        setTitle("Java-Tic-Tac-Toe");
        setSize(600, 650);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // set up text label
        textLabel = new JLabel("Java-Tic-Tac-Toe", JLabel.CENTER);
        textLabel.setOpaque(true);
        textLabel.setBackground(Color.DARK_GRAY);
        textLabel.setForeground(Color.YELLOW);
        textLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(textLabel, BorderLayout.NORTH);

        turnLabel = new JLabel("Players turn");
        add(turnLabel);

        //set up players panel
        JPanel playersPanel = new JPanel(new FlowLayout());
        playersPanel.setBackground(Color.CYAN);
        add(playersPanel, BorderLayout.EAST);

        playersPanel.add(new JLabel("Player 1"));
        player1Field = new JTextField(player1Name, 10);
        playersPanel.add(player1Field);

        playersPanel.add(new JLabel("Player 2"));
        player2Field = new JTextField(player2Name, 10);
        playersPanel.add(player2Field);

        //set up board panel
        JPanel boardPanel = new JPanel(new GridLayout(3, 3));
        boardPanel.setBackground(Color.GRAY);
        add(boardPanel, BorderLayout.CENTER);

        //initialization the board array
        board = new JButton[3][3];
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c] = new JButton();
                board[r][c].setPreferredSize(new Dimension(200, 200));
                board[r][c].setBackground(Color.LIGHT_GRAY);
                board[r][c].setForeground(Color.WHITE);
                board[r][c].setFont(new Font("Arial", Font.BOLD, 80));
                board[r][c].setFocusable(false);
                boardPanel.add(board[r][c]);
            }
        }

        //set up score panel
        JPanel scorePanel = new JPanel(new GridLayout(2, 2));
        scorePanel.setBackground(Color.ORANGE);
        add(scorePanel, BorderLayout.WEST);

        player1ScoreLabel = new JLabel(player1Name + " : 0");
        player2ScoreLabel = new JLabel(player2Name + " : 0");
        scorePanel.add(player1ScoreLabel);
        scorePanel.add(player2ScoreLabel);

        restartButton = new JButton("Restart game");
        add(restartButton, BorderLayout.SOUTH);
        
        restartButton.addActionListener(e -> resetBoard());
        pack();
        updatePlayerNames();
    }

    @Override
    public void setTextLabel(String text) {
        SwingUtilities.invokeLater(() -> textLabel.setText(text));
    }

    @Override
    public void updateScoreLabels(String player1Name, int player1Score, String player2Name, int player2Score) {
        player1ScoreLabel.setText(player1Name + " : " + player1Score);
        player2ScoreLabel.setText(player2Name + " : " + player2Score);
    }

    @Override
    public void setTurnLabel(String currentPlayerName) {
        SwingUtilities.invokeLater(() -> {
            if(turnLabel != null){
                turnLabel.setText(currentPlayerName + " 's turn");
            }else{
                System.err.println("Turn label not initialized");
            }
        });
    }

    public void updatePlayerNames() {
        SwingUtilities.invokeLater(()-> {
            String player1NewName = player1Field.getText().trim();
            String player2NewName = player2Field.getText().trim();

            if (player1NewName.isEmpty()) player1NewName = "Player1";
            if (player2NewName.isEmpty()) player2NewName = "Player2";

            System.out.println("Updated names" + player1Name + player2Name);
            
            if (!player1Name.equals(player1NewName) || !player2Name.equals(player2NewName)){
                player1Name = player1NewName;
                player2Name = player2NewName;
                updateScoreLabels(player1NewName, player1Score, player2NewName, player2Score);
                setTurnLabel(currentPlayer.equals("X") ? player1Name : player2Name);
            }
        });
    }
       

    @Override
    public void setButtonIcon(int row, int col, Icon icon) {
        board[row][col].setIcon(icon);
    }

    @Override
    public void setButtonEnabled(int row, int col, boolean enabled) {
        board[row][col].setEnabled(enabled);
    }

    @Override
    public void setBoardIcons(Icon icon) {
        for (int r = 0; r < 3; r++) {
            for (int c = 0; c < 3; c++) {
                board[r][c].setIcon(icon);
            }
        }
    }

    @Override
    public void setWinner(String playerName, int[][] winCells) {
        SwingUtilities.invokeLater(() -> {
            setWinningTiles(winCells);
            updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
            setTextLabel(playerName + " " + " wins only this round"); 
            
        });
    }

    private void resetScores() {
        player1Score = 0;
        player2Score = 0;
        updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
        System.out.println("Score resets");
    }

    private void setWinningTiles(int[][] winCells) {
        if(winCells != null){
            for(int[]cell : winCells){
                int r = cell[0];
                int c = cell[1];
                board[r][c].setIcon(wIcon);
            }
        }else{
        System.err.println("winCells are null");
        }
    }

    public void disableBoard() {
        SwingUtilities.invokeLater(() -> {
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    board[r][c].setEnabled(false);
                }
            }
        });
    }

    @Override
    public void setTie() {
        SwingUtilities.invokeLater(()-> {
            //resetBoard();
            setTextLabel("Tie game!");
            setBoardIcons(new ImageIcon("JavaTicTacToe/lib/orangecup.png"));  
            
        });
        
    }

    private void resetBoard() {
        SwingUtilities.invokeLater(() -> {
            for(int r = 0; r < 3; r++){
                for(int c = 0; c < 3; c++){
                    board[r][c].setEnabled(true);
                    board[r][c].setIcon(null);
                }
            }
    
            updatePlayerNames();
            updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
        });
    }

    @Override
    public JTextField getPlayer1Field() {
        return player1Field;
    }

    @Override
    public JTextField getPlayer2Field() {
        return player2Field;
    }

    @Override
    public JButton[][] getBoard() {
        return board;
    }

    @Override
    public JLabel getTextLabel() {
        return textLabel;
    }

    @Override
    public JLabel getPlayer1ScoreLabel() {
        return player1ScoreLabel;
    }

    @Override
    public JLabel getPlayer2ScoreLabel() {
        return player2ScoreLabel;
    }

    @Override
    public JButton getRestartButton() {
        return restartButton;
    }
}

