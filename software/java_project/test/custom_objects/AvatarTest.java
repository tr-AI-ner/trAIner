/** 
  * Copyright 2018-11-22 Patrick
  *
  * All rights reserved
  *
  * @Author: Patrick
  * 
  * 
  * Short description for what this file does...
  * 
  */
package custom_objects;

import org.junit.Test;

import functionality.Constants;
import functionality.GraphicsManager;
import functionality.InputManager;
import functionality.Setup;
import game.Game;
import map_builder.Map;

import static org.junit.Assert.assertEquals;

import java.awt.Color;

import org.junit.Before;

public class AvatarTest {
	
	Game game;
	GraphicsManager gm;
	InputManager inputManager;
	Setup setup;
	Map map;
	
	Avatar avatar;
	int aX = 33 * Constants.MAP_ELEMENT_SIZE;
	int aY = 33 * Constants.MAP_ELEMENT_SIZE;
	
	@Before
	public void setup(){
		this.inputManager = new InputManager();
		this.map = new Map();
		this.gm = new GraphicsManager(inputManager, map);
		this.setup = new Setup();
		this.game = new Game(gm);
		
		this.avatar = new Avatar(aX, aY,
				Constants.AVATAR_WIDTH, Constants.AVATAR_HEIGHT, 
				Constants.COLOR_AVATAR_RED);
		this.avatar.setSetup(setup);
		this.avatar.setGame(game);
	}
	
	@Test
	public void testCollisionWithBorder(){
		avatar.setX(x);
	}


    @Test
    public void testInteger(){
        int result = 5 * 10;
        
        assertEquals(50, result);
    }

    @Test
    public void testString(){
        String result = "the string";
        assertEquals("the string", result);
    }


}





