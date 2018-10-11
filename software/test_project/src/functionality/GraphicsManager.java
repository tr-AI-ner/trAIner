package functionality;

import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.GraphicsConfiguration;
import java.awt.GraphicsEnvironment;
import java.awt.image.BufferedImage;

import javax.swing.JFrame;
import javax.swing.JPanel;

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

	JFrame frame;
	Game game;
	Setup setup = new Setup();


	public GraphicsManager(){
		setPreferredSize(new Dimension(setup.getFrameWidth(), setup.getFrameHeight()));

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

		imageBuffer = graphicsConf.createCompatibleImage(this.getWidth(), this.getHeight());	 
		graphics = imageBuffer.getGraphics();

		this.requestFocus();
		this.setFocusable(true);
	}










}
