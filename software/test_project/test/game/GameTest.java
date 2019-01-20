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
package game;

import org.junit.*;

import game.Game;
import functionality.GraphicsManager;
import functionality.InputManager;
import map_builder.Map;

import static org.junit.Assert.assertEquals;



public class GameTest {

    Game game;
    GraphicsManager gm;
    InputManager inputManager;
    private Map map;

    boolean theFuck;

    @Before
    public void setup(){
        System.out.println("Setting up GameTest...");

//        Map map = new Map();
//        map = new Map();
        this.inputManager = new InputManager();
        this.map = new Map();
        this.gm = new GraphicsManager(inputManager, map);
        this.game = new Game(gm);
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





