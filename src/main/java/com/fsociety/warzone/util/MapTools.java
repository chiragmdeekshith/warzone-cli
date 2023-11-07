package com.fsociety.warzone.util;

import com.fsociety.warzone.map.AbstractMap;
import com.fsociety.warzone.map.EditMap;
import com.fsociety.warzone.map.PlayMap;

import java.io.BufferedReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.io.File;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.Map;
import java.util.ArrayList;


/**
 * This class provides all the functionality to read from Domination type .map files and store them in WZMap Objects.
 * It can also validate the map and save the map into a new file.
 */
public class MapTools {

    /**
     * Reads the file from system and loads it into the EditMap. Verifies the format of the map file before loading it in and then
     * logically validates it again before returning it.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the EditMap object ready
     */
    public static EditMap loadAndValidateEditableMap(String p_fileName) {
        EditMap l_editMap;
        try {
            // Read the file line by line
            l_editMap = new EditMap();
            String l_filePath = "src/main/resources/maps" + p_fileName;
            FileReader mapFile;
            try {
                mapFile = new FileReader(l_filePath);
            } catch (FileNotFoundException e) {
                File newFile = new File(l_filePath);
                boolean success = newFile.createNewFile();
                if(!success) {
                    throw new RuntimeException("Failed to create file.");
                }
                l_editMap.setFileName(p_fileName);
                return l_editMap;
            }
            String line;
            StringBuilder data = new StringBuilder();
            BufferedReader mapReader = new BufferedReader(mapFile);
            line = mapReader.readLine();
            while(line !=null) {
                if(!line.equals("\n")){
                    data.append(line).append("\n");
                    line = mapReader.readLine();
                }
            }
            if (validateFileFormat(l_editMap, data, p_fileName)) {
                if (!loadDataFromFile(l_editMap, data)) {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (NumberFormatException e) {
            Console.print("Cannot parse integer.");
            return null;
        }
        catch (Exception e) {
            Console.print("File does not exist!");
            return null;
        }
        if(validateMap(l_editMap)) {
            return l_editMap;
        }
        return null;
    }

    /**
     * Reads the file from system and loads it into the PlayMap. Verifies the format of the map file before loading it in and then
     * logically validates it again before returning it.
     *
     * @param p_fileName - the name of the file to be opened
     * @return - returns an instance of the PlayMap object ready
     */
    public static PlayMap loadAndValidatePlayableMap(String p_fileName) {
        PlayMap l_playMap;
        try {
            l_playMap = new PlayMap();
            String l_filePath = "src/main/resources/maps" + p_fileName;
            FileReader mapFile;
            mapFile = new FileReader(l_filePath);
            String line;
            StringBuilder data = new StringBuilder();
            BufferedReader mapReader = new BufferedReader(mapFile);
            line = mapReader.readLine();
            while(line !=null) {
                if(!line.equals("\n")){
                    data.append(line).append("\n");
                    line = mapReader.readLine();
                }
            }
            if (validateFileFormat(l_playMap, data, p_fileName)) {
                if (!loadDataFromFile(l_playMap, data)) {
                    return null;
                }
            }
            else {
                return null;
            }
        }
        catch (NumberFormatException e) {
            Console.print("Cannot parse integer.");
            return null;
        }
        catch (Exception e) {
            Console.print("File does not exist!");
            return null;
        }
        if(validateMap(l_playMap)) {
            l_playMap.initGameMapElements();
            return l_playMap;
        }
        return null;
    }

    /**
     *
     * This functions checks if the file is in the correct format
     *
     * @param p_map - the Map object that we need to load data into
     * @param p_data - the StringBuilder object that contains the data from the file
     * @return true if the data is loaded in correctly, false otherwise
     */
    public static boolean loadDataFromFile(AbstractMap p_map, StringBuilder p_data) {
        String[] continentData = p_data.substring(p_data.toString().toLowerCase().indexOf("[continents]")+13, p_data.toString().toLowerCase().indexOf("[countries]")).split("\n");
        String[] countryData = p_data.substring(p_data.toString().toLowerCase().indexOf("[countries]")+12, p_data.toString().toLowerCase().indexOf("[borders]")).split("\n");
        String[] neighborData = p_data.substring(p_data.toString().toLowerCase().indexOf("[borders]")+10).split("\n");
        int l_counter = 0;
        for(String s:continentData) {
            int l_continentId = ++l_counter;
            String[] l_splitData = s.split(" ");
            int l_continentBonusValue = Integer.parseInt(l_splitData[1]);
            if (!loadContinent(p_map,l_continentId, l_continentBonusValue)){
                return false;
            }
        }
        for(String s:countryData) {
            String[] l_splitData = s.split(" ");
            int l_countryId = Integer.parseInt(l_splitData[0]);
            int l_continentId = Integer.parseInt(l_splitData[2]);
            if (!loadCountry(p_map,l_countryId, l_continentId)) {
                return false;
            }
        }
        for(String s:neighborData) {
            String[] temp = s.split(" ");
            int[] arr = new int[temp.length];
            int countryID= Integer.parseInt(temp[0]);
            for (int i=1;i<temp.length;i++) {
                if(Arrays.binarySearch(arr,Integer.parseInt(temp[i]))>0) {
                    Console.print("Duplicate neighbours cannot exist.");
                    return false;
                }
                arr[i] = Integer.parseInt(temp[i]);
                if (!loadNeighbour(p_map,countryID,arr[i])) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Loads a new continent to the map.
     *
     * @param p_map            the AbstractMap object to which we are adding the continent to
     * @param p_continentId    the id of the continent to add
     * @param p_continentBonus the bonus for the control of the continent to add
     */
    private static boolean loadContinent(AbstractMap p_map, int p_continentId, int p_continentBonus) {
        if (p_map.getCountriesInContinent().get(p_continentId) != null) {
            Console.print("Continent already exists.");
            return false;
        } else {
            p_map.getCountriesInContinent().put(p_continentId, new LinkedHashSet<>());
            p_map.getContinentBonuses().put(p_continentId, p_continentBonus);
            return true;
        }
    }

    /**
     * Loads a country to the map.
     *
     * @param p_map         the AbstractMap object to which we are adding the country to
     * @param p_countryId   the id of the country to add
     * @param p_continentId the id of the continent to add the country to
     */
    private static boolean loadCountry(AbstractMap p_map, int p_countryId, int p_continentId) {
        if (p_map.getCountriesInContinent().get(p_continentId) == null) {
            Console.print("Continent does not exist.");
            return false;
        } else if (p_map.getNeighbours().get(p_countryId) != null) {
            Console.print("Country already exists.");
            return false;
        } else {
            p_map.getNeighbours().put(p_countryId, new LinkedHashSet<>());
            p_map.getCountriesInContinent().get(p_continentId).add(p_countryId);
            return true;
        }
    }

    /**
     * Loads a neighbour to a country
     *
     * @param p_map                the AbstractMap object to which we are adding the country to
     * @param p_countryId          the id of the country to add the neighbour to
     * @param p_neighbourCountryId the id of the neighbour to add
     */
    private static boolean loadNeighbour(AbstractMap p_map, int p_countryId, int p_neighbourCountryId) {
        if (p_countryId == p_neighbourCountryId) {
            Console.print("Country cannot be neighbour of itself.");
            return false;
        } else if (p_map.getNeighbours().get(p_countryId) == null) {
            Console.print("Country does not exist or is invalid.");
            return false;
        } else if (p_map.getNeighbours().get(p_neighbourCountryId) == null) {
            Console.print("Neighbour Country does not exist or is invalid.");
            return false;
        } else {
            p_map.getNeighbours().get(p_countryId).add(p_neighbourCountryId);
            p_map.getNeighbours().get(p_neighbourCountryId).add(p_countryId);
            return true;
        }
    }
    /**
     *
     * This functions validates the map before saving into a physical file on the system
     *
     * @param p_mapData - the EditMap object to save to the file
     * @param p_fileNameForSave - name of the new save file
     * @return true if the file was saved successfully, false otherwise
     */
    public static boolean saveMapFile(EditMap p_mapData, String p_fileNameForSave) {

        // Ensure the map is valid
        if(!validateMap(p_mapData)) {
            return false;
        }

        // Serialise the data
        StringBuilder l_data = new StringBuilder();
        l_data.append("[continents]\n");
        p_mapData.getContinentBonuses().forEach((key,values) -> {
            l_data.append(key).append(" ").append(key).append(" ").append(values).append("\n");
        });
        l_data.append("\n[countries]\n");
        p_mapData.getCountriesInContinent().forEach((key,values) -> {
            for(Integer c:values)
                l_data.append(c).append(" ").append(c).append(" ").append(key).append("\n");
        });
        l_data.append("\n[borders]");
        p_mapData.getNeighbours().forEach((key,values) -> {
            l_data.append("\n").append(key).append(" ").append(values.toString().trim().replaceAll("[\\[\\]\",]",""));
        });

        // Write the data to the file
        PrintWriter l_write;
        try {
            l_write = new PrintWriter("src/main/resources/maps"+p_fileNameForSave);
            l_write.write(String.valueOf(l_data));
            l_write.close();
            return true;
        }
        catch (Exception e) {
            Console.print(e.getMessage());
            return false;
        }
    }

    /**
     *
     * This functions checks if the file is in the correct format
     *
     * @param p_map - the EditMap object that we need to check format of
     * @param p_data - the StringBuilder object that contains the data from the file
     * @param p_fileName - name of the file
     * @return the EditMap object if the file is in the correct format, null otherwise
     */
    public static boolean validateFileFormat(AbstractMap p_map, StringBuilder p_data, String p_fileName) {
        if (p_data.toString().toLowerCase().contains("[continents]") && p_data.toString().toLowerCase().contains("[countries]") && p_data.toString().toLowerCase().contains("[borders]")) {
            p_map.setFileName(p_fileName);
            return true;
        } else {
            Console.print("File is missing information or is in the wrong format.");
            return false;
        }
    }

    /**
     * This class is used to create a graph of the Map to check for connectivity.
     */
    private static class Graph {

        // Number of vertices in the graph
        private int vertices;

        // Adjacency list for the graph
        private ArrayList<Integer>[] adjacencyList;

        /**
         * Default constructor for creating a new graph
         *
         * @param p_vertex - the number of vertices in the graph
         */
        Graph(int p_vertex) {
            this.vertices = p_vertex;
            adjacencyList = new ArrayList[vertices];
            for(int i = 0; i < vertices; i++) {
                adjacencyList[i] = new ArrayList<Integer>();
            }
        }

        /**
         * Adds an edge to the graph
         *
         * @param p_vertex - the vertex to which the edge is to be added
         * @param p_edge - the edge to be added
         */
        void addEdge(int p_vertex, int p_edge) {
            adjacencyList[p_vertex].add(p_edge);
        }

        /**
         * Used to recursively traverse the graph
         *
         * @param p_vertex - the vertex to start the traversal from
         * @param p_visited - the boolean array to keep track of visited vertices
         */
        void dfsTraversal(int p_vertex, boolean[] p_visited) {
            p_visited[p_vertex] = true;
            for (int l_next : adjacencyList[p_vertex]) {
                if (!p_visited[l_next]) {
                    dfsTraversal(l_next, p_visited);
                }
            }
        }

        /**
         * Gets the transpose of the graph
         *
         * @return the transpose of the graph
         */
        Graph getTranspose(){
            Graph l_graph = new Graph(vertices);
            for (int l_vertex = 0; l_vertex < vertices; l_vertex++) {
                for (Integer l_integer : adjacencyList[l_vertex]) {
                    l_graph.adjacencyList[l_integer].add(l_vertex);
                }
            }
            return l_graph;
        }

        /**
         * Checks if the graph is strongly connected
         *
         * @return true if the graph is strongly connected, false otherwise
         */
        Boolean isStronglyConnected() {
            boolean[] l_visited = new boolean[vertices];
            for(int l_vertex = 0; l_vertex < vertices; l_vertex++) {
                l_visited[l_vertex] = false;
            }
            dfsTraversal(0, l_visited);
            for (boolean l_vertex : l_visited) {
                if (!l_vertex) {
                    return false;
                }
            }
            Graph l_transpose = getTranspose();
            for(int l_vertex = 0; l_vertex < vertices; l_vertex++) {
                l_visited[l_vertex] = false;
            }
            l_transpose.dfsTraversal(0, l_visited);
            for (boolean l_vertex : l_visited) {
                if (!l_vertex) {
                    return false;
                }
            }
            return true;
        }
    }

    /**
     * Logically validates the AbstractMap object
     *
     * @param p_mapData - the AbstractMap object that needs to be validated
     * @return true if the map is valid, false otherwise
     */
    public static boolean validateMap(AbstractMap p_mapData) {
        if(checkEmptyContinent(p_mapData)) {
            return false;
        }
        if(checkEmptyNeighbours(p_mapData)) {
            return false;
        }
        if(!checkConnectedContinent(p_mapData)) {
            return false;
        }
        return checkConnectedMap(p_mapData);
    }

    /**
     * Check if the continent is empty , i.e, has no countries
     *
     * @param p_mapData - the AbstractMap object that needs to be checked
     * @return true if the continent doesn't have any countries, false otherwise
     */
    public static boolean checkEmptyContinent(AbstractMap p_mapData) {
        if(p_mapData.getContinentBonuses().isEmpty())
            return true;
        else {
            for(Set<Integer> countries:p_mapData.getCountriesInContinent().values()) {
                if(countries.isEmpty()) {
                    Console.print("Continent has no countries.");
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if there are any countries with empty neighbours.
     *
     * @param p_mapData - the AbstractMap object that needs to be checked
     * @return true if the neighbours are empty, false otherwise
     */
    public static boolean checkEmptyNeighbours(AbstractMap p_mapData) {
        for(Set<Integer> neighbours:p_mapData.getNeighbours().values())
            if(neighbours.isEmpty()) {
                Console.print("Country has no neighbours.");
                return true;
            }
        return false;
    }

    /**
     * Checks if the continents are connected
     *
     * @param p_mapData - the AbstractMap object that needs to be checked
     * @return true if the continent is a connected graph, false otherwise
     */
    public static boolean checkConnectedContinent(AbstractMap p_mapData) {
        boolean isContinentConnected = false;
        for (Map.Entry<Integer, Set<Integer>> entry : p_mapData.getCountriesInContinent().entrySet()) {
            Integer l_continentId = entry.getKey();
            Set<Integer> l_countryIDs = entry.getValue();
            if(p_mapData.getCountriesInContinent().get(l_continentId).size() == 1) {
                continue;
            }
            for (Integer l_countryID : l_countryIDs) {
                isContinentConnected=false;
                for (Integer l_neighbourID : p_mapData.getNeighbours().get(l_countryID)) {
                    if(p_mapData.getContinentIdForCountry(l_neighbourID) == p_mapData.getContinentIdForCountry(l_countryID)) {
                        isContinentConnected = true;
                        break;
                    }
                }
                if(!isContinentConnected) {
                    Console.print("Continent " + l_continentId + " is not connected.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the map is a connected graph
     *
     * @param p_mapData - the AbstractMap object that needs to be checked
     * @return true if the map is a connected graph, false otherwise
     */
    public static boolean checkConnectedMap(AbstractMap p_mapData) {
        Graph l_graph = new Graph(p_mapData.getNeighbours().keySet().size());
        for (Map.Entry<Integer, Set<Integer>> entry : p_mapData.getNeighbours().entrySet()) {
            Integer l_countryId = entry.getKey();
            Set<Integer> l_neighbourIds = entry.getValue();
            for (Integer l_neighbourId : l_neighbourIds) {
                l_graph.addEdge(l_countryId-1, l_neighbourId-1);
            }
        }
        if (!l_graph.isStronglyConnected()){
            Console.print("The map is not strongly connected.");
            return false;
        }
        return true;
    }
}