public interface Game {
    void startGame();
    void makeMove(int row, int col);
    void resetGame();
    boolean isGameOver();
    int getPlayer1Score();
    int getPlayer2Score();
}
