package com.fsociety.warzone.model.player.strategy;

import com.fsociety.warzone.GameEngine;
import com.fsociety.warzone.asset.phase.Phase;
import com.fsociety.warzone.asset.phase.play.mainplay.Attack;
import com.fsociety.warzone.asset.phase.play.mainplay.Reinforcement;
import com.fsociety.warzone.controller.GameplayController;
import com.fsociety.warzone.model.Country;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class implements the Random strategy for a computer player.
 * A random computer player strategy that focuses on doing random crazy stuff.
 */
public class Random implements Strategy {

    /**
     * This variable is used to track if it's executing the first advance or not.
     */
    private boolean d_isFirstAdvanceOfTurn;

    /**
     * This map is used for executing advance orders in order to randomly produce advance orders.
     */
    private Map<Country, Country> d_advanceOrders;

    /**
     * Default constructor for the class
     */
    public Random() {
        d_isFirstAdvanceOfTurn = true;
        d_advanceOrders = new HashMap<>();
    }

    /**
     * Issue order for a "random" player
     * @param p_playerName The name of the player that is to be prompted for an input. (Applicable only for Human players)
     */
    @Override
    public void issueOrder(String p_playerName) {
        // Get the player ID from player name
        int l_playerId = GameplayController.getPlayerNameMap().get(p_playerName).getId();

        // Get all countries the player currently owns
        List<Country> l_playerCountries = GameplayController.getPlayMap().getCountriesOwnedByPlayer(l_playerId);

        // Get the current phase of the game and then decide what to do
        Phase l_currentPhase = GameEngine.getPhase();

        // Deploy in case the game is in the Reinforcement phase
        if(l_currentPhase instanceof Reinforcement) {
            issueReinforcementOrder(l_currentPhase, l_playerCountries, l_playerId);
        }
        // Attack in case the game is in the Attack phase
        if (l_currentPhase instanceof Attack) {
            issueAdvanceOrder(l_currentPhase, l_playerCountries);
        }
    }

    /**
     * Issues the advance / attack order randomly for all countries having troops greater than 0
     * @param p_currentPhase - The current phase
     * @param p_playerCountries - The list of countries owned by the player
     */
    private void issueAdvanceOrder(Phase p_currentPhase, List<Country> p_playerCountries) {
        if(d_isFirstAdvanceOfTurn) {
            createOrdersRandomlyForEachCountry(p_playerCountries);
            d_isFirstAdvanceOfTurn = false;
        }
        if(d_advanceOrders.isEmpty()) {
            d_advanceOrders = new HashMap<>();
            d_isFirstAdvanceOfTurn = true;
            p_currentPhase.commit();
            return;
        }

        java.util.Random l_random = new java.util.Random();
        Country l_advanceFrom = d_advanceOrders.keySet().stream().toList().get(0);
        int l_troopsForAdvancement = l_random.nextInt(l_advanceFrom.getArmies()) + 1;
        Country l_advanceTo = d_advanceOrders.get(l_advanceFrom);
        p_currentPhase.advance(l_advanceFrom.getCountryId(), l_advanceTo.getCountryId(), l_troopsForAdvancement);
        d_advanceOrders.remove(l_advanceFrom);
    }

    /**
     * Creates random advance orders for each country
     * @param p_playerCountries The list of player Countries
     */
    private void createOrdersRandomlyForEachCountry(List<Country> p_playerCountries) {
        java.util.Random l_random = new java.util.Random();
        p_playerCountries
                .stream()
                .filter(p_playerCountry -> p_playerCountry.getArmies() > 0)
                .forEach(p_playerCountry -> {
                    List<Country> l_listOfAllNeighbours = GameplayController.getPlayMap().getListOfAllNeighbours(p_playerCountry);
                    int l_countryIndex = l_random.nextInt(l_listOfAllNeighbours.size());
                    Country l_destinationCountry = l_listOfAllNeighbours.get(l_countryIndex);
                    d_advanceOrders.put(p_playerCountry, l_destinationCountry);
                });
    }

    /**
     * Issue deploy orders randomly
     * @param p_currentPhase - Current phase of the game
     * @param p_playerCountries - The list of countries the player owns
     * @param p_playerId  - The ID of the player
     */
    private void issueReinforcementOrder(Phase p_currentPhase, List<Country> p_playerCountries, int p_playerId) {
        java.util.Random l_random = new java.util.Random();
        int l_troopsAvailableForDeployment = GameplayController.getPlayerFromId(p_playerId).getAvailableReinforcements();
        int l_countryIndex = l_random.nextInt(p_playerCountries.size());
        Country l_countryForDeployment = p_playerCountries.get(l_countryIndex);
        int l_troopsForDeployment = l_random.nextInt(l_troopsAvailableForDeployment) + 1;
        p_currentPhase.deploy(l_countryForDeployment.getCountryId(), l_troopsForDeployment);
    }

    public String toString() {
        return "Random";
    }
}
