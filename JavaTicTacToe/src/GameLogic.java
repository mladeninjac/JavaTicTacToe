import javax.swing.*;

public class GameLogic implements Game{
    private static final int SIZE = 3;
    private static final int WIN_SCORE = 5;
    private JButton[][] board;
    private Icon iconX, iconO, iconWin, iconTie;
    private String currentPlayer = "X";
    private boolean gameOver = false;
    private int turns = 0;
    private int player1Score = 0;
    private int player2Score = 0;
    private String player1Name = "";
    private String player2Name = "";
    private GameUI gameUI;
    private GameFrame frame;
    //private String currentPlayerName;

    public GameLogic(JButton[][] board, Icon iconX, Icon iconO, Icon iconWin, Icon iconTie, GameUI gameUI, GameFrame frame) {
        this.board = board;
        this.iconX = iconX;
        this.iconO = iconO;
        this.iconWin = iconWin;
        this.iconTie = iconTie;
        this.gameUI = gameUI;
        this.frame = frame;
    }

    @Override
    public void startGame() {
        frame.updatePlayerNames();
        SwingUtilities.invokeLater(() -> {
            player1Name = gameUI.getPlayer1Field().getText().trim();
            player2Name = gameUI.getPlayer2Field().getText().trim();
            System.out.println("Updated names start" +player1Name+ player2Name);

            gameUI.updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
            gameUI.setTurnLabel(currentPlayer.equals("X") ? player1Name :player2Name);
            resetGame();
        });
    }

    @Override
    public void makeMove(int row, int col) {
        if (gameOver || board[row][col].getIcon() != null) {
            return;
        }

        board[row][col].setIcon(currentPlayer.equals("X") ? iconX : iconO);
        turns++;
        if (checkWinner()) {
            announceWinner();
        } else if (turns == SIZE * SIZE && !gameOver) {
            announceTie();
        } else {
            currentPlayer = currentPlayer.equals("X") ? "O" : "X";
            String currentPlayerName = currentPlayer.equals("X")? player1Name : player2Name;
            System.out.println("Next turn player"+ currentPlayerName);
            gameUI.setTurnLabel(currentPlayerName);
        }
    }
/* 
    @Override
    public void resetGame() {
        if (player1Score >= WIN_SCORE) {
            announceChampion(player1Name);
        }else if(player2Score >= WIN_SCORE){
            announceChampion(player2Name);
        }else{
            gameUI.setTextLabel("New round!" + " " + player1Name + " 's turn");
            resetBoard();
        }
    }*/

    public void resetGame(){
        if(player1Score >= WIN_SCORE || player2Score >= WIN_SCORE){
        }else{
            gameUI.setTextLabel("Nova runda : " + player1Name + "'s turn");
        }
    }
        
    private void announceChampion(String playerName){
        SwingUtilities.invokeLater(()-> {
            gameUI.setTextLabel("<html><font color ='green'>" + playerName + " IS THE CHAMPION !");
            gameUI.setBoardIcons(iconTie);
            resetScores();
            gameOver = true;
            frame.disableBoard();
        });
    }

    private void resetBoard(){
        turns = 0;
        gameOver = false;
        currentPlayer = "X";
        for(int r = 0; r < SIZE; r++){
            for(int c = 0; c < SIZE; c++){
                board[r][c].setIcon(null);
                board[r][c].setEnabled(true);
            }
        }
        gameUI.setTurnLabel(player1Name);
        gameUI.updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
        
    }     

    private void resetScores() {
        player1Score = 0;
        player2Score = 0;
        gameUI.updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
        System.out.println("Scores reset");
    }

    private boolean checkWinner() {
        for (int i = 0; i < SIZE; i++) {
            //check rows
            if (checkLine(board[i][0], board[i][1], board[i][2])){
                highlightWinning(i, 0, i, 1, i, 2);
                return true;
            }
            // check columns
            if (checkLine(board[0][i], board[1][i], board[2][i])) {
                highlightWinning(0, i,1, i,2, i);
                return true;
            }
        }
        //check diagonals
        if (checkLine(board[0][0], board[1][1], board[2][2])){
            highlightWinning(0, 0, 1, 1, 2, 2);
            return true;
        }
        if (checkLine(board[0][2], board[1][1], board[2][0])){
            highlightWinning(0, 2, 1, 1, 2, 0);
            return true;
        }
        return false;
            
    }

    private boolean checkLine(JButton a, JButton b, JButton c) {
        return a.getIcon() != null &&
               a.getIcon().equals(b.getIcon()) &&
               b.getIcon().equals(c.getIcon());
    }

    private void announceWinner() {
        gameOver = true;
        String roundWinner;

        if (currentPlayer.equals("X")){
            player1Score++;
            roundWinner = player1Name;
        }else{
            player2Score++;
            roundWinner = player2Name;
        }

        int[][] winCells = getWinningCells();
        
        gameUI.setWinner(roundWinner, winCells); 
        gameUI.updateScoreLabels(player1Name ,player1Score, player2Name,player2Score);
        
        if(player1Score >= WIN_SCORE){
            gameUI.setTextLabel("<html><font color ='blue'>" + player1Name + "</font> is the CHAMPION! </html>");
            announceChampion(player1Name);
        }else if(player2Score >= WIN_SCORE){
            gameUI.setTextLabel("<html><font color ='red'>" + player2Name + "</font> is the CHAMPION! </html>");
            announceChampion(player2Name);
        }else{
            gameUI.setTextLabel(roundWinner + " wins this round");
            resetBoard();
        }
        gameUI.updateScoreLabels(player1Name, player1Score, player2Name, player2Score);
        //gameUI.setTextLabel(roundWinner + "wins this round.");
        

    }
  
    private int[][] getWinningCells() {
            for (int i = 0; i < SIZE; i++) {
                // Check rows
                if (checkLine(board[i][0], board[i][1], board[i][2])) {
                    return new int[][]{{i, 0}, {i, 1}, {i, 2}};
                }
                // Check columns
                if (checkLine(board[0][i], board[1][i], board[2][i])) {
                    return new int[][]{{0, i}, {1, i}, {2, i}};
                }
            }
            // Check diagonals
            if (checkLine(board[0][0], board[1][1], board[2][2])) {
                return new int[][]{{0, 0}, {1, 1}, {2, 2}};
            }
            if (checkLine(board[0][2], board[1][1], board[2][0])) {
                return new int[][]{{0, 2}, {1, 1}, {2, 0}};
            }
            return null; // No winning cells
        };
    

    private void announceTie() {
        for (int r = 0; r < SIZE; r++) {
            for (int c = 0; c < SIZE; c++) {
                board[r][c].setIcon(iconTie);
            }
        }
        gameOver = true;
        gameUI.setTie();
    }

    private void highlightWinning(int r1,int c1, int r2, int c2, int r3, int c3) {
        board[r1][c1].setIcon(iconWin);
        board[r2][c2].setIcon(iconWin);
        board[r3][c3].setIcon(iconWin);
    }

    public String getCurrentPlayer() {
        return currentPlayer;
    }
    
    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public int getPlayer1Score() {
        return player1Score;
    }

    @Override
    public int getPlayer2Score() {
        return player2Score;
    }
    
}

