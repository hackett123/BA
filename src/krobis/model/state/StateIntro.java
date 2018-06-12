package krobis.model.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenders;
import krobis.view.GuiHandler;

public class StateIntro extends GameState {

  public StateIntro(ModelManager mm) {
    super(mm); 
  }

  @Override
  protected String setName() {
    return "Intro";
  }

  @Override
  protected GameState[] setStateStructures() {
    return new GameState[] {
        
    };
  }
  
  @Override
  protected void onLoad() {
    
  }
  
  @Override
  public void drawPlayPanel(Graphics2D g) {
    g.setColor(Color.DARK_GRAY); 
    
    PlayPanelRenders.CenterText.render(
        new String[] {
            "Welcome to Blacksmith's Apprentice!", "Click a button on the left to begin!"
        }, 
        new int[] {
            150, 200
        }, GuiHandler.WIDTH_PLAYPANEL, 
        new Font[] {
            new Font("Garamond", Font.BOLD, 64),
            new Font("Garamond", Font.BOLD, 48)
        }, g);
  }

}
