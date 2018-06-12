package krobis.model.state;

import java.awt.Color;
import java.awt.Graphics2D;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;

public class SubState extends GameState {
  
  private PlayPanelRenderers.Renderable renderer;
  
  private String[] lines;
  
  private Color color;

  public SubState(ModelManager mm) {
    super(mm); 
  } 
  
  public SubState setRenderer(PlayPanelRenderers.Renderable renderer) {
    this.renderer = renderer;
    return this;
  }
  
  public SubState setColor(Color color) {
    this.color = color;
    return this;
  }
  
  public SubState setLines(String[] lines) {
    this.lines = lines;
    return this;
  }
  
  @Override
  protected String setName() {
    return "ANON";
  }

  @Override
  protected GameState[] setStateStructures() {
    return null;
  }
  
  @Override
  public void drawPlayPanel(Graphics2D g) {
    if (this.color != null) {
      g.setColor(color);
    }
    
    if (this.renderer != null) {
      this.renderer.render(g, lines);
    }
  }

}
