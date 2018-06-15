package krobis.model.state;

import java.awt.Color;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.Shape;
import java.awt.geom.Ellipse2D;

import krobis.model.ModelManager;
import krobis.model.playpanelrender.PlayPanelRenderers;
import krobis.view.GuiHandler;

public class StateNew extends GameState {

  StateNew(ModelManager mm) {
    super(mm); 
  }

  @Override
  protected String setName() {
    return "New";
  }
  
  /**
   * To initialize all necessary components for the first SubState of this GameState
   * @return The SubState
   */
  private SubState initSubState1() {
    /*
     * Oh dear
     * So. SubStates are a bit complex. I'll walk you through it. 
     * Basically, each SubState is initially built receiving only the modelmanager (this.mm). 
     * In order to make them useful, use the builder methods to add functionality. Jump 
     * to the return statement for this method below...
     */
    
    
    // title renderer
    int[] yCoordsTitle = new int[] { 100, 175 };
    // title fonts
    Font[] fontsTitle = new Font[] {
        new Font("Garamond", Font.BOLD, 72), 
        new Font("Garamond", Font.PLAIN, 48)
    };
    // title lines
    String[] linesTitle = new String[] {
        "Let's start a new game!", "What is your name?"
    };
    // title shapes
    Shape[] shapesTitle = new Shape[] {
        new Rectangle(100,100,100,100), new Ellipse2D.Double(150, 150, 100, 100)
    };
    // title shape fill flags
    PlayPanelRenderers.ShapeFillOption[] shapesFillFlags = 
        new PlayPanelRenderers.ShapeFillOption[] {
            PlayPanelRenderers.ShapeFillOption.OUTLINE, 
            PlayPanelRenderers.ShapeFillOption.FILLED
    };
    // title shape center flags
    PlayPanelRenderers.ShapeCenteringOption[] shapeCenterOptions = 
        new PlayPanelRenderers.ShapeCenteringOption[] {
            PlayPanelRenderers.ShapeCenteringOption.ALONG_NONE,
            PlayPanelRenderers.ShapeCenteringOption.ALONG_BOTH
        };
    
    // title renderables
    PlayPanelRenderers.Renderable[] renderers = 
        new PlayPanelRenderers.Renderable[] {
            new PlayPanelRenderers.CenterText(linesTitle,
                GuiHandler.WIDTH_PLAYPANEL, yCoordsTitle)
                .setFonts(fontsTitle),
            new PlayPanelRenderers.RenderShapes(shapesTitle)
            .setShapeFillOptions(shapesFillFlags)
            .setShapeCenteringOptions(shapeCenterOptions),
        }; 
    
    
    /*
     * ...here. Note how the (still anonymous) new SubState takes in this.mm as its only
     * constructor value. However, many other builder methods are called to give it its
     * functionality. Hop into the SubState class file to see what this shit is.
     */
    return new SubState(this.mm) 
            .setColor(Color.BLACK)
            .setRenderers(renderers)
            .setLines(linesTitle)
            .overrideOnLoad(() -> {
              System.out.println("temporary filler code here");
            })
            .overrideTextIn((text) -> {
              // eventually provide real content here
            });
  }

  @Override
  protected SubState[] setStateStructures() {
    return new SubState[] {
        this.initSubState1()
    };
  }
  


}
