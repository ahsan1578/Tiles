/**
 * @author D M Raisul Ahsan
 * @version 1.0
 */

package Tiles;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.Optional;

/**
 * JAVAfx GUI representation of the game
 */
public class GameDisplay extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public void start(Stage stage){
        /**
         * Creates a GameController object that controls the game and obtains necessary information about the board
         */
        GameController gameController = new GameController();
        Board board = gameController.getBoard();
        int rows = gameController.getRows();
        int cols = gameController.getCols();


        /**
         * A Vbox pane that works as a scorecard of the game
         */
        VBox scoreCard = new VBox();
        scoreCard.setPadding(new Insets(50));
        scoreCard.setSpacing(20);
        scoreCard.setBackground(new Background(new BackgroundFill(Color.BLUEVIOLET, CornerRadii.EMPTY, Insets.EMPTY)));

        Text currentCombo = new Text("Current Combo " +gameController.getCurrentComboCount());
        currentCombo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        currentCombo.setFill(Color.WHITE);

        Text longestCombo = new Text("Longest Combo "+gameController.getLongestComboCount());
        longestCombo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        longestCombo.setFill(Color.WHITE);

        Text totalCombo = new Text("Number of Combos "+gameController.getTotalCombo());
        totalCombo.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        totalCombo.setFill(Color.WHITE);

        scoreCard.getChildren().add(totalCombo);
        scoreCard.getChildren().add(longestCombo);
        scoreCard.getChildren().add(currentCombo);

        Button newGameButton = new Button();
        newGameButton.setText("Start a new game");
        newGameButton.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 17px");
        scoreCard.getChildren().add(newGameButton);


        /**
         * An 2D array of TileMaker objects is made which is essentially parallel to board array
         * The board array has the information about the tiles whereas TileMaker array has the visual representation on canvas
         * GridPane object is created to show the TileMaker canvases of tiles in the scene
         */
        TileMaker [][] tileMakers = new TileMaker[rows][cols];
        GridPane tileGrid = new GridPane();
        for(int i = 0; i<rows; i++){
            for(int j = 0; j<cols; j++){
                tileMakers[i][j] = new TileMaker();
                tileGrid.add(tileMakers[i][j],j,i);
                tileMakers[i][j].drawTile(board,i,j,false);
            }
        }

        /**
         * The newGameButton resets the board for new game onclick. The button is on the Vbox score card.
         */
        newGameButton.setOnMouseClicked(event -> {
            gameController.startNewGame();
            for(int i = 0; i<rows; i++){
                for(int j = 0; j<cols; j++){
                    tileMakers[i][j] = new TileMaker();
                    tileGrid.add(tileMakers[i][j],j,i);
                    tileMakers[i][j].drawTile(board,i,j,false);
                }
            }
            currentCombo.setText("Current Combo "+gameController.getCurrentComboCount());
            longestCombo.setText("Longest Combo "+gameController.getLongestComboCount());
            totalCombo.setText("Number of Combos "+gameController.getTotalCombo());
        });

        /**
         * A dialog box is created to give the users option to start a new game or exit when the game is over
         */
        ButtonType restartButtonType = new ButtonType("Play again", ButtonBar.ButtonData.NEXT_FORWARD);
        ButtonType exitButtonType = new ButtonType("Exit", ButtonBar.ButtonData.NEXT_FORWARD);
        Dialog<ButtonType> dialog = new Dialog<>();
        dialog.initModality(Modality.WINDOW_MODAL);
        dialog.getDialogPane().getButtonTypes().addAll(restartButtonType,exitButtonType);
        Button restartButton = (Button)dialog.getDialogPane().lookupButton(restartButtonType);
        Button exitButton = (Button)dialog.getDialogPane().lookupButton(exitButtonType);
        restartButton.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 17px");
        exitButton.setStyle("-fx-background-color: #021b45; -fx-text-fill: white; -fx-font-size: 17px");


        /**
         * For any mouse click of the tileGrid grid pane, which is essentially the game board visual representation on scene,
         * the tile is found out using the x,y coordinate of mouse click.
         * Then the tile information is passed to the game controller. Using the information game controller selects tile
         * accordingly, checks match, removes matched items, keeps scores, checks if the game is over and send all the information
         * back to this class. Using the those information, this class draws the GUI accordingly after every mouse click
         */
        tileGrid.setOnMouseClicked(event -> {
            int y = (int)event.getX()/100;
            int x = (int)event.getY()/100;

            if(!gameController.getHasOneTileSelected()){
                gameController.setSelectedTileOne(x,y);
                if(gameController.getHasOneTileSelected()){
                    tileMakers[x][y].drawTile(board, x,y,true);
                }
            }else{
                gameController.setSelectedTileTwo(x,y);
                gameController.play();
                int a = gameController.getSelectedTileOne()[0];
                int b = gameController.getSelectedTileOne()[1];
                tileMakers[a][b].drawTile(board, a,b,false);
                if(board.getBoard()[x][y].numTotalItems()==0){
                    tileMakers[x][y].drawTile(board,x,y,false);
                }else {
                    tileMakers[x][y].drawTile(board,x,y,true);
                }
                currentCombo.setText("Current Combo "+gameController.getCurrentComboCount());
                longestCombo.setText("Longest Combo "+gameController.getLongestComboCount());
                totalCombo.setText("Number of Combos "+gameController.getTotalCombo());
                if(gameController.getIsGameOver()){
                    dialog.setHeaderText("Game Over");
                    dialog.setContentText("Number of Combos "+gameController.getTotalCombo()+"\n"+"Longest Combo "+gameController.getLongestComboCount());
                    Optional<ButtonType> result = dialog.showAndWait();
                    ButtonType choose = null;
                    if(result.isPresent()){
                        choose = result.get();
                    }
                    if(choose.equals(exitButtonType)){
                        stage.close();
                    }else if(choose.equals(restartButtonType)){
                        gameController.startNewGame();
                        for(int i = 0; i<rows; i++){
                            for(int j = 0; j<cols; j++){
                                tileMakers[i][j] = new TileMaker();
                                tileGrid.add(tileMakers[i][j],j,i);
                                tileMakers[i][j].drawTile(board,i,j,false);
                            }
                        }
                        currentCombo.setText("Current Combo "+gameController.getCurrentComboCount());
                        longestCombo.setText("Longest Combo "+gameController.getLongestComboCount());
                        totalCombo.setText("Number of Combos "+gameController.getTotalCombo());
                    }
                }else {
                    gameController.swapSelection();
                }
            }
        });


        /**
         * The root border pane is created on which everything is shown
         */
        BorderPane root = new BorderPane();
        root.setLeft(tileGrid);
        root.setRight(scoreCard);


        stage.setScene(new Scene(root));

        stage.show();
    }
}
