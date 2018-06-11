package krobis.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GridLayout; 
import java.awt.event.ActionListener; 
import java.util.ArrayList; 

import javax.swing.*;

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

    public static final String BUTTON_NAME_HOME = "Home";
    public static final String BUTTON_NAME_OPTIONS = "Options";
    public static final String BUTTON_NAME_NEW = "New Game";
    public static final String BUTTON_NAME_LOAD = "Load Game";
    public static final String BUTTON_NAME_SETTINGS = "Settings"; 
    public static final String BUTTON_NAME_CREDITS = "Credits";

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

      this.displayStartupScreen();
      
    }

    private void displayStartupScreen() {

        /**
         * panelDraw setup
         */
        JLabel welcomeText, instructionsText;
        JButton newGame, loadGame, settings, credits;

        ArrayList<JLabel> gameLabels = new ArrayList<>();
        ArrayList<JButton> gameButtons = new ArrayList<>();

        welcomeText = new JLabel("Welcome to Blacksmith's Apprentice!", SwingConstants.CENTER);
        welcomeText.setFont(new Font("Courier New", Font.BOLD, 18));
        welcomeText.setForeground(Color.DARK_GRAY);

        instructionsText = new JLabel("Choose from one of the options below to begin:", SwingConstants.CENTER);
        instructionsText.setFont(new Font("Courier New", Font.ITALIC, 16));
        instructionsText.setForeground(Color.DARK_GRAY);

        gameLabels.add(welcomeText);
        gameLabels.add(instructionsText);

        newGame = new JButton(BUTTON_NAME_NEW);

        loadGame = new JButton(BUTTON_NAME_NEW);

        settings = new JButton(BUTTON_NAME_NEW);

        credits = new JButton(BUTTON_NAME_NEW);

        gameButtons.add(newGame);
        gameButtons.add(loadGame);
        gameButtons.add(settings);
        gameButtons.add(credits);

        for (JButton gameButton : gameButtons) {
            gameButton.setFont(new Font("Courier New", Font.BOLD, 16));
            gameButton.setBackground(Color.DARK_GRAY);
            gameButton.setForeground(Color.WHITE);
            gameButton.setOpaque(true);
            gameButton.setBorderPainted(true);
            gameButton.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2, true));
        }

        GridLayout grid = new GridLayout(4, 4);
        grid.setHgap(WIDTH / 10);
        grid.setVgap(HEIGHT / 10);
        panelDraw.setLayout(grid);

        panelDraw.add(welcomeText);
        panelDraw.add(instructionsText);
        panelDraw.add(newGame);
        panelDraw.add(loadGame);
        panelDraw.add(settings);
        panelDraw.add(credits);

        /**
         * panelStatus setup
         */

        JLabel title, cred, contact;
        title = new JLabel("BLACKSMITH'S APPRENTICE", SwingConstants.CENTER);
        title.setFont(new Font("Courier New", Font.BOLD, 24));
        title.setForeground(Color.WHITE);

        cred = new JLabel("Created by MHSProductions");
        cred.setFont(new Font("Courier New", Font.ITALIC, 12));
        cred.setForeground(Color.WHITE);

        contact = new JLabel("Contact Us: mhsproductions.com, mhsproductions1@gmail.com");
        contact.setFont(new Font("Courier New", Font.PLAIN, 12));
        contact.setForeground(Color.WHITE);

        GridLayout gl = new GridLayout(3, 1);
        panelStatus.setLayout(gl);

        panelStatus.add(title);
        panelStatus.add(cred);
        panelStatus.add(contact);

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
      
      this.textIn.addActionListener((e) -> {
        String msg = e.getActionCommand();
        if (!msg.equals("")) {
          enterPressed(msg);
        }
      });
    }

    private void initButtons() {
      this.home = new JButton(BUTTON_NAME_HOME);  
      this.options = new JButton(BUTTON_NAME_OPTIONS);
      
      JButton[] buttons = new JButton[] {
          home, options
      };
      
      ActionListener buttonListener = (e) -> {
        gameController.onButtonPress(e.getActionCommand());
      };
      
      for (JButton button : buttons) {
        button.setPreferredSize(DIM_MENUBUTTON); 
        button.addActionListener(buttonListener); 
        button.setForeground(Color.BLACK);
        button.setBackground(Color.WHITE); 
      }
      
      
    }

    private void initPanels() {
      // panelDraw
      this.panelDraw = new JPanel() {
        private static final long serialVersionUID = 1L;
        
        @Override
        public void paintComponent(Graphics g) {
          super.paintComponent(g);
          
          Graphics2D G = (Graphics2D) g;
          gameController.paintPlayPanel(G);
        }
        
      };
      this.panelDraw.setPreferredSize(DIM_PANELDRAW);
      this.panelDraw.setBackground(Color.WHITE);
      
      // panelStatus
      this.panelStatus = new JPanel();
      this.panelStatus.setPreferredSize(DIM_PANELSTATUS);
      this.panelStatus.setBackground(Color.BLACK);
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
      this.frame = new JFrame("Blacksmith's Apprentice V 1.0.0");
    }
    
    
    /**
     * To deliver text to the gamecontroller when enter is pressed
     * @param msg
     */
    private void enterPressed(String msg) {
      this.gameController.textIn(msg);
      this.textIn.setText("");
    }
}
