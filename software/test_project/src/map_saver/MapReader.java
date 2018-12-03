

package map_saver;

import functionality.Constants;

import java.io.FileWriter;
import java.io.IOException;
import java.io.BufferedReader;
import java.io.FileReader;

import static functionality.Constants.MAP_ELEMENT_SIZE;


public class MapReader {
    //Delimiter used in CSV file
    private static final String COMMA_DELIMITER = ",";
    private static final String NEW_LINE_SEPARATOR = "\n";
    private static char[][] mapArray=new char[Constants.WINDOW_MAP_WIDTH/MAP_ELEMENT_SIZE][Constants.WINDOW_MAP_HEIGHT/MAP_ELEMENT_SIZE];

// reads maps from CSV files

    /**
     * converts information from a csv file into a 2d array
     * @param mapFileName
     * @return
     */
    public static char[][] readMap(String mapFileName){
        BufferedReader fileReader = null;
        try {
            String line = "";
            fileReader = new BufferedReader(new FileReader(mapFileName));
            int y=0;
            while ((line = fileReader.readLine()) != null) {
                String [] tokens = line.split(COMMA_DELIMITER);
                    for (int x = 0; x < Constants.WINDOW_MAP_WIDTH/MAP_ELEMENT_SIZE; x++) {
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

/**
 * used to generate csv files for teting purposes
 * hardcoded walues
 * */
    public static void generateMap(){
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


}
