/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package Tiles;

import java.util.LinkedList;

public class Tile {
    private LinkedList<Items> items;

    /**
     * Tile is basically a linked list od items
     */
    public Tile(){
        items = new LinkedList<>();
    }


    /**
     * Insert an item in the tile
     * @param item item to the inserted
     */
    public void insertItems(Items item){
        items.add(item);
    }

    /**
     * This method clears all the items from a tile
     */
    public void clearTile(){
        items.clear();
    }

    /**
     * Removes a specific item from the tile
     * @param item to be removed
     */
    public void removeItem(Items item){
        items.remove(item);
    }

    /**
     * Get the list of items
     * @return the linkedlist of items
     */
    public LinkedList<Items> getItems(){
        return items;
    }

    /**
     * Get the total number of items in the tile
     * @return the number of items in the tile
     */
    public int numTotalItems(){
        return items.size();
    }

    /**
     * Checks if the tile has the specific item
     * @param item the item to be checked
     * @return true if the tile has the item, false otherwise
     */
    public boolean hasItem(Items item){
        if(items.contains(item)){
            return true;
        }else {
            return false;
        }
    }

    /**
     * Checks the matched items in tile with another tile
     * @param t the tile to be checked with
     * @return the list of items that are matched
     */
    public LinkedList<Items> isMatch(Tile t){
        LinkedList<Items> items = new LinkedList<>();
        for(Items i: t.getItems()){
            if(this.hasItem(i)){
                items.add(i);
            }
        }
        return items;
    }
}
