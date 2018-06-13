package krobis.model.state;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Shape;
import java.util.function.Consumer;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;

public class SubState extends GameState {
  
  /**
   * (following from the return statement in initSubstate1() in StateNew...)
   * Note the fields below. There are, as of now, a few: 
   *  - renderers, an array of Renderables
   *  - an array of Strings called lines
   *  - a Color
   *  - an array of Shapes
   *  - a Consumer, of type String
   *  - a Runnable
   *  All, some, or none of these may be initialized by the builder methods below to give 
   *  them function. Read through the fields and their Javadocs all the way down.
   */
  
  /**
   * drawPlayPanel() functionality
   */
  private PlayPanelRenderers.Renderable[] renderers;
  
  /**
   * What text to display to the user on the playpanel
   */
  private String[] lines;
  
  /**
   * Ignore this one. Soon to change (maybe)
   */
  private Color color;
  
  /**
   * Any Shapes to render onto the playpanel
   */
  private Shape[] shapes;
  
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
  
  /*
   * Each of those fields is initially null when this SubState is called. They are given 
   * their values through the following methods (called builder methods). Each method accepts
   * a parameter that matches one of the fields above, and each method returns this object. 
   * This is so the methods may be chained (as they were in the return call in StateNew).
   * Read what each does. 
   */
  
  /**
   * To accept an array of Renderables, which tell this SubState what to render onto the 
   * playpanel
   * @param renderer
   * @return
   */
  public SubState setRenderers(PlayPanelRenderers.Renderable[] renderer) {
    this.renderers = renderer;
    return this;
  }
  
  /**
   * To accept this SubState's color
   * @param color
   * @return
   */
  public SubState setColor(Color color) {
    this.color = color;
    return this;
  }
  
  /**
   * To accept the text to render onto the playpanel
   * @param lines
   * @return
   */
  public SubState setLines(String[] lines) {
    this.lines = lines;
    return this;
  }
  
  /**
   * To accept a Runnable to run when onLoad() is called upon this SubState
   * @param onLoad
   * @return
   */
  public SubState overrideOnLoad(Runnable onLoad) {
    this.onLoad = onLoad;
    return this;
  }
  
  /**
   * To accept a Consumer<String> to accept user text input 
   * @param textIn
   * @return
   */
  public SubState overrideTextIn(Consumer<String> textIn) {
    this.textIn = textIn; 
    return this;
  }  
  
  /**
   * To accept the Shapes to be rendered onto the playpanel
   * @param shapes
   * @return
   */
  public SubState setShapes(Shape[] shapes) {
    this.shapes = shapes;
    return this;
  }
  
  /*
   * Each builder method is optional. Based on the needs for any particular SubState for 
   * any GameState, any or all of the methods may be called. 
   * Skip below to see how some of the input is used. 
   */

  @Override
  protected String setName() {
    return "ANON";
  }

  @Override
  protected GameState[] setStateStructures() {
    return null;
  }

  /*
   * onLoad() is called by the modelmanager (this.mm) when a new GameState is added
   * to the state stack, AND when an old SubState is popped from that GameState's state queue
   * (the SubState underneath the popped one, which is next to be shown, gets its onLoad() run).
   * For any given SubState, the functionality that needs to run in this method is given as
   * a Runnable (of signature void -> void, which matches onLoad's signature). The Runnable
   * is then run during onLoad. 
   * Note: in StateNew, this builder method, when called in the return statement, takes 
   * a lambda of no arguments and no return statement. That signature matches the 
   * signature of both onLoad() and Runnable.run().
   */
  
  /**
   * To load this SubState
   */
  @Override
  public void onLoad() {
    if (this.onLoad != null) {
      this.onLoad.run();
    } 
  }
  
  /*
   * textIn works the same way: a Consumer<String>, of signature (String -> void), accepts
   * text from the user and processes it however the Consumer is told to.
   * Note: textIn in StateNew takes a lambda of one String argument and no return. This
   * matches the signature of both textIn() and Consumer.accept().
   */
  
  /**
   * To accept text from the user
   */
  @Override 
  public void textIn(String in) {
    if (this.textIn != null) {
      this.textIn.accept(in);
    } 
  }
  
  /*
   * Drawing is fun. Again, ignore the color- it needs to change. 
   * The rest of the method says: if there are any renderers to render, 
   * iterate through them and render them. Jump to the PlayPanelRenderers java file. 
   */
  
  /**
   * To render the playpanel
   */
  @Override
  public void drawPlayPanel(Graphics2D g) {
    if (this.color != null) {
      g.setColor(color);
    }
    
    if (this.renderers != null && this.renderers.length != 0) {
      for (PlayPanelRenderers.Renderable renderable : this.renderers) {
        renderable.render(g);
      }
    }
  }
  
  

}
