package com.fsociety.warzone.util;

import com.fsociety.warzone.map.WZMap;

import java.io.BufferedReader;
import java.io.*;

public class FileIO {

    public static WZMap loadAndValidateMap(String p_fileName) {
        WZMap mapValues = null;
        try {
            mapValues = new WZMap();
            String l_filePath = "src/main/resources/" + p_fileName;
            FileReader mapFile = new FileReader(l_filePath);
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
                return null;
            }
            String[] continentData = data.substring(data.toString().toLowerCase().indexOf("[continents]")+13, data.toString().toLowerCase().indexOf("[countries]")).split("\n");
            String[] countryData = data.substring(data.toString().toLowerCase().indexOf("[countries]")+12, data.toString().toLowerCase().indexOf("[borders]")).split("\n");
            String[] neighborData = data.substring(data.toString().toLowerCase().indexOf("[borders]")+10).split("\n");
            for(String s:continentData) {
                mapValues.addContinent(Integer.parseInt(s.substring(0,s.indexOf(" "))),Integer.parseInt(s.substring(s.indexOf(" ")+1)));
            }
            for(String s:countryData) {
                mapValues.addCountry(Integer.parseInt(s.substring(0,s.indexOf(" "))),Integer.parseInt(s.substring(s.indexOf(" ")+1)));
            }
            for(String s:neighborData) {
                String[] temp = s.split(" ");
                int[] arr = new int[temp.length];
                int countryID= Integer.parseInt(temp[0]);
                for (int i=1;i<temp.length;i++) {
                    arr[i] = Integer.parseInt(temp[i]);
                    mapValues.addNeighbour(countryID,arr[i]);
                }
            }
        }
        catch (Exception e) {
            // e.printStackTrace();
            return null;
        }
        mapValues.initGameStates();
        return mapValues;
    }

    public static boolean validateMapPlaceholder(WZMap dWzMap) {
        return true;
    }

    public static boolean saveMapPlaceholder(WZMap dWzMap) {
        return true;
    }
}
