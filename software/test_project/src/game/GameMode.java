/** 
  * This class handles the different modes of the game.
  * 
  * 
  * @author: Patrick
  * 
  */
package game;

import functionality.Constants;
import functionality.GraphicsManager;
import game.Game;

public class GameMode {

    /**
     * Modes:
     *     -1: None
     *      0: Player Game
     *      1: Map Builder
     *      2: Preview Mode
     *      3: AI Game
     *      4: Challenge Mode
     *      5: Menu
     *      6: Help Screen
     *      7: Exit Screen
     *      8: Finish Screen
     */
	private int MODE = Constants.MODE_MENU; 
    private int PREVIOUS_MODE = Constants.MODE_NONE;
    private int NEXT_MODE = Constants.MODE_NONE;

    Game game;
    GraphicsManager graphicsManager;

    public GameMode(GraphicsManager graphicsManager){
        this.graphicsManager = graphicsManager;
    }

    /**
     * Switches between modes of the game.
     *
     * @param mode :
     *     -1: None
     *      0: Player Game
     *      1: Map Builder
     *      2: Preview Mode
     *      3: AI Game
     *      4: Challenge Mode
     *      5: Menu
     *      6: Help Screen
     *      7: Exit Screen
     *      8: Finish Screen
     *
     */
    public void changeMode(int mode, boolean fromBar){
        switch(mode){
            case Constants.MODE_PLAYER_GAME:
                if(MODE==Constants.MODE_PLAYER_GAME 
                    || PREVIOUS_MODE==Constants.MODE_PLAYER_GAME
                    || PREVIOUS_MODE==Constants.MODE_NONE){
                    MODE = Constants.MODE_PLAYER_GAME;
                    PREVIOUS_MODE = Constants.MODE_PLAYER_GAME;
                }
                else if(PREVIOUS_MODE != Constants.MODE_PLAYER_GAME
                        || (MODE == Constants.MODE_AI_GAME)
                        || (MODE == Constants.MODE_MAP_BUILDER)){
                    if (graphicsManager.getExitScreen().wasYesSelected()){
                        MODE = Constants.MODE_PLAYER_GAME;
                        PREVIOUS_MODE = Constants.MODE_PLAYER_GAME;
                        NEXT_MODE = Constants.MODE_NONE;
                        graphicsManager.getExitScreen().resetYesSelected();
                    } else {
                        showExitScreen(PREVIOUS_MODE, mode);
                    }
                }
                break;
            case Constants.MODE_MAP_BUILDER:
                if(MODE==Constants.MODE_MAP_BUILDER 
                  || PREVIOUS_MODE==Constants.MODE_MAP_BUILDER
                  || MODE==Constants.MODE_PREVIEW
                  || PREVIOUS_MODE==Constants.MODE_NONE
                  || MODE==Constants.MODE_FINISH){
                    MODE = Constants.MODE_MAP_BUILDER;
                    PREVIOUS_MODE = Constants.MODE_MAP_BUILDER;
                    game.reloadBuildState();
                }
                else if(PREVIOUS_MODE != Constants.MODE_MAP_BUILDER
                        || (MODE == Constants.MODE_PLAYER_GAME)
                        || (MODE == Constants.MODE_AI_GAME)){
                    if (graphicsManager.getExitScreen().wasYesSelected()){
                        MODE = Constants.MODE_MAP_BUILDER;
                        PREVIOUS_MODE = Constants.MODE_MAP_BUILDER;
                        NEXT_MODE = Constants.MODE_NONE;
                        graphicsManager.getExitScreen().resetYesSelected();
                    } else {
                        showExitScreen(PREVIOUS_MODE, mode);
                    }
                }
                break;
            case Constants.MODE_PREVIEW:
                if(MODE == Constants.MODE_MAP_BUILDER)
                    MODE = Constants.MODE_PREVIEW;
                else {
                    MODE = Constants.MODE_MAP_BUILDER;
                    game.reloadBuildState();
                }
                break;
            case Constants.MODE_AI_GAME:
                if(MODE==Constants.MODE_AI_GAME 
                  || PREVIOUS_MODE==Constants.MODE_AI_GAME
                  || PREVIOUS_MODE==Constants.MODE_NONE){
                    MODE = Constants.MODE_AI_GAME;
                    PREVIOUS_MODE = Constants.MODE_AI_GAME;
                }
                else if(PREVIOUS_MODE != Constants.MODE_AI_GAME
                        || (MODE == Constants.MODE_PLAYER_GAME)
                        || (MODE == Constants.MODE_MAP_BUILDER)){
                    if (graphicsManager.getExitScreen().wasYesSelected()){
                        MODE = Constants.MODE_AI_GAME;
                        PREVIOUS_MODE = Constants.MODE_AI_GAME;
                        NEXT_MODE = Constants.MODE_NONE;
                        graphicsManager.getExitScreen().resetYesSelected();
                    } else {
                        showExitScreen(PREVIOUS_MODE, mode);
                    }
                }
                break;
            case Constants.MODE_CHALLENGE:
                break;
            case Constants.MODE_MENU:
                if(MODE==Constants.MODE_AI_GAME
                  || MODE==Constants.MODE_PLAYER_GAME
                  || MODE==Constants.MODE_MAP_BUILDER){
                    PREVIOUS_MODE = MODE;        
                }
                MODE = Constants.MODE_MENU;
                break;
            case Constants.MODE_HELP:
                if(MODE==Constants.MODE_AI_GAME
                  || MODE==Constants.MODE_PLAYER_GAME
                  || MODE==Constants.MODE_MAP_BUILDER){
                    PREVIOUS_MODE = MODE;
                }
                MODE = Constants.MODE_HELP;
                graphicsManager.getHelpScreen().setLastOpenedTime(System.currentTimeMillis());
                break;
            case Constants.MODE_EXIT:
                System.exit(0);
                break;
            case Constants.MODE_FINISH:
                if(MODE==Constants.MODE_AI_GAME){
                    PREVIOUS_MODE = MODE;
                    MODE = Constants.MODE_FINISH;

                }
                if (MODE==Constants.MODE_PLAYER_GAME){
                    PREVIOUS_MODE = MODE;
                    MODE = Constants.MODE_FINISH;
                }
                break;
            default: 
                System.out.println("default case... mode: "+getModeString(mode));
                break;
        }
    }

    /**
     * Get string representation of a mode for debugging purposes.
     *
     * @param mode integer value of constants
     *
     * @return the string representation
     */
    public String getModeString(int mode){
        switch (mode){
            case -1: return "NONE"; 
            case 0: return "PLAYER_GAME"; 
            case 1: return "MAP_BUILDER"; 
            case 2: return "PREVIEW"; 
            case 3: return "AI_GAME"; 
            case 4: return "CHALLENGE"; 
            case 5: return "MENU"; 
            case 6: return "HELP"; 
            case 7: return "EXIT"; 
        }
        return "Undefined mode: "+mode;
    }

    /**
     * Shows the exit screen in case a different game/build session should be opened.
     *
     * @param oldMode from which to switch from
     * @param newMode from which to switch to
     */
    private void showExitScreen(int oldMode, int newMode){
        graphicsManager.getExitScreen().setText(oldMode, newMode);
        NEXT_MODE = newMode;
        MODE = Constants.MODE_EXIT;
    }

    public void setGame(Game game){this.game = game;}
    public Game getGame(){return game;}

    public int getPreviousMode(){return PREVIOUS_MODE;}
    public int getMode(){return MODE;}
    public int getNextMode(){return NEXT_MODE;}
    public void resetNextMode(){NEXT_MODE=Constants.MODE_NONE;}

}





