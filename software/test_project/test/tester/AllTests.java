package tester;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import custom_objects.AvatarTest;
import custom_objects.EntityTest;
import functionality.ClockTest;
import functionality.GraphicsManagerTest;
import functionality.InputManagerTest;
import functionality.SetupTest;
import game.GameTest;
import game.MainTest;
import map_builder.ElementBallTest;
import map_builder.ElementBlackHoleTest;
import map_builder.ElementEnemyTest;
import map_builder.ElementFinishTest;
import map_builder.ElementLandTest;
import map_builder.ElementLaserTest;
import map_builder.ElementPlasmaBallTest;
import map_builder.ElementStartTest;
import map_builder.ElementWallTest;
import map_builder.ElementWaterTest;
import map_builder.MapElementTest;
import map_builder.MapTest;
import ui.BottomBarTest;
import ui.CustomSliderTest;
import ui.RightBarTest;
import ui.TopBarTest;
import ui.UIElementTest;

@RunWith(Suite.class)
@SuiteClasses({
	//custom_objects package
    AvatarTest.class, EntityTest.class,
    //functionality package
    ClockTest.class, GraphicsManagerTest.class,
    InputManagerTest.class, SetupTest.class,
    //game package
    GameTest.class, MainTest.class,
    //map_builder package
    ElementBallTest.class, ElementBlackHoleTest.class, ElementEnemyTest.class,
    ElementFinishTest.class, ElementLandTest.class, ElementLaserTest.class,
    ElementPlasmaBallTest.class, ElementStartTest.class, ElementWallTest.class,
    ElementWaterTest.class, MapElementTest.class, MapTest.class,
    //ui package
    BottomBarTest.class, CustomSliderTest.class, RightBarTest.class, TopBarTest.class,
    UIElementTest.class
})
public class AllTests {

}
