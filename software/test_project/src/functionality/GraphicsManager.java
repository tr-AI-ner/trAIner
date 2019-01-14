package functionality;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import custom_objects.Avatar;
import custom_objects.Entity;
import game.Game;
import game.GameMode;
import map_builder.Map;
import map_builder.MapElement;
import ui.BottomBar;
import ui.RightBar;
import ui.TopBar;
import ui.Menu;
import ui.HelpScreen;
import ui.ExitScreen;

public class GraphicsManager extends JPanel {

	private static final long serialVersionUID = 1L;

	// GraphicsSystem variables
	private GraphicsConfiguration graphicsConf = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().
			getDefaultScreenDevice().getDefaultConfiguration();
	private BufferedImage imageBuffer;
	private Graphics graphics;

	JFrame frame;
	Game game;
	Setup setup = new Setup();
	InputManager inputManager;
	Map map;
    GameMode gameMode;

	// the 3 bars for the game (these should only be accessed if needed, never overridden)
	private TopBar topBar;
	private BottomBar bottomBar;
	private RightBar rightBar;

    private Menu menu;
    private HelpScreen helpScreen;
    private ExitScreen exitScreen;

	
	public GraphicsManager(InputManager inputManager, Map map){
		//set window size
		setPreferredSize(new Dimension(setup.getFrameWidth(), setup.getFrameHeight()));
		
		this.inputManager = inputManager;
		this.map = map;
        this.setupMenu();
		this.setupToolbars();

		// set up window configurations
		frame = new JFrame(Constants.GAME_NAME);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setSize(setup.getFrameWidth(),setup.getFrameHeight());
		if(setup.getFullScreen()) { frame.setUndecorated(true); }
		frame.pack();
		//frame.setAlwaysOnTop(true); -> doesn't work with fileChooser for map-loading/saving
		//center window on host PC
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.validate();
		
		// initialize listeners for user input
		this.addKeyListener(inputManager);
		this.addMouseListener(inputManager);
		this.addMouseMotionListener(inputManager);

		imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());
		graphics = imageBuffer.getGraphics();

		this.requestFocus();
		this.setFocusable(true);
	}
	
	/**
	 * swap imageBuffer to make new drawn changes visible
	 */
	public void redraw() {
		if(this.getGraphics() != null){
			this.getGraphics().drawImage(imageBuffer, 0, 0, this); // Swap
		}
	}
	
	/**
	 * clears full window and draws background color
	 */
	public void clear(){
		graphics.setColor(Constants.COLOR_BACKGROUND);
		graphics.fillRect(0, 0,setup.getFrameWidth()+12,setup.getFrameHeight()+12);
	}

    /**
     * draws the menu
     */
    public void drawMenu(){
        menu.draw(graphics);
    }

    /**
     * Draws the exit screen when user wants to switch to a different mode.
     */
    public void drawExitScreen(){
        exitScreen.draw(graphics);
    }

    /**
     * Draws the help screen.
     */
    public void drawHelpScreen(){
        helpScreen.draw(graphics);
    }
	
	/**
	 * draws the top-, bottom-, & right-bar
	 */
	public void drawWindowSetup(){
		//draw header
		topBar.draw(graphics);
		//draw footer
		bottomBar.draw(graphics);
		//draw right bar
		rightBar.draw(graphics);
	}
	
	/**
	 * draw map and the entities
	 * @param entities
	 */
	public void drawMap(ArrayList<Entity> entities){
		//draw map
		map.draw(graphics, entities, gameMode);
	}

    /**
     * draws the selected map element from the right bar underneath the mouse
     * if the game is in building mode
     *
     * @param selectedElement 
     *
     */
    public void drawBuilderElement(MapElement selectedElement){
        //int x = (gridX * Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN;
        //int y = (gridY*Constants.MAP_ELEMENT_SIZE) + Constants.WINDOW_MAP_MARGIN+Constants.WINDOW_HEADER_HEIGHT;

        //System.out.println("x: "+inputManager.getMouseMovedX()+", y: "+inputManager.getMouseMovedY());
        if (selectedElement != null){
            //System.out.println("oldX: "+selectedElement.getX()+", oldY: "+selectedElement.getY());
//            selectedElement.move(inputManager.getMouseMovedX(), inputManager.getMouseMovedY());
            selectedElement.setX(inputManager.getMouseMovedX());
            selectedElement.setY(inputManager.getMouseMovedY());
//            selectedElement.setGridX(inputManager.getMouseMovedX());
//            selectedElement.setGridY(inputManager.getMouseMovedY());
            //System.out.println("newX: "+selectedElement.getX()+", newY: "+selectedElement.getY());
            selectedElement.drawTemporary(graphics);
        }
    }

	/**
	 * initialize all 3 toolbars
	 * (top-, bottom-, & right-bar)
	 * 
	 */
	private void setupToolbars(){
		topBar = new TopBar(0, 0, setup.getFrameWidth()+12, Constants.WINDOW_HEADER_HEIGHT, 
				Constants.COLOR_HEADER_1, setup, inputManager, "map name", gameMode); // TODO change with the loaded map name
		
		bottomBar = new BottomBar(0, 
				Constants.WINDOW_HEADER_HEIGHT+(Constants.WINDOW_MAP_MARGIN*2)+Constants.WINDOW_MAP_HEIGHT, 
				setup.getFrameWidth()+12, 
				Constants.WINDOW_HEADER_HEIGHT, 
				Constants.COLOR_HEADER_1, setup, inputManager, gameMode);
		
		rightBar = new RightBar((Constants.WINDOW_MAP_MARGIN*2)+Constants.WINDOW_MAP_WIDTH, 
				Constants.WINDOW_HEADER_HEIGHT, 
				Constants.WINDOW_RIGHT_BAR_WIDTH, 
				Constants.WINDOW_RIGHT_BAR_HEIGHT, 
				Constants.COLOR_MAP_LAND, setup, inputManager, gameMode);
        rightBar.setGame(game);
	}

    /**
     * Initializes the Menu of the game.
     */
    private void setupMenu(){
        gameMode = new GameMode(this);
        menu = new Menu(0,0,setup.getFrameWidth()+12,setup.getFrameHeight()+12,
                Constants.COLOR_MAP_LAND, setup, inputManager, gameMode);
        helpScreen = new HelpScreen(0,0,setup.getFrameWidth()+12,setup.getFrameHeight()+12,
                Constants.COLOR_MAP_LAND, setup, inputManager, gameMode);
        exitScreen = new ExitScreen(0,0,setup.getFrameWidth()+12,setup.getFrameHeight()+12,
                Constants.COLOR_MAP_LAND, setup, inputManager, gameMode);
    }

	public Setup getSetup(){
		return setup;
	}
	
	public Map getMap(){
		return map;
	}

    public void setGame(Game game){
        this.game = game;
        gameMode.setGame(game);
    }

	public InputManager getInputManager(){
		return inputManager;
	}

	public TopBar getTopBar(){return topBar;}
	public BottomBar getBottomBar(){return bottomBar;}
	public RightBar getRightBar(){return rightBar;}
	public Menu getMenu(){return menu;}
    public HelpScreen getHelpScreen(){return helpScreen;}
	public ExitScreen getExitScreen(){return exitScreen;}
	public GameMode getGameMode(){return gameMode;}

	public JFrame getFrame(){
		return frame;
	}
}
