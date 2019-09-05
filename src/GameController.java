/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package Tiles;

public class GameController {
    private Board board;
    private int rows;
    private int cols;
    private int[] selectedTileOne;
    private boolean hasOneTileSelected;
    private int[] selectedTileTwo;
    private int longestComboCount;
    private int currentComboCount;
    private int totalCombo;
    private boolean isGameOver;


    /**
     * GameController initializes the board, controls and runs the game.
     */
    public GameController(){
        this.rows = 6;
        this.cols = 5;
        this.board = new Board(rows,cols);
        board.resetBoard();
        hasOneTileSelected = false;
        selectedTileOne = new int[2];
        selectedTileTwo = new int[2];
        this.isGameOver = false;

    }

    /**
     * Using the mouse input in GUI from the user selects the first tile to be matched
     * Also set if the first tile has been selected to true which information is used if mouseclick event in GameDisplay class
     * @param row row of the selected tile
     * @param col column of the selected tile
     */
    public void setSelectedTileOne(int row, int col){
        if(board.getBoard()[row][col].numTotalItems() != 0){
            selectedTileOne[0] = row;
            selectedTileOne[1] = col;
            hasOneTileSelected = true;
        }
    }

    /**
     * Using the mouse input in GUI from the user selects the second tile to be matched
     * @param row row of the selected tile
     * @param col column of the selected tile
     */
    public void setSelectedTileTwo(int row, int col){
        selectedTileTwo[0] = row;
        selectedTileTwo[1] = col;
    }

    /**
     * get if tile one is selected
     * @return true if tile one is selected
     */
    public boolean getHasOneTileSelected(){
        return hasOneTileSelected;
    }

    /**
     * Get number of rows in board
     * @return number of rows in board
     */
    public int getRows(){
        return rows;
    }

    /**
     * Get number of columns in board
     * @return number of columns in board
     */
    public int getCols(){
        return cols;
    }


    /**
     * Get the board
     * @return the board
     */
    public Board getBoard(){
        return board;
    }


    /**
     * Get the selcted tile one row and columns index in an int array
     * @return row and column index of the selected tile one in array
     */
    public int[] getSelectedTileOne(){
        return  selectedTileOne;
    }

    /**
     * Get the count of longest combo
     * @return count of longest combo
     */
    public int getLongestComboCount(){
        return longestComboCount;
    }

    /**
     * Get the count of current combo
     * @return count of current combo
     */
    public int getCurrentComboCount(){
        return currentComboCount;
    }


    /**
     * Get the count of total number of combos used
     * @return count of total number of combos
     */
    public int getTotalCombo(){
        return totalCombo;
    }

    /**
     * Checks if the game is over by counting total items on board
     */
    public void setIsGameOver(){
        if(board.getNumTotalItems() == 0){
            isGameOver = true;
        }else {
            isGameOver = false;
        }
    }

    /**
     * @return true if the game is over, false otherwise
     */
    public boolean getIsGameOver(){
        return isGameOver;
    }

    /**
     * Reset for a new game if user prompts for a new game
     */
    public void startNewGame(){
        board.resetBoard();
        longestComboCount = 0;
        currentComboCount = 0;
        totalCombo = 0;
        hasOneTileSelected = false;
    }

    /**
     * Swaps the selected-tile-two to selected-tile-one after it has been matched
     * Swap doesn't happen if the selected tile two is empty. In that case next mouseclick
     * selects new selected-tile-one
     */
    public void swapSelection(){
        if(board.getBoard()[selectedTileTwo[0]][selectedTileTwo[1]].numTotalItems()==0){
            hasOneTileSelected = false;
        }else {
            selectedTileOne[0] = selectedTileTwo[0];
            selectedTileOne[1] = selectedTileTwo[1];
        }
    }

    /**
     * If two selected tile are not the same tile check if they are a match
     * if it's a match increment current combo count, check if it's the longest combo
     * if not match that means the combo ended, increment total combo count
     */
    public void play(){
        if(selectedTileOne[0]!= selectedTileTwo[0] || selectedTileOne[1] != selectedTileTwo[1]){
            boolean isMatch = board.matchRemove(selectedTileOne[0],selectedTileOne[1],selectedTileTwo[0],selectedTileTwo[1]);
            if(isMatch){
                currentComboCount++;
                if(currentComboCount>longestComboCount){
                    longestComboCount = currentComboCount;
                }
                setIsGameOver();
            }else {
                currentComboCount = 0;
                totalCombo++;
            }
        }
    }
}
