

package mapsaver;

import functionality.GraphicsManager;
import functionality.Constants;
import map_builder.Map;
import map_builder.MapElement;
import map_builder.MapType;


import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.awt.*;




public class MapSaver {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static char[][] mapArray=new char[Constants.WINDOW_MAP_WIDTH][Constants.WINDOW_MAP_HEIGHT];

    public static void saver(GraphicsManager gc){
        char[][] a=gc.getMap().getMap();
        for(int y=0;y<Constants.WINDOW_MAP_HEIGHT;y++){
            System.out.print(a[0][y]);
            for(int x=1;x<Constants.WINDOW_MAP_WIDTH;x++){
                System.out.print(a[x][y]);
            }
        }

    }

    public static char[][] readMap(String mapFileName){
        BufferedReader fileReader = null;
        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(mapFileName));
            int y=0;
            //Read the file line by line starting from the second line
            while ((line = fileReader.readLine()) != null) {
                String [] tokens = line.split(COMMA_DELIMITER);
                    for (int x = 0; x < Constants.WINDOW_MAP_WIDTH; x++) {
                        mapArray[x][y] = tokens[x].charAt(0);
                    }
                y++;
            }
            System.out.println("CsvFileReader was READ Successfylly!!!");

        }
        catch (Exception e) {
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



    public static void createMap(){
        FileWriter fileWriter = null;
        String fileName="map_01";
        try {
            fileWriter = new FileWriter(fileName);
            //Add a new line separator after the header
            for(int y=0;y<Constants.WINDOW_MAP_HEIGHT;y++){
                if(y>0){fileWriter.append(NEW_LINE_SEPARATOR);}
                fileWriter.append('W');
                fileWriter.append(COMMA_DELIMITER);
                for(int x=1;x<Constants.WINDOW_MAP_WIDTH;x++) {
                    if(x+y>1024){
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


}
