package krobis.controller;

import krobis.main.Father;
import krobis.model.ModelManager;
import krobis.view.GuiHandler;

import javax.swing.*;

public class GameController {

    private Father father;
    private ModelManager modelManager;
    private GuiHandler guiHandler;

    public GameController(Father father) {
        this.father = father;
        this.modelManager = new ModelManager(this);
        SwingUtilities.invokeLater(this.guiHandler = new GuiHandler(this));
    }

    public void startGame() {

    }

}
