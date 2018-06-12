package krobis.model.playpanelrender;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D; 

public class PlayPanelRenderers {
  
  public static interface Renderable {
    public void render(Graphics2D g, String[] lines);
  }

 
  public static class DrawText implements Renderable {
    
    private int[] xCoords;
    private int[] yCoords;
    
    public DrawText(int[] xCoords, int[] yCoords) {
      this.xCoords = xCoords;
      this.yCoords = yCoords;
    } 

    @Override
    public void render(Graphics2D g, String[] lines) {
      throw new UnsupportedOperationException("Unimplemented!");
    }
  }
  
  
  
  public static class CenterText implements Renderable {
    
    private int width; 
    
    private int[] yCoords;
    
    private Font[] fonts = null;
    
    public CenterText(int width, int[] yCoords, Font[] fonts) { 
      if (yCoords.length != fonts.length) {
        throw new IllegalArgumentException("Input arrays must have the same lengths!");
      }
      this.width = width;
      this.yCoords = yCoords;
      this.fonts = fonts;
    }
    

    public CenterText(int width, int[] yCoords) { 
      this.width = width;
      this.yCoords = yCoords;
    }


    @Override
    public void render(Graphics2D g, String[] lines) {
      if (lines.length != this.yCoords.length) {
        throw new IllegalArgumentException("Input arrays must have the same lengths!");
      }
      
      if (this.fonts == null) {
        this.renderWithoutFonts(g, lines);
      } else {
        this.renderWithFonts(g, lines);
      }
    } 
    

    /**
     * To center each line of text at its relative y coordinate, based on the used font,
     * onto a graphics2d instance
     * @param lines The text to render, one String per line
     * @param yCoords The y-coordinates of each line of text
     * @param width The width of the object onto which g renders
     * @param g 
     */
    public void renderWithoutFonts(Graphics2D g, String[] lines) { 
      // center at half the width
      int centerAbout = width / 2; 
      // get the current font
      Font font = g.getFont();
      
      // for each line...
      for (int i = 0; i < lines.length; i++) {
        // get the line (as String and char[]), y coord, length of the string, and 
        // set current x coord to the center
        String thisLine = lines[i];
        char[] chars = thisLine.toCharArray();
        int thisCoord = yCoords[i];
        int thisLength = thisLine.length();
        int xCoord = centerAbout; 
        
        // get the current FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // determine the space needed for the first half of the line and deduct it 
        // from xCoord
        int space = metrics.charsWidth(chars, 0, thisLength / 2);
        xCoord -= space;

        g.drawString(thisLine, xCoord, thisCoord);
      }
    }
    
    /**
     * To center each line of text at its relative y coordinate, based on the used font,
     * onto a graphics2d instance
     * @param lines The text to render, one String per line
     * @param yCoords The y-coordinates of each line of text
     * @param width The width of the object onto which g renders
     * @param fonts The font to use for each line of text
     * @param g 
     */
    public void renderWithFonts(Graphics2D g, String[] lines) { 
      
      // center at half the width
      int centerAbout = width / 2; 
      
      // for each line...
      for (int i = 0; i < lines.length; i++) {
        // get the line (as String and char[]), y coord, length of the string, and 
        // set current x coord to the center
        String thisLine = lines[i];
        char[] chars = thisLine.toCharArray();
        int thisCoord = yCoords[i];
        int thisLength = thisLine.length();
        int xCoord = centerAbout; 
        
        // get and set the current font
        Font font = fonts[i];
        g.setFont(font);
        
        // get the current FontMetrics
        FontMetrics metrics = g.getFontMetrics(font);
        // determine the space needed for the first half of the line and deduct it 
        // from xCoord
        int space = metrics.charsWidth(chars, 0, thisLength / 2);
        xCoord -= space;

        g.drawString(thisLine, xCoord, thisCoord);
      }
    }

  }
  
}
