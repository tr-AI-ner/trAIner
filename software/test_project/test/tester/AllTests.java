package tester;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

import custom_objects.AvatarTest;
import game.GameTest;
import genetic_algorithm.GenomeTest;
import genetic_algorithm.IndividualTest;
import genetic_algorithm.PopulationTest;
import map_builder.ElementEnemyTest;
import map_builder.ElementPlasmaBallTest;
import ui.RightBarTest;

@RunWith(Suite.class)
@SuiteClasses({
	//custom_objects package
    AvatarTest.class,
    //game package
    GameTest.class,
    //map_builder package
    ElementEnemyTest.class, ElementPlasmaBallTest.class,
    //ui package
    RightBarTest.class,
    //genetic_algorithm package
    GenomeTest.class, IndividualTest.class, PopulationTest.class
})
public class AllTests {

}
