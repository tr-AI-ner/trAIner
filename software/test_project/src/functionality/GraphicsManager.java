package functionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

import javax.swing.JFrame;
import javax.swing.JPanel;

import custom_objects.Avatar;
import custom_objects.Entity;
import game.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import map_builder.MapElement;

public class GraphicsManager extends JPanel {

	private static final long serialVersionUID = 1L;

	// GraphicsSystem variables
	private GraphicsConfiguration graphicsConf = 
			GraphicsEnvironment.getLocalGraphicsEnvironment().
			getDefaultScreenDevice().getDefaultConfiguration();
	private BufferedImage imageBuffer;
	private Graphics graphics;
	
	private Color backgroundColor = new Color(34, 30, 31);//new Color(0,0,255);

	JFrame frame;
	Game game;
	Setup setup = new Setup();
	InputManager inputManager;


	public GraphicsManager(InputManager inputManager){
		//set window size
		setPreferredSize(new Dimension(setup.getFrameWidth(), setup.getFrameHeight()));
		
		this.inputManager = inputManager;

		// set up window configurations
		frame = new JFrame("trAIner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setSize(setup.getFrameWidth(),setup.getFrameHeight());
		if(setup.getFullScreen()) { frame.setUndecorated(true); }
		frame.pack();
		frame.setAlwaysOnTop(true);
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
		drawWindowSetup();
	}
	
	private void drawWindowSetup(){
		//draw header
		graphics.setColor(Constants.COLOR_HEADER_1);
		graphics.fillRect(0, 0, setup.getFrameWidth()+12, Constants.WINDOW_HEADER_HEIGHT);
		
		//draw map
		graphics.setColor(Constants.COLOR_MAP_LAND);
		graphics.fillRect(Constants.WINDOW_MAP_MARGIN,
						  Constants.WINDOW_HEADER_HEIGHT+Constants.WINDOW_MAP_MARGIN, 
						  Constants.WINDOW_MAP_WIDTH, 
						  Constants.WINDOW_MAP_HEIGHT);
		
		//draw footer
		graphics.setColor(Constants.COLOR_HEADER_1);
		graphics.fillRect(0, 
						  Constants.WINDOW_HEADER_HEIGHT+(Constants.WINDOW_MAP_MARGIN*2)+Constants.WINDOW_MAP_HEIGHT, 
						  setup.getFrameWidth()+12, 
						  Constants.WINDOW_HEADER_HEIGHT);
		
		//draw right bar
		graphics.setColor(Constants.COLOR_MAP_LAND);
		graphics.fillRect((Constants.WINDOW_MAP_MARGIN*2)+Constants.WINDOW_MAP_WIDTH,
						  Constants.WINDOW_HEADER_HEIGHT, 
						  Constants.WINDOW_RIGHT_BAR_WIDTH, 
						  Constants.WINDOW_RIGHT_BAR_HEIGHT);
	}
	
	/**
	 * draws all entities
	 * 
	 * @param entities
	 */
	public void draw(ArrayList<Entity> entities){
		for (Entity e: entities){
			e.draw(graphics);
//			if (e instanceof Avatar){
//				((Avatar) e).draw(graphics);
//			}
//			else if (e instanceof MapElement){
//				((MapElement) e).draw(graphics);
//			} else {
//				System.out.println("draw function of entity type "+e.getType()+" - not implemented yet");
//			}
		}
	}

//	public void draw(Avatar avatar){
////		this.clear();
////		System.out.println("draw avatar: "+avatar.toString());
//		
//		avatar.draw(graphics);
//		
////		graphics.setColor(avatar.getColor());
////		graphics.fillRect(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
////		graphics.setColor(Color.DARK_GRAY);
////		graphics.drawRect(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
//		
//		//swap buffers to make changes visible
////		this.redraw();
//	}
	
//	public void draw(ArrayList<MapElement> mapElements) {
//		for (MapElement mapElement: mapElements){
//			mapElement.draw(graphics);
//		}
//	}
	
//	public void drawOval(int x, int y, int w, int h, Color color){
//		graphics.setColor(color);
//		graphics.fillOval(x, y, w, h);
//		graphics.setColor(Color.DARK_GRAY);
//		graphics.drawOval(x,y,w,h);
//		
//		this.redraw();
//	}



	public Setup getSetup(){
		return setup;
	}

	public InputManager getInputManager(){
		return inputManager;
	}

	public void setBackgroundColor(Color newCol){
		backgroundColor = newCol;
	}


}
