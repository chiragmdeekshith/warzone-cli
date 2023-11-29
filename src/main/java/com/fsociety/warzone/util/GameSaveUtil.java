package com.fsociety.warzone.util;

import com.fsociety.warzone.model.GameSaveData;

import java.io.*;

/**
 * This class gives the functionality to save the game into a file and read a game from a save file
 */
public class GameSaveUtil {

    private static final String d_gameSavePath = "src/main/resources/saves/";

    /**
     * Saves the GameSaveData object into a file
     * @param p_gameSaveData the game save data
     * @param p_fileName the nmae of the file
     * @return true if the save was successful, false otherwise
     */
    public static boolean saveGameToFile(GameSaveData p_gameSaveData, String p_fileName) {
        String l_filePath = d_gameSavePath + p_fileName;
        File l_file = new File(l_filePath);
        FileOutputStream l_fileOutputStream = null;
        try {
            l_fileOutputStream = new FileOutputStream(l_file);
        } catch (FileNotFoundException p_fileNotFoundException) {
            File l_newFile = new File(l_filePath);
            boolean l_success;
            try {
                l_success = l_newFile.createNewFile();
                if(!l_success) {
                    throw new IOException();
                }
            } catch (IOException p_ioException) {
                return false;
            }
        }
        try {
            ObjectOutputStream l_objectOutputStream;
            l_objectOutputStream = new ObjectOutputStream(l_fileOutputStream);
            l_objectOutputStream.writeObject(p_gameSaveData);
            l_objectOutputStream.flush();
        } catch (IOException p_ioException) {
            return false;
        }
        return true;
    }


    /**
     * Load the GameSaveData object from the file
     * @param p_fileName the name of the file
     * @return the loaded GameSaveData object
     */
    public static GameSaveData loadGameFromFile(String p_fileName) {
        String l_filePath = d_gameSavePath + p_fileName;
        GameSaveData l_gameSaveData = null;
        try {
            FileInputStream l_fileInputStream = new FileInputStream(l_filePath);
            ObjectInputStream l_objectInputStream = new ObjectInputStream(l_fileInputStream);
            l_gameSaveData = (GameSaveData) l_objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            return null;
        }
        return l_gameSaveData;
    }
}
