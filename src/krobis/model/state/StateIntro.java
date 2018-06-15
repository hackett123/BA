/*
package krobis.model.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;
import krobis.view.GuiHandler;

public class StateIntro extends GameState {

  private Font[] DRAW_FONTS = new Font[] {
      new Font("Garamond", Font.BOLD, 64),
      new Font("Garamond", Font.BOLD, 48)
  };

  private String[] DRAW_LINES = new String[] {
      "Welcome to Blacksmith's Apprentice!", "Click a button on the left to begin!"
  };

  private int[] DRAW_YCOORDS = new int[] {
      100, 175
  };



  public StateIntro(ModelManager mm) {
    super(mm);
  }

  @Override
  protected String setName() {
    return "Intro";
  }

  @Override
  protected SubState[] setSubStateStructures() {
    return new SubState[] {

    };
  }

	@Override
	protected PlayPanelRenderers.Renderable[] setRenderers() {
		return new PlayPanelRenderers.Renderable[] {
				new PlayPanelRenderers.CenterLines(DRAW_LINES, DRAW_FONTS,
						GuiHandler.WIDTH_PLAYPANEL, DRAW_YCOORDS[0])
		};
	}

	@Override
  public void onLoad() {
    // stuff here??
  }

  @Override
  protected void onButtonNew() {
    // new game
    this.mm.addState(new StateNew(this.mm));
  }

  @Override
  protected void onButtonLoad() {
    // load game
    System.out.println("load");
  }

}
*/
