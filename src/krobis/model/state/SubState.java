package krobis.model.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.util.function.Consumer;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;

public class SubState extends GameState {
  
  /**
   * drawPlayPanel() functionality
   */
  private PlayPanelRenderers.Renderable renderer;
  
  private String[] lines;
  
  private Color color;
  
  /**
   * textIn() functionality
   */
  private Consumer<String> textIn;
  
  /**
   * onLoad() functionality
   */
  private Runnable onLoad;
  

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
  
  public SubState overrideOnLoad(Runnable onLoad) {
    this.onLoad = onLoad;
    return this;
  }
  
  public SubState overrideTextIn(Consumer<String> textIn) {
    this.textIn = textIn;
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
  public void onLoad() {
    if (this.onLoad == null) {
      throw new IllegalArgumentException("Cannot call onLoad() without calling "
          + "overrideOnLoad() on this SubState!");
    } else { 
      this.onLoad.run();
    }
  }
  
  @Override
  public void textIn(String in) {
    if (this.textIn == null) {
      throw new IllegalArgumentException("Cannot call textIn() without calling "
          + "overrideTextIn() on this SubState!");
    } else {
      this.textIn.accept(in);
    }
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
