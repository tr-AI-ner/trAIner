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
package tester;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import custom_objects.*;
import functionality.*;
import game.*;
import map_builder.*;
import ui.*;

public class Tester {

    public static void main(String[] args) {
        boolean noErrors = true;

        // sample code for testing
//        Result result = JUnitCore.runClasses(
//            //custom_objects package
//            AvatarTest.class, EntityTest.class,
//            //functionality package
//            ClockTest.class, GraphicsManagerTest.class,
//            InputManagerTest.class, SetupTest.class,
//            //game package
//            GameTest.class, MainTest.class,
//            //map_builder package
//            ElementBallTest.class, ElementBlackHoleTest.class, ElementEnemyTest.class,
//            ElementFinishTest.class, ElementLandTest.class, ElementLaserTest.class,
//            ElementPlasmaBallTest.class, ElementStartTest.class, ElementWallTest.class,
//            ElementWaterTest.class, MapElementTest.class, MapTest.class,
//            //ui package
//            BottomBarTest.class, CustomSliderTest.class, RightBarTest.class, TopBarTest.class,
//            UIElementTest.class
//        );
        Result result = JUnitCore.runClasses(
        		AllTests.class
        );
        for (Failure failure : result.getFailures()) {
            System.out.println(failure.toString());
            noErrors = false;
        }

        String finalMessage = "Everything works fine, good job!!!";
        if (!noErrors)
            finalMessage = "Sorry, there were some errors... Fix them & re-run the tests!";

        System.out.println(finalMessage);


    }


}





