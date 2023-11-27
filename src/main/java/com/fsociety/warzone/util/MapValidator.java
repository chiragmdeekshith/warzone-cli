package com.fsociety.warzone.util;

import com.fsociety.warzone.model.map.AbstractMap;
import com.fsociety.warzone.view.Console;

import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * This class is used to validate the map.
 */
public class MapValidator {
    /**
     * This class is used to create a graph of the Map to check for connectivity.
     */
    private static class Graph {

        // Number of vertices in the graph
        private int d_vertices;

        // Adjacency list for the graph
        private ArrayList<Integer>[] d_adjacencyList;

        /**
         * Default constructor for creating a new graph.
         *
         * @param p_vertex - the number of vertices in the graph
         */
        @SuppressWarnings("unchecked")
        Graph(int p_vertex) {
            this.d_vertices = p_vertex;
            d_adjacencyList = new ArrayList[d_vertices];
            for(int i = 0; i < d_vertices; i++) {
                d_adjacencyList[i] = new ArrayList<Integer>();
            }
        }

        /**
         * Adds an edge to the graph.
         *
         * @param p_vertex - the vertex to which the edge is to be added
         * @param p_edge - the edge to be added
         */
        void addEdge(int p_vertex, int p_edge) {
            d_adjacencyList[p_vertex].add(p_edge);
        }

        /**
         * Used to recursively traverse the graph.
         *
         * @param p_vertex - the vertex to start the traversal from
         * @param p_visited - the boolean array to keep track of visited vertices
         */
        void dfsTraversal(int p_vertex, boolean[] p_visited) {
            p_visited[p_vertex] = true;
            for (int l_next : d_adjacencyList[p_vertex]) {
                if (!p_visited[l_next]) {
                    dfsTraversal(l_next, p_visited);
                }
            }
        }

        /**
         * Gets the transpose of the graph.
         *
         * @return the transpose of the graph
         */
        Graph getTranspose(){
            Graph l_graph = new Graph(d_vertices);
            for (int l_vertex = 0; l_vertex < d_vertices; l_vertex++) {
                for (Integer l_integer : d_adjacencyList[l_vertex]) {
                    l_graph.d_adjacencyList[l_integer].add(l_vertex);
                }
            }
            return l_graph;
        }

        /**
         * Checks if the graph is strongly connected.
         *
         * @return true if the graph is strongly connected, false otherwise
         */
        Boolean isStronglyConnected() {
            boolean[] l_visited = new boolean[d_vertices];
            for(int l_vertex = 0; l_vertex < d_vertices; l_vertex++) {
                l_visited[l_vertex] = false;
            }
            dfsTraversal(0, l_visited);
            for (boolean l_vertex : l_visited) {
                if (!l_vertex) {
                    return false;
                }
            }
            Graph l_transpose = getTranspose();
            for(int l_vertex = 0; l_vertex < d_vertices; l_vertex++) {
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
     * Logically validates the AbstractMap object.
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
     * Check if the continent is empty , i.e, has no countries.
     *
     * @param p_mapData - the AbstractMap object that needs to be checked
     * @return true if the continent doesn't have any countries, false otherwise
     */
    public static boolean checkEmptyContinent(AbstractMap p_mapData) {
        if(p_mapData.getContinentBonuses().isEmpty())
            return true;
        else {
            for(Set<Integer> l_countries:p_mapData.getCountriesInContinent().values()) {
                if(l_countries.isEmpty()) {
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
        for(Set<Integer> l_neighbours:p_mapData.getNeighbours().values())
            if(l_neighbours.isEmpty()) {
                Console.print("Country has no neighbours.");
                return true;
            }
        return false;
    }

    /**
     * Checks if the continents are connected.
     *
     * @param p_mapData - the AbstractMap object that needs to be checked
     * @return true if the continent is a connected graph, false otherwise
     */
    public static boolean checkConnectedContinent(AbstractMap p_mapData) {
        boolean l_isContinentConnected;
        for (Map.Entry<Integer, Set<Integer>> entry : p_mapData.getCountriesInContinent().entrySet()) {
            Integer l_continentId = entry.getKey();
            Set<Integer> l_countryIDs = entry.getValue();
            if(p_mapData.getCountriesInContinent().get(l_continentId).size() == 1) {
                continue;
            }
            for (Integer l_countryID : l_countryIDs) {
                l_isContinentConnected=false;
                for (Integer l_neighbourID : p_mapData.getNeighbours().get(l_countryID)) {
                    if(p_mapData.getContinentIdForCountry(l_neighbourID) == p_mapData.getContinentIdForCountry(l_countryID)) {
                        l_isContinentConnected = true;
                        break;
                    }
                }
                if(!l_isContinentConnected) {
                    Console.print("Continent " + l_continentId + " is not connected.");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Checks if the map is a connected graph.
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
