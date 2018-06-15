package krobis.model;

import krobis.controller.GameController;
import krobis.model.playpanelrender.PlayPanelRenderers;
import krobis.model.state.GameState;
import krobis.view.GuiHandler;

import java.awt.*;
import java.util.Stack;

public class ModelManager {

	private GameState stateIntro = this.initIntroState();

	private GameController gameController;

	private Stack<GameState> stateStack;

	public ModelManager(GameController gameController) {
		this.gameController = gameController;
		this.stateStack = new Stack<GameState>();
		GameState intro = this.stateIntro;
		this.addState(intro);
	}

	private GameState initIntroState() {
		// lines, fonts
		String[] titleLines = new String[] {
			"Welcome to Blacksmith's Apprentice!",
				"Press a button to the left to begin!"
		};
		Font[] titleFonts = new Font[] {
			new Font("Garamond", Font.BOLD, 64),
				new Font("Garamond", Font.BOLD, 56)
		};
		//width of GuiHandler ; start at y=100
		int width = GuiHandler.WIDTH_PLAYPANEL;
		int firstYCoord = 100;
		// renderer has this
		PlayPanelRenderers.Renderable[] renderers = new PlayPanelRenderers.Renderable[]{
				new PlayPanelRenderers.CenterLines(titleLines, titleFonts, width, firstYCoord)
		};

		return new GameState(this)
				.setName("Intro")
				.setRenderers(renderers)
				.setOnButtonNew(() -> System.out.println("do something when 'new' is pressed"));
	}


	/**
	 * To process input text appropriately for this phase of the game
	 *
	 * @param msg What the user typed
	 */
	public void textIn(String msg) {
		this.stateStack.peek().textIn(msg);
	}

	public void onButtonPress(String actionCommand) {
		this.stateStack.peek().onButtonPress(actionCommand);
	}

	public void paintPlayPanel(Graphics2D g) {
		this.stateStack.peek().drawPlayPanel(g);
	}

	private void addState(GameState newState) {
		this.stateStack.add(newState);
		newState.onLoad();
		this.gameController.repaint();
	}


}
