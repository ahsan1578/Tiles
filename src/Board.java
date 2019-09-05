/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package Tiles;

import java.util.LinkedList;
import java.util.Random;

public class Board {
    private int rows;
    private int cols;
    private Items [] items;
    private Tile [][] board;

    /**
     * Board is a 2D array of Tile objects
     * @param rows number or rows in the board
     * @param cols number of columns in the board
     *The GameController initializes the board with the number of rows and columns
     */
    public Board(int rows, int cols){
        this.rows = rows;
        this.cols = cols;
        items = Items.values();

        board = new Tile[rows][cols];
        for(int i = 0; i< rows; i++){
            for(int j = 0; j<cols; j++){
                board[i][j] = new Tile();
            }
        }
    }


    /**
     * Reset the board with new tiles.
     * Each tile is cleared first with the clearTile() method from Tile class
     * The board is then refilled using the fillBoard() method of this class
     */
    public void resetBoard(){
        for(int i = 0; i< rows; i++){
            for(int j = 0; j<cols; j++){
               board[i][j].clearTile();
            }
        }

        fillBoard(rows, cols, items, 1);
        fillBoard(rows, cols, items, 2);
        fillBoard(rows, cols, items, 3);
    }


    /**
     * @param rows number of rows in the board
     * @param cols number of columns in the board
     * @param items array of items in each tile
     * @param numItems number of minimum items to be inserted in each tile every time the method is called
     *
     * This is a very important method that makes sure that each tile has at least 3 items, no item repetition and each item has
     *                 at least one pair in some other tile.
     *
     * It goes through each tile in board (which is a tile array), checks if the tile minimum number of items.
     *                 If not, a random item is picked. If the tile doesn't have that item, the item is inserted
     *                 in the tile. As long as the tile already has the item, the loop keeps picking a random item.
     *                 Then another tile is picked to insert the pair of the item. The loop keeps picking
     *                 another random tile until it finds a tile that doesn't already have the item.
     */
    private void fillBoard(int rows, int cols, Items [] items, int numItems){
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                if(board[i][j].numTotalItems()<numItems){
                    boolean isFilled = false;
                    while (!isFilled){
                        int itemIndex = new Random().nextInt(11);
                        Items randomItem = items[itemIndex];
                        if(!board[i][j].hasItem(randomItem)){
                            board[i][j].insertItems(randomItem);
                            boolean hasCouple = false;
                            while (!hasCouple){
                                int rowIndex = new Random().nextInt(rows);
                                int colIndex = new Random().nextInt(cols);
                                if(!board[rowIndex][colIndex].hasItem(randomItem)){
                                    board[rowIndex][colIndex].insertItems(randomItem);
                                    hasCouple = true;
                                }
                            }
                            isFilled = true;
                        }
                    }
                }
            }
        }
    }

    /**
     * @return the board (Tile array)
     */
    public Tile[][] getBoard(){
        return board;
    }


    /**
     * The method checks if two tiles (named first tile and second tile) has any item in common.
     * Uses isMatch() methos from tile class
     * The common items are removed
     * @param rowFirstTile row index of the first tile
     * @param colFirstTile column index of the first tile
     * @param rowSecondTile row index of the second tile
     * @param colSecondTile column index of the second tile
     * @return true if any item was removed, false otherwise
     */
    public boolean matchRemove(int rowFirstTile, int colFirstTile, int rowSecondTile, int colSecondTile){
        boolean isRemoved = false;
        LinkedList<Items> items = board[rowFirstTile][colFirstTile].isMatch(board[rowSecondTile][colSecondTile]);
        if(items.size()>0){
            for(Items i: items){
                board[rowFirstTile][colFirstTile].removeItem(i);
                board[rowSecondTile][colSecondTile].removeItem(i);
                isRemoved = true;
            }
        }
        return isRemoved;
    }


    /**
     * @return total number of items in a board at any given time
     * uses the numTotalItems() methos from Tile class to check each tile
     */
    public int getNumTotalItems(){
        int count = 0;
        for(int i =0 ; i<rows; i++){
            for(int j = 0; j<cols; j++){
                count += board[i][j].numTotalItems();
            }
        }
        return count;
    }

}
