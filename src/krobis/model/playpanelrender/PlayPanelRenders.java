package krobis.model.playpanelrender;

import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics2D; 

public class PlayPanelRenders {
 
  public static class DrawText {
    
    public static void render(String[] lines, int[] xCoords, int[] yCoords, Graphics2D g) {
      throw new UnsupportedOperationException("Unimplemented!");
    }
  }
  
  public static class CenterText {
    
    /**
     * To center each line of text at its relative y coordinate, based on the used font,
     * onto a graphics2d instance
     * @param lines The text to render, one String per line
     * @param yCoords The y-coordinates of each line of text
     * @param width The width of the object onto which g renders
     * @param g 
     */
    public static void render(String[] lines, int[] yCoords, int width, Graphics2D g) {
      if (lines.length != yCoords.length) {
        throw new IllegalArgumentException("Input arrays must be the same length!");
      }
      
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
    public static void render(String[] lines, int[] yCoords, int width, 
        Font[] fonts, Graphics2D g) {
      if ((lines.length != yCoords.length) || (lines.length != fonts.length)) {
        throw new IllegalArgumentException("Input arrays must be the same length!");
      }
      
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
