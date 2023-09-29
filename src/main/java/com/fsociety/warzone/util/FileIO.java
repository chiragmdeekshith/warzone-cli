package com.fsociety.warzone.util;

import java.io.BufferedReader;
import java.io.*;

public class FileIO {
    public static boolean loadAndValidateMap(String p_fileName) {
        try {
            FileReader mapFile = new FileReader(p_fileName);
            String line = null;
            StringBuilder data = new StringBuilder();
            BufferedReader mapReader = new BufferedReader(mapFile);
            line = mapReader.readLine();
            while(line !=null) {
                if(!line.equals("\n")){
                    data.append(line).append("\n");
                    line = mapReader.readLine();
                }
            }
            if (data.toString().toLowerCase().contains("[continents]") && data.toString().toLowerCase().contains("[countries]") && data.toString().toLowerCase().contains("[borders]")) {
            } else {
                System.out.println("Missing Information/Not in correct format");
                return false;
            }
            String[] continentData = data.substring(data.toString().toLowerCase().indexOf("[continents]"), data.toString().toLowerCase().indexOf("[countries]")).split("\n");
            String[] countryData = data.substring(data.toString().toLowerCase().indexOf("[countries]"), data.toString().toLowerCase().indexOf("[borders]")).split("\n");
            String[] neighborData = data.substring(data.toString().toLowerCase().indexOf("[borders]")).split("\n");
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }
}
