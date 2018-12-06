package map_saver;

import functionality.Constants;
import game.Game;
import map_builder.*;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import static functionality.Constants.MAP_ELEMENT_SIZE;
import static javax.swing.JOptionPane.showMessageDialog;

public class MapSaverLoader {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private Game game;

    public MapSaverLoader(Game game){
        this.game=game;
    }

    /**
     * Takes filename as input parameter
     * Loads a csv file from a default location, unless specified differently
     *  clears all MapElements and Entities and reloads them
     *  OPEN QUESTION:
     *  Map Elementn Land
     * @param mapFileName
     */
    public void loadMap(String mapFileName){//, ArrayList<MapElement> mapElements) {
        ElementBlackHole frontHole=new ElementBlackHole(0,0, Constants.COLOR_BLACK_HOLE);
        ElementBlackHole backHole;
        boolean firstHole=true;
        char[][] map = MapReader.readMap(mapFileName);
        game.resetMapElements();
        game.resetEntities();
        for (int col = 0; col < Constants.WINDOW_MAP_WIDTH / MAP_ELEMENT_SIZE; col += 1) {
            for (int row = 0; row < Constants.WINDOW_MAP_HEIGHT / MAP_ELEMENT_SIZE; row += 1) {
                if (map[col][row] == MapType.WALL.representation()) {
                    ElementWall newWall = new ElementWall(col, row, Constants.COLOR_WALL);
                    game.getMapElements().add(newWall);
                }
                else if (map[col][row] == MapType.START.representation())	{
                    ElementStart newStart = new ElementStart(col, row, functionality.Constants.COLOR_MAP_START);
                    game.getMapElements().add(newStart);
                }
                else if (map[col][row] == MapType.FINISH.representation())	{
                    ElementFinish newFinish = new ElementFinish(col, row, functionality.Constants.COLOR_MAP_FINISH);
                    game.getMapElements().add(newFinish);
                }

                else if (map[col][row] == MapType.BLACK_HOLE.representation())	{
                    if(firstHole){
                        frontHole=new ElementBlackHole(col, row, Constants.COLOR_BLACK_HOLE);
                        firstHole=false;
                    }
                    else{
                        backHole=new ElementBlackHole(col, row, Constants.COLOR_BLACK_HOLE);
                        frontHole.setAttachedBlackHole(backHole);
                        backHole.setAttachedBlackHole(frontHole);
                        game.getMapElements().add(frontHole);
                        game.getMapElements().add(backHole);
                        firstHole=true;
                    }
                }
                else if (map[col][row] == MapType.ENEMY.representation())	{
                        ElementEnemy newEnemy = new ElementEnemy(col, row, functionality.Constants.COLOR_ENEMY);
                        game.getMapElements().add(newEnemy);
                }
                else if (map[col][row] == MapType.WATER.representation())	{
                     ElementWater newWater = new ElementWater(col, row,  functionality.Constants.COLOR_WATER);
                     game.getMapElements().add(newWater);
                }

                else if (map[col][row] == MapType.LASER.representation())	{
                    ElementLaser newLaser = new ElementLaser(col, row,  Constants.COLOR_LASER);
                    game.getMapElements().add(newLaser);
                }
                else if (map[col][row] == MapType.PLASMA_BALL.representation())	{
                    ElementPlasmaBall newBall = new ElementPlasmaBall(col, row,  Constants.COLOR_PLASMA_BALL);
                    game.getMapElements().add(newBall);
                }
                else{
                    // MapType.LAND.representation()
                    // LAND??
                }

            }
        }
        game.getEntities().add(game.getAvatar());
        game.getEntities().addAll(game.getMapElements());
    }


//============================================================================================================
// //====================================SAVE====================================================
////=============================================================================================================

    /**
     * saves files into a default directory
     * in a csv format
     * @param fileName
     */
    public void saveMap(String fileName){
      //  fileName=editFileName(fileName);
        FileWriter fileWriter = null;
        char [][] m=game.getMap().getMapArr();
        try {
            fileWriter = new FileWriter(fileName);
            //Add a new line separator after the header
            for(int y=0;y<Constants.WINDOW_MAP_HEIGHT/MAP_ELEMENT_SIZE;y++){
                if(y>0){fileWriter.append(NEW_LINE_SEPARATOR);}
                //check what representation
                fileWriter.append(m[0][y]);
                fileWriter.append(COMMA_DELIMITER);
                for(int x=1;x<Constants.WINDOW_MAP_WIDTH/MAP_ELEMENT_SIZE;x++) {
                    fileWriter.append(m[x][y]);
                    fileWriter.append(COMMA_DELIMITER);
                }
            }
            System.out.println("CSV file was created successfully !!!");
        } catch (Exception e) {
            System.out.println("Error in CsvFileWriter !!!");
            e.printStackTrace();
        } finally {
            try {
                fileWriter.flush();
                fileWriter.close();
            } catch (IOException e) {
                System.out.println("Error while flushing/closing fileWriter !!!");
                e.printStackTrace();
            }
        }
    }

//=================================================================================================================

    //===================================LOGIC====================================================================
    //=============================================================================================================

    /**
     * opens a filereader for Loading maps
     */
    public void loadButtonLogic(){
        game.getGraphicsManager().getInputManager().setMouseClicked(false);
        try{
            JFileChooser fcLoad = new JFileChooser();
            fcLoad.setVisible(true);
            fcLoad.setCurrentDirectory(new File("."));
            fcLoad.setDialogTitle("LOAD MAP");
            int returnVal = fcLoad.showOpenDialog(game.getGraphicsManager().getFrame());
            if (returnVal == JFileChooser.APPROVE_OPTION && fcLoad.getSelectedFile().getName().contains(".csv")) {
                String filename=fcLoad.getSelectedFile().getName();
                loadMap(filename);
            }
            else{fcLoad.cancelSelection();}
        }
        catch(Exception e){
            showMessageDialog(game.getGraphicsManager().getFrame(), "The file was unable to load. Try again or select a different file");

        }
    }
/*
* opens a filechooser dialog
* */
    public void saveButtonLogic(){
        game.getGraphicsManager().getInputManager().setMouseClicked(false);
        try{
            JFileChooser fcPick = new JFileChooser();
            fcPick.setCurrentDirectory(new File("."));
            fcPick.setVisible(true);
            fcPick.setDialogTitle("SAVE MAP");
            int returnVal = fcPick.showDialog(game.getGraphicsManager().getFrame(),"SAVE");
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                String filename=fcPick.getSelectedFile().getName();
                filename=editFileName(filename);
                saveMap(filename);
            }
            else{
                fcPick.cancelSelection();
                //writer.writeIt(file.getPath()+".txt", factura.toString());
            }
        }
        catch(Exception e){
            showMessageDialog(game.getGraphicsManager().getFrame(), "Do not Use '.' in a filename");
        }
    }

    /**
     * edits filename
     * @param filename
     * @return
     */
    public String editFileName(String filename){
        if(filename.length()>0) {
            if (filename.contains(".")) {
                String[] name = filename.split(".");
                if (name[0].length()>0) {
                    filename = name[0] + ".csv";
                }
                else {
                    filename="";
                }
            }
            else {
                filename += ".csv";
            }
        }
        return filename;
    }


}
