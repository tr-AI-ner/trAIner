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
package map_builder;

import org.junit.Test;
import static org.junit.Assert.assertEquals;

public class ElementStartTest {


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





