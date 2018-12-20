package map_saver;

import functionality.Constants;
import game.Game;
import map_builder.*;

import javax.swing.*;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;

import java.awt.EventQueue;
import javax.swing.JFileChooser;
import java.io.FileReader;

import static functionality.Constants.MAP_ELEMENT_SIZE;
import static functionality.Constants.WINDOW_MAP_WIDTH;
import static functionality.Constants.WINDOW_MAP_HEIGHT;
import static javax.swing.JOptionPane.showMessageDialog;

public class MapSaverLoader {

    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private Game game;
    
    int xSize = WINDOW_MAP_WIDTH / MAP_ELEMENT_SIZE;
    int ySize = WINDOW_MAP_HEIGHT / MAP_ELEMENT_SIZE;
    private char[][] mapArray = new char[xSize][ySize];

    // directory name where custom maps should be saved and loaded from
    private String dirName = "../../default_maps/";

    public MapSaverLoader(Game game){
        this.game=game;

        // init basic map
        for(int x=0; x < xSize; x++){
            for(int y=0; x < ySize; x++){
                mapArray[x][y] = '_'; 
            }
        }
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
        //char[][] map = MapReader.readMap(mapFileName);
        char[][] map = readMap(mapFileName);
        game.getGraphicsManager().getTopBar().setMapName(editMapName(mapFileName));
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
                    game.getAvatar().setX(row*Constants.MAP_ELEMENT_SIZE);
                    game.getAvatar().setY(col*Constants.MAP_ELEMENT_SIZE);
                    game.getAvatar().reset();
                    // TODO: do this for every single individual once the GA is merged with the master branch
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


    //============= SAVE =====================

    /**
     * saves files into a default directory
     * in a csv format
     * @param fileName
     */
    public void saveMap(String fileName){
        FileWriter fileWriter = null;
        char[][] m =game.getMap().getMapArr();
        try {
            fileWriter = new FileWriter(new File(dirName,fileName));
            //fileWriter.setCurrentDirectory("../../default_maps/");
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


    //============= LOGIC =====================

    /**
     * opens a filereader for Loading maps
     */
    public void loadButtonLogic(){
        game.getGraphicsManager().getInputManager().setMouseClicked(false);
        try{
            EventQueue.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    JFileChooser fcLoad = new JFileChooser();
                    fcLoad.setVisible(true);
                    fcLoad.setCurrentDirectory(new File(dirName));
                    fcLoad.setDialogTitle("LOAD MAP");
                    int returnVal = fcLoad.showOpenDialog(game.getGraphicsManager().getFrame());
                    if (returnVal == JFileChooser.APPROVE_OPTION && fcLoad.getSelectedFile().getName().contains(".csv")) {
                        String filename=fcLoad.getSelectedFile().getName();
                        System.out.println("should load filename: "+filename);
                        loadMap(filename);
                    }
                    else{fcLoad.cancelSelection();}
                }
            });
        }
        catch(Exception e){
            showMessageDialog(game.getGraphicsManager().getFrame(), "The file was unable to load. Try again or select a different file");
        }
    }

    /**
     * opens a filechooser dialog
     */
    public void saveButtonLogic(){
        game.getGraphicsManager().getInputManager().setMouseClicked(false);
        try{
            JFileChooser fcPick = new JFileChooser();
            fcPick.setCurrentDirectory(new File(dirName));
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
     * takes away '.csv' from the filename
     * in order for the map name to be shown in the TopBar
     */
    public String editMapName(String mapName) {
    	mapName=mapName.substring(0,mapName.length()-4);
    	return mapName;
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
                } else {
                    filename="";
                }
            } else {
                filename += ".csv";
            }
        }
        return filename;
    }

    /**
     * converts information from a csv file into a 2d array
     * @param mapFileName
     * @return
     */
    public char[][] readMap(String mapFileName){
        BufferedReader fileReader = null;
        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(new File(dirName, mapFileName)));
            int y=0;
            while ((line = fileReader.readLine()) != null) {
                String [] tokens = line.split(COMMA_DELIMITER);
                    for (int x = 0; x < Constants.WINDOW_MAP_WIDTH/MAP_ELEMENT_SIZE; x++) {
                        mapArray[x][y] = tokens[x].charAt(0);
                    }
                y++;
            }
            System.out.println("CsvFileReader was READ Successfylly!!!");

        } catch (Exception e) {
            System.out.println("Error in CsvFileReader !!!");
            e.printStackTrace();
        } finally {
            try {
                fileReader.close();
            } catch (IOException e) {
                System.out.println("Error while closing fileReader !!!");
                e.printStackTrace();
            }
         }
         return mapArray;
    }

    /**
     * info: not used currently
     *
     * used to generate csv files for teting purposes
     * hardcoded walues
     */
    public void generateMap(){
        FileWriter fileWriter = null;
        String fileName="map_02";
        try {
            fileWriter = new FileWriter(fileName);
            //Add a new line separator after the header
            for(int y=0;y<Constants.WINDOW_MAP_HEIGHT/MAP_ELEMENT_SIZE;y++){
                if(y>0){fileWriter.append(NEW_LINE_SEPARATOR);}
                if(y%4==0){fileWriter.append('W');}
                else{fileWriter.append('_');}
                fileWriter.append(COMMA_DELIMITER);
                for(int x=1;x<Constants.WINDOW_MAP_WIDTH/MAP_ELEMENT_SIZE;x++) {
                    if((x+y>70)&&(x%4==0)&&(y%4==0)){
                        fileWriter.append('W');
                        fileWriter.append(COMMA_DELIMITER);
                    }
                    else{
                        fileWriter.append('_');
                        fileWriter.append(COMMA_DELIMITER);
                    }
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
    /**
     * returns True if SAVE button was clicked
     * @return
     */
    public boolean saveButtonClicked() {
    	return game.getGraphicsManager().getTopBar().getSaveButton().contains(game.getGraphicsManager().getInputManager().getMouseClickedX(), game.getGraphicsManager().getInputManager().getMouseClickedY());
    }

    /**
     * returns True if LOAD button was clicked
     * @return 
     */
    public boolean loadButtonClicked() {
    	return game.getGraphicsManager().getTopBar().getLoadButton().contains(game.getGraphicsManager().getInputManager().getMouseClickedX(), game.getGraphicsManager().getInputManager().getMouseClickedY());
    }
    
    /**
     * Resets entities and map elements
     * so that the UI shows an empty map
     */
    public void initEmptyMap() {
        game.resetMapElements();
        game.resetEntities();
    }
    
}
