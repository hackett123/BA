package krobis.controller;

import krobis.main.Father;
import krobis.model.ModelManager;
import krobis.view.GuiHandler;

import javax.swing.*;

public class GameController {
  
    private static GameController instance = null;

    private Father father;
    private ModelManager modelManager;
    private GuiHandler guiHandler;

    private GameController(Father father) {
        this.father = father;
    }
    
    public void init() {
      this.modelManager = new ModelManager(this);
      SwingUtilities.invokeLater(this.guiHandler = new GuiHandler(this));
    }
    
    public static GameController getInstance(Father father) {
      if (instance == null) {
        instance = new GameController(father);
      }
      return instance;
    }

    public void startGame() {

    }

}
