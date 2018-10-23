package functionality;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

import custom_objects.Avatar;
import game.Game;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

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
		setPreferredSize(new Dimension(setup.getFrameWidth(), setup.getFrameHeight()));
		
		this.inputManager = inputManager;

		frame = new JFrame("trAIner");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().add(this);
		frame.setSize(setup.getFrameWidth(),setup.getFrameHeight());
		if(setup.getFullScreen()) { frame.setUndecorated(true); }
		frame.pack();
		frame.setAlwaysOnTop(true);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		frame.validate();
		
		// initialize listeners for user input
		this.addKeyListener(inputManager);
		this.addMouseListener(inputManager);
		this.addMouseMotionListener(inputManager);

		imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());
		imageBuffer.getGraphics().setColor(Color.BLUE);
//		System.out.println("image buffer rgb: "+imageBuffer.getGraphics().setColor(Color.BLUE););
		graphics = imageBuffer.getGraphics();

		this.requestFocus();
		this.setFocusable(true);
		
//		draw(300, 200, 25, 25, Color.BLUE);
	}
	
	private void redraw() {
		if(this.getGraphics() != null){
			this.getGraphics().drawImage(imageBuffer, 0, 0, this); // Swap
		}
	}
	
	public void clear(){
//		graphics.setColor(Color.DARK_GRAY);
		graphics.setColor(backgroundColor);
		graphics.fillRect(0, 0,setup.getFrameWidth()+12,setup.getFrameHeight()+12);
	}

	public void draw(Avatar avatar){
		this.clear();
//		System.out.println("draw avatar: "+avatar.toString());
		
		graphics.setColor(avatar.getColor());
		graphics.fillRect(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
		graphics.setColor(Color.DARK_GRAY);
		graphics.drawRect(avatar.getX(), avatar.getY(), avatar.getWidth(), avatar.getHeight());
		
		//swap buffers to make changes visible
		this.redraw();
	}
	
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
