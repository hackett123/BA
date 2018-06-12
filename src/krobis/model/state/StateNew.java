package krobis.model.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;
import krobis.view.GuiHandler;

public class StateNew extends GameState {

  public StateNew(ModelManager mm) {
    super(mm); 
  }

  @Override
  protected String setName() {
    return "New";
  }

  @Override
  protected GameState[] setStateStructures() { 
    
    // title renderer
    int[] yCoordsTitle = new int[] { 200, 250 };
    Font[] fontsTitle = new Font[] {
        new Font("Impact", Font.BOLD, 40), 
        new Font("Comic Sans MS", Font.PLAIN, 32)
    };
    PlayPanelRenderers.Renderable rendererTitle = 
        new PlayPanelRenderers.CenterText(
            GuiHandler.WIDTH_PLAYPANEL, yCoordsTitle, fontsTitle);
    
    // Title lines
    String[] linesTitle = new String[] {
        "This is rough", "but it works"
    };
    
    return new GameState[] {
        new SubState(mm) 
          .setColor(Color.BLACK)
          .setRenderer(rendererTitle)
          .setLines(linesTitle)
    };
  }
  
  @Override
  public void drawPlayPanel(Graphics2D g) {
    this.stateQueue.peek().drawPlayPanel(g);
  }

}
