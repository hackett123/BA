package krobis.model;

import java.awt.Graphics2D;

import krobis.controller.GameController;

public class ModelManager {

    private GameController gameController;

    public ModelManager(GameController gameController) {
        this.gameController = gameController;
    }

    /**
     * To process input text appropriately for this phase of the game
     * @param msg
     */
    public void textIn(String msg) {
      
    }

    public void onButtonPress(String actionCommand) {
      switch (actionCommand) {
      case "Home":
        
        break;
      default:
        throw new IllegalArgumentException("Invalid button action command: " + actionCommand);
      }
    }

    public void paintPlayPanel(Graphics2D g) {
      
    }
    
    

}
