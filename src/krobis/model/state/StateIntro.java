package krobis.model.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;

import krobis.model.ModelManager;

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
    g.setColor(Color.RED);
    g.setFont(new Font("garamond", Font.BOLD, 32));
    g.drawString("Here's how a state could draw on the playpanel", 150, 150);
    g.drawString("(this is done in StateIntro)", 150, 180);
  }

}
