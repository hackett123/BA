package krobis.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;

import krobis.controller.GameController;

public class GuiHandler implements Runnable { 
  
    private static final int WIDTH = 1800;
    private static final int HEIGHT = 960;
    
    private static final Dimension DIM_CONTAINER = new Dimension(WIDTH, HEIGHT);
  
    private static final Dimension DIM_CONTAINERLEFT = new Dimension(WIDTH / 4, HEIGHT);
    private static final Dimension DIM_PANELDRAW = new Dimension(3 * WIDTH / 4, HEIGHT);
    private static final Dimension DIM_CONTAINERCHOICE = new Dimension(WIDTH / 4, HEIGHT / 2);
    private static final Dimension DIM_CONTAINERMENU = new Dimension(WIDTH / 4, HEIGHT / 8);
    private static final Dimension DIM_TEXTIN = new Dimension(WIDTH / 4, HEIGHT / 8);
    private static final Dimension DIM_PANELSTATUS = new Dimension(WIDTH / 4, HEIGHT / 4);

    private static final Dimension DIM_MENUBUTTON = new Dimension(100, 50);
    
    private static final Font FONT_TEXTIN = new Font("garamond", Font.PLAIN, 32);
    
    private GameController gameController;
    
    /**
     * The JFrame that contains everything
     */
    private JFrame frame;
    
    /**
     * The nested JPanel that contains all game elements, including all 
     * functional panels and text components
     */
    private JPanel container;
    
    /**
     * The panel on the right
     */
    private JPanel panelDraw;
    
    /**
     *  The container panel on the left
     */
    private JPanel containerLeft;
    
    /**
     * The panel to show status ??
     */
    private JPanel panelStatus;
    
    /**
     * The field for the user to input text
     */
    private JTextField textIn;
    
    /**
     * The container for choice buttons
     */
    private JPanel containerChoiceButton;
    
    /**
     * The container for menu buttons
     */
    private JPanel containerMenuButton;
    
    private JButton home;
    
    private JButton save;
    
    private JButton options; 

    public GuiHandler(GameController gameController) {
        this.gameController = gameController;
    }

    @Override
    public void run() {
      // to do: 
      this.initFrame();
      
      this.initContainers();
      
      this.initPanels();
      
      this.initButtons();
      
      this.initTextIn(); 
      
      this.assemble();
      
    }

    private void assemble() {
      // menu buttons 
      
      this.containerMenuButton.setLayout(new GridLayout(1, 2, 20, 20));
      this.containerMenuButton.add(this.home);
      this.containerMenuButton.add(this.options);
      
      // leftContainer
      this.containerLeft.setLayout(new BoxLayout(this.containerLeft, BoxLayout.PAGE_AXIS));
      this.containerLeft.add(this.panelStatus);
      this.containerLeft.add(this.textIn);
      this.containerLeft.add(this.containerChoiceButton);
      this.containerLeft.add(this.containerMenuButton);
      
      // container
      BoxLayout bl = new BoxLayout(this.container, BoxLayout.LINE_AXIS);
      this.container.setLayout(bl); 
      
      this.container.add(containerLeft);
      this.container.add(panelDraw);
      
      // pack the frame
      this.frame.add(this.container);
      
      this.frame.pack();
      this.frame.setResizable(false);
      this.frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      this.frame.setVisible(true);
    }

    private void initTextIn() {
      this.textIn = new JTextField();
      this.textIn.setPreferredSize(DIM_TEXTIN);
      this.textIn.setBackground(Color.LIGHT_GRAY);
      this.textIn.setFont(FONT_TEXTIN);
      this.textIn.setBorder(null);
    }

    private void initButtons() {
      this.home = new JButton("Home"); 
      this.options = new JButton("Options");
      
      JButton[] buttons = new JButton[] {
          home, options
      };
      
      for (JButton button : buttons) {
        button.setPreferredSize(DIM_MENUBUTTON);
      }
      
      
    }

    private void initPanels() {
      // panelDraw
      this.panelDraw = new JPanel();
      this.panelDraw.setPreferredSize(DIM_PANELDRAW);
      this.panelDraw.setBackground(Color.YELLOW);
      
      // panelStatus
      this.panelStatus = new JPanel();
      this.panelStatus.setPreferredSize(DIM_PANELSTATUS);
      this.panelStatus.setBackground(Color.WHITE);
    }

    private void initContainers() {
      // container first
      this.container = new JPanel();
      this.container.setPreferredSize(DIM_CONTAINER);
      this.container.setBackground(Color.GRAY);
      
      // containerLeft
      this.containerLeft = new JPanel();
      this.containerLeft.setPreferredSize(DIM_CONTAINERLEFT);
      this.containerLeft.setBackground(Color.RED);
      
      // containerChoiceButton
      this.containerChoiceButton = new JPanel();
      this.containerChoiceButton.setPreferredSize(DIM_CONTAINERCHOICE);
      this.containerChoiceButton.setBackground(Color.DARK_GRAY);
      
      // containerMenuButton
      this.containerMenuButton = new JPanel();
      this.containerMenuButton.setPreferredSize(DIM_CONTAINERMENU);
      this.containerMenuButton.setBackground(Color.BLACK);
      
    }

    private void initFrame() {
      this.frame = new JFrame("hello gruel world");
    }
}
