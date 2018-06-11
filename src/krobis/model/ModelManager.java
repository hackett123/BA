package krobis.model;

import java.awt.Graphics2D;
import java.util.Stack;

import krobis.controller.GameController;
import krobis.model.state.GameState;
import krobis.model.state.StateIntro;

public class ModelManager {

    private GameController gameController;

    private Stack<GameState> stateStack;
    
    public ModelManager(GameController gameController) {
        this.gameController = gameController;
        this.stateStack = new Stack<GameState>();
        
        this.stateStack.add(new StateIntro(this));
    }

    /**
     * To process input text appropriately for this phase of the game
     * @param msg
     */
    public void textIn(String msg) {
      
    }

    public void onButtonPress(String actionCommand) {
      this.stateStack.peek().onButtonPress(actionCommand);
    }

    public void paintPlayPanel(Graphics2D g) {
      this.stateStack.peek().drawPlayPanel(g);
    }
    
    

}
