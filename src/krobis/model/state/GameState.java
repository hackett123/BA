package krobis.model.state;

import java.awt.Graphics2D;
import java.util.LinkedList;
import java.util.Queue;

import krobis.model.ModelManager;
import krobis.view.GuiHandler; 

/**
 * An abstract frame for game states.<br>
 * Each state needs to handle all types of user input.<br>
 * Each explicit GameState subclass overrides all useful methods to 
 * provide functionality. Additionally, each subclass may have states of its
 * own, which are stored in the stateQueue field. Each of these substates is 
 * initialized as an anonymous AnonState instance, and all necessary fields
 * are anonymously overridden as appropriate.
 */
public abstract class GameState {
  
  /**
   * The user-friendly name of this state (for toString)
   */
  protected String stateName;
  
  /**
   * ModelManager reference
   */
  protected ModelManager mm;
  
  /**
   * Queue of states
   */
  protected Queue<GameState> stateQueue;
  
  
  public GameState(ModelManager mm) { 
    this.mm = mm;
    this.stateName = this.setName();
    this.stateQueue = new LinkedList<GameState>();
    this.initStateStructures();
  }
  

  /**
   * To set the user-friendly name of this state
   */
  protected abstract String setName();
  
  /**
   * To initalize an array of anonymous inner states
   * @return
   */
  protected abstract GameState[] setStateStructures();
  
  
  private void initStateStructures() {
    GameState[] innerStates = this.setStateStructures();
    
    if (innerStates != null) {
      for (GameState gs : innerStates) {
        this.stateQueue.add(gs);
      }
    }
    
  }


  public void onButtonPress(String actionCommand) { 
    switch (actionCommand) {
    case GuiHandler.BUTTON_NAME_HOME: 
      this.onButtonHome();
      break;
    case GuiHandler.BUTTON_NAME_OPTIONS:
      this.onButtonOptions();
      break;
    case GuiHandler.BUTTON_NAME_NEW: 
      this.onButtonNew();
      break; 
    case GuiHandler.BUTTON_NAME_LOAD:
      this.onButtonLoad();
      break;
    case GuiHandler.BUTTON_NAME_SETTINGS:
      this.onButtonSettings();
      break;
    case GuiHandler.BUTTON_NAME_CREDITS:
      this.onButtonCredits();
      break;
    default: 
      throw new IllegalArgumentException("Illegal button action event: " + actionCommand);
    }
  }

  // one for each button

  protected void onButtonHome() {
    // tbd
  }

  protected void onButtonOptions() {
    // tbd
  } 
 
  public void drawPlayPanel(Graphics2D g) {
    this.stateQueue.peek().drawPlayPanel(g);
  }
   
  public void onLoad() {
    this.stateQueue.peek().onLoad();
  }

  public void textIn(String msg) {
    this.stateQueue.peek().textIn(msg);
  }
  
  protected void onButtonNew() {
    
  }

  protected void onButtonLoad() {
    
  }
  
  protected void onButtonSettings() {
    
  }
  
  protected void onButtonCredits() {
    
  }  
  
} 



