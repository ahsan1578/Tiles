/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */
package Tiles;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class TileMaker extends Canvas {

    private GraphicsContext gc;

    /**
     * TileMaker is a child of Canvas class that will be used to make custom canvas of each each tile
     */
    TileMaker(){
        this.setHeight(100);
        this.setWidth(100);
        gc = this.getGraphicsContext2D();
    }

    /**
     * This method draws one tile. It gets the information about the tile from the parameters
     * Then it draws shapes specific to the items of the tile
     * @param board the board of the game
     * @param row row index of the tile
     * @param col column index of the tile
     * @param drawSelector if a selector should be drawn. Selector is an indicator that the tile is current selected tile
     */
    public void drawTile(Board board, int row, int col, boolean drawSelector){
        gc.setFill(Color.rgb(225, 225, 235));
        gc.fillRect(0,0,100,100);
        gc.setStroke(Color.BLACK);
        gc.strokeRect(0,0,getWidth(),getHeight());
        Tile tile = board.getBoard()[row][col];
        double x = getWidth();
        double y = getHeight();
        for(Items items: tile.getItems()){
            switch (items){
                case PLUS:
                    gc.setStroke(Color.MEDIUMAQUAMARINE);
                    gc.strokeLine(0, y/2, x, y/2);
                    gc.strokeLine(x/2,0,x/2,y);
                    break;
                case CROSS:
                    gc.setStroke(Color.PALEVIOLETRED);
                    gc.strokeLine(0,0,x,y);
                    gc.strokeLine(0,y,x,0);
                    break;
                case FOUR_RECTANGLE:
                    gc.setStroke(Color.MEDIUMSLATEBLUE);
                    gc.setFill(Color.MEDIUMSLATEBLUE);
                    gc.fillRect(10,10,15,15);
                    gc.fillRect(75,10,15,15);
                    gc.fillRect(10,75,15,15);
                    gc.fillRect(75,75,15,15);
                    break;
                case RECTANGLE:
                    gc.setStroke(Color.INDIANRED);
                    gc.strokeRect(10,10,80,80);
                    break;
                case CIRCLE:
                    gc.setStroke(Color.INDIGO);
                    gc.strokeOval(32,32,35.355,35.355);
                    break;
                case SMALL_CIR:
                    gc.setStroke(Color.DARKMAGENTA);
                    gc.setFill(Color.DARKMAGENTA);
                    gc.fillOval(40,40,20,20);
                    break;
                case TILTED_RECTANGLE:
                    gc.setStroke(Color.DARKCYAN);
                    gc.strokeLine(10,50,50,10);
                    gc.strokeLine(10,50,50,90);
                    gc.strokeLine(50,10,90,50);
                    gc.strokeLine(90,50,50,90);
                    break;
                case BIG_FOUR_REC:
                    gc.setStroke(Color.PURPLE);
                    gc.setFill(Color.PURPLE);
                    gc.fillRect(0,0,10,10);
                    gc.fillRect(90,0,10,10);
                    gc.fillRect(0,90,10,10);
                    gc.fillRect(90,90,10,10);
                    break;
                case CIRCLE_BLUE:
                    gc.setStroke(Color.CORNFLOWERBLUE);
                    gc.strokeOval(25,25,49.498,49.498);
                    break;
                case RECTANGLE_BLUE:
                    gc.setStroke(Color.DARKSLATEGRAY);
                    gc.strokeRect(25,25,50,50);
                    break;
                case FOUR_TRIANGLE:
                    gc.setStroke(Color.DARKVIOLET);
                    gc.setFill(Color.DARKVIOLET);
                    double [] x1 = {40,60,50};
                    double [] y1 = {0,0,10};
                    double [] x2 = {0,0,10};
                    double [] y2 = {40,60,50};
                    double [] x3 = {40,60,50};
                    double [] y3 = {100,100,90};
                    double [] x4 = {100,100,90};
                    double [] y4 = {40,60,50};
                    gc.fillPolygon(x1,y1,3);
                    gc.fillPolygon(x2,y2,3);
                    gc.fillPolygon(x3,y3,3);
                    gc.fillPolygon(x4,y4,3);
                    break;
            }
        }

        if(drawSelector){
            gc.setFill(Color.rgb(120, 9, 148,0.15));
            gc.fillRect(0,0,this.getWidth(),this.getHeight());
        }
    }
}
