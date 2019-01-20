/** 
  * Copyright 2019-01-20 Patrick
  *
  * All rights reserved
  *
  * @Author: Patrick
  * 
  * 
  * Short description for what this file does...
  * 
  */
package genetic_algorithm;

import org.junit.*;
import static org.junit.Assert.assertEquals;

public class PopulationTest {


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





