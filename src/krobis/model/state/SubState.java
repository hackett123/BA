package krobis.model.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.function.Consumer;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;

public class SubState extends GameState {
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


	SubState(ModelManager mm) {
		super(mm);
	}

	/**
	 * To accept an array of Renderables, which tell this SubState what to render onto the
	 * playpanel
	 *
	 * @param renderers An array of Renderables
	 * @return this
	 */
	SubState setRenderers(PlayPanelRenderers.Renderable[] renderers) {
		this.renderers = renderers;
		return this;
	}

	/**
	 * To accept a Runnable to run when onLoad() is called upon this SubState
	 *
	 * @param onLoad The Runnable to run
	 * @return this
	 */
	SubState overrideOnLoad(Runnable onLoad) {
		this.onLoad = onLoad;
		return this;
	}

	/**
	 * To accept a Consumer<String> to accept user text input
	 *
	 * @param textIn the Consumer to run
	 * @return this
	 */
	SubState overrideTextIn(Consumer<String> textIn) {
		this.textIn = textIn;
		return this;
	}

	@Override
	protected String setName() {
		return "ANON";
	}

	@Override
	protected SubState[] setStateStructures() {
		return null;
	}


	/**
	 * To load this SubState
	 */
	@Override
	public void onLoad() {
		if (this.onLoad != null) {
			this.onLoad.run();
		}
	}

	/**
	 * To accept text from the user
	 */
	@Override
	public void textIn(String in) {
		if (this.textIn != null) {
			this.textIn.accept(in);
		}
	}

	/**
	 * To render the playpanel
	 * @param g The graphics instance
	 */
	@Override
	public void drawPlayPanel(Graphics2D g) {
		if (this.renderers != null && this.renderers.length != 0) {
			for (PlayPanelRenderers.Renderable renderable : this.renderers) {
				renderable.render(g);
			}
		}
	}


}
