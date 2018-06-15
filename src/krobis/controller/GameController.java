package krobis.controller;

import java.awt.Graphics2D;

import javax.swing.SwingUtilities;

import krobis.model.ModelManager;
import krobis.view.GuiHandler;

public class GameController {

	private static GameController instance = null;

	private ModelManager modelManager;
	private GuiHandler guiHandler;

	private GameController() {

	}

	public void init() {
		this.guiHandler = new GuiHandler(this);
		SwingUtilities.invokeLater(() -> guiHandler.initGUI());
		this.modelManager = new ModelManager(this);
	}

	public static GameController getInstance() {
		if (instance == null) {
			instance = new GameController();
		}
		return instance;
	}

	public void startGame() {

	}

	/**
	 * To delegate input text to the modelmanager
	 *
	 * @param msg
	 */
	public void textIn(String msg) {
		this.modelManager.textIn(msg);
	}

	/**
	 * To delegate a button press to the modelmanager
	 *
	 * @param actionCommand
	 */
	public void onButtonPress(String actionCommand) {
		this.modelManager.onButtonPress(actionCommand);
	}

	/**
	 * To delegate drawing the playpanel to the modelmanager
	 *
	 * @param g
	 */
	public void paintPlayPanel(Graphics2D g) {
		this.modelManager.paintPlayPanel(g);
	}

	/**
	 * To delegate repainting the playpanel to the guihandler
	 */
	public void repaint() {
		this.guiHandler.repaint();
	}

}
