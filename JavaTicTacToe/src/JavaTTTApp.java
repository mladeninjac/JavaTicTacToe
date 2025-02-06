import javax.swing.*;

public class JavaTTTApp {
    public static void main(String[] args){
        SwingUtilities.invokeLater(() -> {
            // load images for tiles
            GameFrame frame = new GameFrame();
            Icon iconX = new ImageIcon("JavaTicTacToe/lib/bluecup.png");
            Icon iconO = new ImageIcon("JavaTicTacToe/lib/redcup.png");
            Icon iconWin = new ImageIcon("JavaTicTacToe/lib/greencup.png");
            Icon iconTie = new ImageIcon("JavaTicTacToe/lib/orangecup.png");

            // Create UI and game logic
            
            GameLogic gameLogic = new GameLogic(frame.getBoard(), iconX, iconO, iconWin, iconTie, frame, frame);
            frame.setVisible(true);


            // Setup action listeners
            for (int r = 0; r < 3; r++) {
                for (int c = 0; c < 3; c++) {
                    int row = r;
                    int col = c;
                    frame.getBoard()[r][c].addActionListener(e -> {
                        if (!gameLogic.isGameOver()) {
                            gameLogic.makeMove(row, col);
                            updateUI(frame, gameLogic);
                        }
                    });
                }
            }

            frame.getRestartButton().addActionListener(e->{
                gameLogic.startGame();
            });
            
            frame.getPlayer1Field().addActionListener(e -> {
                frame.updatePlayerNames();
                updateUI(frame, gameLogic);
            });
            
            frame.getPlayer2Field().addActionListener(e -> {
                frame.updatePlayerNames();
                updateUI(frame, gameLogic);
            });

            frame.getRestartButton().addActionListener(e -> {
                gameLogic.resetGame();
                updateUI(frame, gameLogic);
            });

            frame.setVisible(true);
        });
    }

    private static void updateUI(GameFrame frame, GameLogic gameLogic) {
        String currentPlayerName = gameLogic.getCurrentPlayer().equals("X") ? frame.getPlayer1Field().getText().trim() : frame.getPlayer2Field().getText().trim();

        String setStatus = gameLogic.isGameOver() ? "Game over" : currentPlayerName +  " 's turn";
        frame.setTextLabel(setStatus);

        frame.updateScoreLabels(frame.getPlayer1Field().getText().trim(), gameLogic.getPlayer1Score(),frame.getPlayer2Field().getText().trim(), gameLogic.getPlayer2Score());
        frame.updatePlayerNames();
    }
}


