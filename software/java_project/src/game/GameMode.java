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
     *
     */
    public void changeMode(int mode, boolean fromMenu){
        System.out.println("changeMode... currently: "+getModeString(MODE)+", PREVIOUS_MODE="+getModeString(PREVIOUS_MODE)+", should change to mode: "+getModeString(mode));
        switch(mode){
            case Constants.MODE_PLAYER_GAME:
                System.out.println("changeMode - PLAYER, mode: "+getModeString(mode)); 
                if(MODE==Constants.MODE_PLAYER_GAME 
                    || PREVIOUS_MODE==Constants.MODE_PLAYER_GAME
                    || PREVIOUS_MODE==Constants.MODE_NONE){
                    MODE = Constants.MODE_PLAYER_GAME;
                    PREVIOUS_MODE = MODE;
                }
                else if(PREVIOUS_MODE != Constants.MODE_PLAYER_GAME){
                    if (graphicsManager.getExitScreen().wasYesSelected()){
                        MODE = Constants.MODE_PLAYER_GAME;
                        PREVIOUS_MODE = Constants.MODE_NONE;
                        NEXT_MODE = Constants.MODE_NONE;
                        graphicsManager.getExitScreen().resetYesSelected();
                    } else {
                        showExitScreen(PREVIOUS_MODE, mode);
                    }
                }
                break;
            case Constants.MODE_MAP_BUILDER:
                System.out.println("changeMode - BULDER, mode: "+getModeString(mode)); 
                if(MODE==Constants.MODE_MAP_BUILDER 
                  || PREVIOUS_MODE==Constants.MODE_MAP_BUILDER
                  || MODE==Constants.MODE_PREVIEW
                  || PREVIOUS_MODE==Constants.MODE_NONE){
                    MODE = Constants.MODE_MAP_BUILDER;
                    PREVIOUS_MODE = MODE;
                    game.reloadBuildState();
                }
                else if(PREVIOUS_MODE != Constants.MODE_MAP_BUILDER){
                    if (graphicsManager.getExitScreen().wasYesSelected()){
                        MODE = Constants.MODE_MAP_BUILDER;
                        PREVIOUS_MODE = Constants.MODE_NONE;
                        NEXT_MODE = Constants.MODE_NONE;
                        graphicsManager.getExitScreen().resetYesSelected();
                    } else {
                        showExitScreen(PREVIOUS_MODE, mode);
                    }
                }
                break;
            case Constants.MODE_PREVIEW:
                System.out.println("changeMode - Preview, mode: "+getModeString(mode)); 
                if(MODE == Constants.MODE_MAP_BUILDER)
                    MODE = Constants.MODE_PREVIEW;
                else {
                    MODE = Constants.MODE_MAP_BUILDER;
                    game.reloadBuildState();
                }
                break;
            case Constants.MODE_AI_GAME:
                System.out.println("changeMode - AI game, mode: "+getModeString(mode)); 
                if(MODE==Constants.MODE_AI_GAME 
                  || PREVIOUS_MODE==Constants.MODE_AI_GAME
                  || PREVIOUS_MODE==Constants.MODE_NONE){
                    MODE = Constants.MODE_AI_GAME;
                    PREVIOUS_MODE = MODE;
                }
                else if(PREVIOUS_MODE != Constants.MODE_AI_GAME){
                    if (graphicsManager.getExitScreen().wasYesSelected()){
                        MODE = Constants.MODE_AI_GAME;
                        PREVIOUS_MODE = Constants.MODE_NONE;
                        NEXT_MODE = Constants.MODE_NONE;
                        graphicsManager.getExitScreen().resetYesSelected();
                    } else {
                        showExitScreen(PREVIOUS_MODE, mode);
                    }
                }
                break;
            case Constants.MODE_CHALLENGE:
                System.out.println("changeMode - challenge, mode: "+getModeString(mode)); 
                break;
            case Constants.MODE_MENU:
                System.out.println("changeMode - menu, mode: "+getModeString(mode)); 
                if(MODE==Constants.MODE_AI_GAME
                  || MODE==Constants.MODE_PLAYER_GAME
                  || MODE==Constants.MODE_MAP_BUILDER){
                    PREVIOUS_MODE = MODE;        
                }
                MODE = Constants.MODE_MENU;
                break;
            case Constants.MODE_HELP:
                System.out.println("changeMode - help, mode: "+getModeString(mode)); 
                PREVIOUS_MODE = MODE;
                MODE = Constants.MODE_HELP;
                break;
            case Constants.MODE_EXIT:
                System.out.println("changeMode - exit, mode: "+getModeString(mode)); 
                System.exit(0);
                break;
            default: 
                System.out.println("default case... mode: "+getModeString(mode));
                break;
        }
        System.out.println("ChangeMode done, MODE="+getModeString(MODE)+", PREVIOUS_MODE="+getModeString(PREVIOUS_MODE)+", NEXT_MODE="+getModeString(NEXT_MODE));
    }

    private String getModeString(int mode){
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

    public void setGame(Game game){
        this.game = game;
    }

    public int getPreviousMode(){return PREVIOUS_MODE;}
    public int getMode(){return MODE;}
    public int getNextMode(){return NEXT_MODE;}
    public void resetNextMode(){NEXT_MODE=Constants.MODE_NONE;}

}





