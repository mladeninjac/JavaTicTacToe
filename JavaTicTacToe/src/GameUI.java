import javax.swing.*;

public interface GameUI {
    void setTurnLabel(String currentPlayerName);
    void setTextLabel(String text);
    void updateScoreLabels(String player1Name, int player1Score, String player2Name, int player2Score);
    
    void setButtonIcon(int row, int col,Icon icon);
    void setButtonEnabled(int row, int col, boolean enabled);
    void setBoardIcons(Icon icon);
    void setWinner(String playerName, int[][] winCells);
    void setTie();
    JTextField getPlayer1Field();
    JTextField getPlayer2Field();
    JButton[][] getBoard();
    JLabel getTextLabel();
    JLabel getPlayer1ScoreLabel();
    JLabel getPlayer2ScoreLabel();
    JButton getRestartButton();
}
