/*
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
  
  */
/**
   * To initialize all necessary components for the first SubState of this GameState
   * @return The SubState
   *//*

  private SubState initSubState1() {
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

    return new SubState(this.mm)
			.setRenderers1(renderers);
  }

  @Override
  protected SubState[] setSubStateStructures() {
    return new SubState[] {
        this.initSubState1()
    };
  }

	@Override
	protected PlayPanelRenderers.Renderable[] setRenderers() {
		return null;
	}


}
*/
