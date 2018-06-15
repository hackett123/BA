package krobis.model.state;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;
import krobis.view.GuiHandler;

import java.awt.*;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Queue;
import java.util.function.Consumer;

/**
 * An abstract frame for game states.<br>
 * Each state needs to handle all types of user input.<br>
 * Each explicit GameState subclass overrides all useful methods to
 * provide functionality. Additionally, each subclass may have states of its
 * own, which are stored in the stateQueue field. Each of these substates is
 * initialized as an anonymous AnonState instance, and all necessary fields
 * are anonymously overridden as appropriate.
 */
public class GameState {

	/**
	 * The user-friendly name of this state (for toString)
	 */
	private String stateName;

	/**
	 * ModelManager reference
	 */
	private ModelManager mm;

	/**
	 * Queue of SubStates
	 */
	private Queue<GameState> stateQueue;

	/**
	 * drawPlayPanel() functionality
	 */
	private PlayPanelRenderers.Renderable[] renderers;

	/**
	 * textIn() functionality
	 */
	private Consumer<String> textIn;

	/**
	 * onLoad() functionality
	 */
	private Runnable onLoad;

	/**
	 * onButtonNew() functionality
	 */
	private Runnable onButtonNew;

	public GameState(ModelManager mm) {
		this.mm = mm;
		this.stateQueue = new LinkedList<GameState>();
	}

	// constructor setter methods, overrided in subclasses

	public GameState setName(String name) {
		this.stateName = name;
		return this;
	}

	public GameState setSubStateStructures(GameState[] substates) {
		this.stateQueue.addAll(Arrays.asList(substates));
		return this;
	}

	public GameState setRenderers(PlayPanelRenderers.Renderable[] renderers) {
		this.renderers = renderers;
		return this;
	}

	// buttons

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

	private void onButtonHome() {
		// tbd
	}

	private void onButtonOptions() {
		// tbd
	}

	protected void onButtonNew() {
		this.onButtonNew.run();
	}

	protected void onButtonLoad() {

	}

	private void onButtonSettings() {

	}

	private void onButtonCredits() {

	}

	// other methods

	public void onLoad() {
		if (this.stateQueue.peek() != null) {
			this.stateQueue.peek().onLoad();
		}
		if (this.onLoad != null) {
			this.onLoad.run();
		}
	}

	public void textIn(String msg) {
		if (this.stateQueue.peek() != null) {
			this.stateQueue.peek().textIn(msg);
		} if (this.textIn != null) {
			this.textIn.accept(msg);
		}
	}

	// overrides

	public GameState setOnButtonNew(Runnable onButtonNew) {
		this.onButtonNew = onButtonNew;
		return this;
	}

	/**
	 * To accept a Runnable to initGUI when onLoad() is called upon this SubState
	 *
	 * @param onLoad The Runnable to initGUI
	 * @return this
	 */
	public GameState setOnLoad(Runnable onLoad) {
		this.onLoad = onLoad;
		return this;
	}

	/**
	 * To accept a Consumer<String> to accept user text input
	 *
	 * @param textIn the Consumer to initGUI
	 * @return this
	 */
	public GameState setTextIn(Consumer<String> textIn) {
		this.textIn = textIn;
		return this;
	}

	/**
	 * To render the playpanel
	 * @param g Graphics instance
	 */
	public void drawPlayPanel(Graphics2D g) {
		if (this.stateQueue != null && this.stateQueue.peek() != null) {
			this.stateQueue.peek().drawPlayPanel(g);
		} else if (this.renderers != null && this.renderers.length != 0) {
			for (PlayPanelRenderers.Renderable renderable : this.renderers) {
				renderable.render(g);
			}
		}
	}

}





